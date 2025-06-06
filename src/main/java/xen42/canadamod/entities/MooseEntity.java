package xen42.canadamod.entities;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.AttackGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.HorseBondWithPlayerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import xen42.canadamod.CanadaItems;
import xen42.canadamod.CanadaMod;

public class MooseEntity extends AbstractHorseEntity implements Angerable {
    private static final TrackedData<Boolean> LEFT_ANTLER_MISSING = DataTracker.registerData(MooseEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> RIGHT_ANTLER_MISSING = DataTracker.registerData(MooseEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


    public MooseEntity(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
        var mobNavigation = (MobNavigation)this.getNavigation();
        mobNavigation.setCanSwim(true);
        mobNavigation.setCanWalkOverFences(true);
    }

    private static final EntityDimensions BABY_BASE_DIMENSIONS = EntityType.COW.getDimensions().scaled(0.5F).withEyeHeight(0.665F);

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
		builder.add(LEFT_ANTLER_MISSING, false);
		builder.add(RIGHT_ANTLER_MISSING, false);
    }

    public boolean isLeftAntlerMissing() {
        return this.getDataTracker().get(LEFT_ANTLER_MISSING);
    }

    public boolean isRightAntlerMissing() {
        return this.getDataTracker().get(RIGHT_ANTLER_MISSING);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new AttackGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0D, moose -> moose.isBaby() ? DamageTypeTags.PANIC_CAUSES : DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.add(2, new HorseBondWithPlayerGoal(this, 1.2));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(4, new TemptGoal(this, 1.2D, stack -> this.isBreedingItem(stack), false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]).setGroupRevenge(new Class[0]));
        this.targetSelector.add(2, new ActiveTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
    }

    public static DefaultAttributeContainer.Builder createMooseAttributes() {
        return AnimalEntity.createAnimalAttributes().add(EntityAttributes.MAX_HEALTH, 25.0f).add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
            .add(EntityAttributes.WATER_MOVEMENT_EFFICIENCY, 2f)
            .add(EntityAttributes.ATTACK_DAMAGE, 6.0D)
            .add(EntityAttributes.OXYGEN_BONUS)
            .add(EntityAttributes.JUMP_STRENGTH, 0.4D)
            .add(EntityAttributes.STEP_HEIGHT, 1.5D)
            .add(EntityAttributes.FOLLOW_RANGE, 20.0D);
    }

    @Override
    public void mobTick(ServerWorld world) {
        super.mobTick(world);
        tickAngerLogic(world, true);
        if (this.attacker != null) {
            if (!this.attacker.isAlive() || this.age - this.lastAttackedTime > 100) {
                setAttacker(null);
            }
        }
        tryRegenAntler(true, false);
        tryRegenAntler(false, false);
        tryShedAntler(true, false);
        tryShedAntler(false, false);
    }

    private void tryRegenAntler(boolean left, boolean force) {
        if (left ? this.isLeftAntlerMissing() : this.isRightAntlerMissing()) {
            // Average length 5 minutes to regen antlers
            if (force || this.random.nextFloat() < 1f / (float)(5 * 60 * 20)) {
                this.getDataTracker().set(left ? LEFT_ANTLER_MISSING : RIGHT_ANTLER_MISSING, false);
                this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
            }
        }
    }

    private void tryShedAntler(boolean left, boolean force) {
        if (this.getWorld().isClient()) {
            return;
        }

        // Average length 10 minutes to shed antlers
        if (force || this.random.nextFloat() < 1f / (float)(10 * 60 * 20)) {
            if (left ? !this.isLeftAntlerMissing() : !this.isRightAntlerMissing()) {
                var yaw = (float)(this.bodyYaw * (Math.PI / 180));
                var offset = new Vec3d(left ? 0.4f : -0.4f, 0f, -1.5f);
                var antlerPos = new Vec3d(this.getX(), this.getEyeY(), this.getZ()).add(offset.rotateY(yaw));

                var item = this.dropItem((ServerWorld)this.getWorld(), CanadaItems.ANTLERS);
                item.setPosition(antlerPos);
                this.getDataTracker().set(left ? LEFT_ANTLER_MISSING : RIGHT_ANTLER_MISSING, true);

                this.playSound(SoundEvents.ITEM_AXE_STRIP, 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
            }
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SEAGRASS);
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) { 
        if (other != this && other instanceof MooseEntity otherMoose) {
            return canBreed() && otherMoose.canBreed();
        } 
        return false;
    }

    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return (MooseEntity)CanadaMod.MOOSE_ENTITY.create(world, SpawnReason.BREEDING);
    }

    @Override
    public EntityDimensions getBaseDimensions(EntityPose pose) {
        return isBaby() ? BABY_BASE_DIMENSIONS : super.getBaseDimensions(pose);
    }

    @Override
    public void tickMovement() {
        if (this.getControllingPassenger() != null) {
            if (this.isSubmergedInWater() || (this.isTouchingWater() && this.jumping)) {
                this.swimUpward(FluidTags.WATER);
            }
        }
        super.tickMovement();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) { 
        if (attacking != null) {
            return ActionResult.FAIL;
        }

        var itemStack = player.getStackInHand(hand);
        if (isBreedingItem(itemStack)) {
            return interactHorse(player, itemStack);
        }

        if (player.shouldCancelInteraction() && !isBaby()) {
            openInventory(player);
            return (ActionResult)ActionResult.SUCCESS;
        } 

        if (!itemStack.isEmpty()) {
            if (this.isTame()) {
                ActionResult actionResult = itemStack.useOnEntity(player, (LivingEntity)this, hand);
                if (actionResult.isAccepted())
                    return actionResult; 

                if (getPassengerList().size() < 1 && !isBaby()) {
                    putPlayerOnBack(player);
                }
            }
            else {
                this.playAngrySound();
                return ActionResult.SUCCESS;
            }
        }

        // Try taming
        if (!this.isTame() && !this.isBaby()) {
            super.interactMob(player, hand);
        }

        return (ActionResult)ActionResult.SUCCESS;
    }

    @Override
    protected boolean receiveFood(PlayerEntity player, ItemStack item) {
        if (!isBreedingItem(item)) {
            return false;
        }

        boolean isHurt = (getHealth() < getMaxHealth());
        if (isHurt) {
            heal(2.0F);
        }

        if (isLeftAntlerMissing()) {
            tryRegenAntler(true, true);
        }
        else if (isRightAntlerMissing()) {
            tryRegenAntler(false, true);
        }

        boolean isBaby = isBaby();
        if (isBaby) {
            getWorld().addParticleClient((ParticleEffect)ParticleTypes.HAPPY_VILLAGER, getParticleX(1.0D), getRandomBodyY() + 0.5D, getParticleZ(1.0D), 0.0D, 0.0D, 0.0D);
            if (!(getWorld()).isClient) {
                growUp(10);
            }
        }

        boolean canLove = (getBreedingAge() == 0 && canEat());
        if (canLove) {
            lovePlayer(player);
        }

        if (isHurt || canLove || isBaby) {
            if (!isSilent()) {
                SoundEvent soundEvent = getEatSound();
                if (soundEvent != null) {
                    getWorld().playSound(null, getX(), getY(), getZ(), soundEvent, getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                }
            }

            emitGameEvent(GameEvent.EAT);
            return true;
        }

        return false;
    }

    @Override
    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_HORSE_ANGRY;
    }

    @Override
    public boolean isTame() {
        return super.isTame() && attacking == null;
    }

    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private int angerTime; 
    @Nullable
    private UUID angryAt;

    @Nullable
    private LivingEntity attacking;

    @Nullable
    private LivingEntity attacker;

    @Nullable
    private PlayerEntity attackingPlayer;

    private int lastAttackedTime;

    @Override
    public void setTarget(@Nullable LivingEntity target) {        
        if (target instanceof PlayerEntity player) {
            setPlayerTarget(player);
        }
        
        super.setTarget(target);
    }

    public void openInventory(PlayerEntity player) {
        if (!this.getWorld().isClient) {
            player.openHorseInventory(this, this.items);
        }
    }

    @Override
    public float getScaleFactor() {
        return isBaby() ? 0.5F : 1.0F;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        writeAngerToNbt(nbt);
        nbt.putBoolean("IsLeftAntlerMissing", this.getDataTracker().get(LEFT_ANTLER_MISSING));
        nbt.putBoolean("IsRightAntlerMissing", this.getDataTracker().get(RIGHT_ANTLER_MISSING));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        readAngerFromNbt(getWorld(), nbt);
        this.getDataTracker().set(LEFT_ANTLER_MISSING, nbt.getBoolean("IsLeftAntlerMissing").orElse(false));
        this.getDataTracker().set(RIGHT_ANTLER_MISSING, nbt.getBoolean("IsRightAntlerMissing").orElse(false));
    }

    public void setPlayerTarget(@Nullable PlayerEntity attacking) {
        this.attackingPlayer = attacking;
        this.playerHitTimer = this.age;
    }

    @Override
    public void setAttacker(@Nullable LivingEntity attacker) {
        setTarget(attacker);
        this.attacker = attacker;
        this.lastAttackedTime = this.age;
    }

    @Nullable
    public LivingEntity getAttacker() {
        return this.attacker;
    }

    @Override
    public void chooseRandomAngerTime() {
        setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if (source.getSource() instanceof LivingEntity && this.random.nextBetween(0, 10) == 0) {
            this.tryShedAntler(this.random.nextBoolean(), true);
        }
        return super.damage(world, source, amount);
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        if (damageSource.getSource() instanceof LivingEntity entity) {
            this.setAttacker(entity);
        }

        super.onDamaged(damageSource);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        if (!this.getWorld().isClient() && !this.isBaby()) {
            if (!this.isLeftAntlerMissing()) {
                var item = this.dropItem((ServerWorld)this.getWorld(), CanadaItems.ANTLERS);
                item.setPosition(this.getEyePos());
            }
            if (!this.isRightAntlerMissing()) {
                var item = this.dropItem((ServerWorld)this.getWorld(), CanadaItems.ANTLERS);
                item.setPosition(this.getEyePos());
            }
        }

        super.onDeath(damageSource);
    }

    @Override
    public int getAngerTime() {
        return this.angerTime;
    }

    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    @Override
    public void setAngryAt(UUID angryAt) {
        this.angryAt = angryAt;
    }
}
