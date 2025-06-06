package xen42.canadamod.entities;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.Entity;
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
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import xen42.canadamod.CanadaMod;

public class MooseEntity extends AbstractHorseEntity implements Angerable {
    public MooseEntity(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
        var mobNavigation = (MobNavigation)this.getNavigation();
        mobNavigation.setCanSwim(true);
        mobNavigation.setCanWalkOverFences(true);
    }

    private static final EntityDimensions BABY_BASE_DIMENSIONS = EntityType.COW.getDimensions().scaled(0.5F).withEyeHeight(0.665F);

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new AttackGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0D, moose -> moose.isBaby() ? DamageTypeTags.PANIC_CAUSES : DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
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
    public ActionResult interactMob(PlayerEntity player, Hand hand) { 
        if (!isTame()) {
            return ActionResult.FAIL;
        }

        var itemStack = player.getStackInHand(hand);
        if (player.shouldCancelInteraction() && !isBaby()) {
            openInventory(player);
            return (ActionResult)ActionResult.SUCCESS;
        } 
        ActionResult actionResult = itemStack.useOnEntity(player, (LivingEntity)this, hand);
        if (actionResult.isAccepted())
            return actionResult; 
        if (isBreedingItem(itemStack)) {
            return interactHorse(player, itemStack);
        }
        if (getPassengerList().size() < 1 && !isBaby()) {
            putPlayerOnBack(player);
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

        boolean isBaby = isBaby();
        if (isBaby) {
            getWorld().addParticleClient((ParticleEffect)ParticleTypes.HAPPY_VILLAGER, getParticleX(1.0D), getRandomBodyY() + 0.5D, getParticleZ(1.0D), 0.0D, 0.0D, 0.0D);
            if (!(getWorld()).isClient) {
                growUp(10);
            }
        }

        boolean canLove = (isTame() && getBreedingAge() == 0 && canEat());
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
    public boolean isTame() {
        return attacking == null;
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
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        readAngerFromNbt(getWorld(), nbt);
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
    public void onDamaged(DamageSource damageSource) {
        if (damageSource.getSource() instanceof LivingEntity entity) {
            this.setAttacker(entity);
        }
        super.onDamaged(damageSource);
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
