package xen42.canadamod.entities;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
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
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import xen42.canadamod.CanadaItems;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.CanadaSounds;

public class BeaverEntity extends AnimalEntity {
    private static final TrackedData<Integer> CHOP_FATIGUE = DataTracker.registerData(BeaverEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> CHOP_FRENZY = DataTracker.registerData(BeaverEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Nullable
    private BlockPos choppingTree;

    public final AnimationState chopAnimationState;

    public boolean isChopping;

    public BeaverEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        //this.moveControl = new BeaverMoveControl(this);
        var mobNavigation = (MobNavigation)this.getNavigation();
        mobNavigation.setCanSwim(true);
        chopAnimationState = new AnimationState();
    }

    private static final EntityDimensions BABY_BASE_DIMENSIONS = EntityType.COW.getDimensions().scaled(0.5F).withEyeHeight(0.665F);

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25D));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(4, new TemptGoal(this, 1.2D, stack -> this.isTemptItem(stack), false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.goalSelector.add(9, new BeaverChopTreeGoal(this));
    }

    public static DefaultAttributeContainer.Builder createBeaverAttributes() {
        return AnimalEntity.createAnimalAttributes()
            .add(EntityAttributes.MAX_HEALTH, 5.0f)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
            .add(EntityAttributes.WATER_MOVEMENT_EFFICIENCY, 2f)
            .add(EntityAttributes.OXYGEN_BONUS);
    }

    @Override
    public void tick() {
        super.tick();
        updateAnimations();

        if (this.getDataTracker().get(CHOP_FATIGUE) > 0) {
            this.getDataTracker().set(CHOP_FATIGUE, this.getDataTracker().get(CHOP_FATIGUE) - 1);
        }
        if (this.getDataTracker().get(CHOP_FRENZY) > 0) {
            this.getDataTracker().set(CHOP_FRENZY, this.getDataTracker().get(CHOP_FRENZY) - 1);
        }
    }

    public void updateAnimations() {
        if (isChopping && !chopAnimationState.isRunning()) {
            chopAnimationState.start(this.age);
        }
        if (!isChopping && chopAnimationState.isRunning()) {
            chopAnimationState.stop();
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(CHOP_FATIGUE, this.random.nextBetween(20 * 60 * 3, 20 * 60 * 10));
        builder.add(CHOP_FRENZY, 0);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.STICK);
    }

    public boolean isFrenzyItem(ItemStack stack) {
        return stack.isOf(CanadaItems.DONAIR) || stack.isOf(CanadaItems.POUTINE) || stack.isOf(CanadaItems.PIEROGI);
    }

    public boolean isFatigueRefreshItem(ItemStack stack) {
        return stack.isOf(CanadaItems.MAPLE_SYRUP_BOTTLE);
    }

    public boolean isTemptItem(ItemStack stack) {
        return (this.getDataTracker().get(CHOP_FATIGUE) > 0 && isFatigueRefreshItem(stack))
            || (this.getDataTracker().get(CHOP_FRENZY) <= 0 && isFrenzyItem(stack))
            || isBreedingItem(stack);
    }

    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return (BeaverEntity)CanadaMod.BEAVER_ENTITY.create(world, SpawnReason.BREEDING);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return CanadaSounds.SOUND_BEAVER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return CanadaSounds.SOUND_BEAVER_DEATH;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return CanadaSounds.SOUND_BEAVER_AMBIENT;
    }

    @Override
    public float getScaleFactor() {
        return isBaby() ? 0.5F : 1.0F;
    }

    @Override
    public EntityDimensions getBaseDimensions(EntityPose pose) {
        return isBaby() ? BABY_BASE_DIMENSIONS : super.getBaseDimensions(pose);
    }

    private static class BeaverMoveControl extends AquaticMoveControl {
        public BeaverMoveControl(MobEntity entity) {
            super(entity, 45, 10, 0.1F, 0.5F, false);
        }
    }

    private static class SwimToRandomPlaceGoal extends SwimAroundGoal { 
        public SwimToRandomPlaceGoal(BeaverEntity beaver) {
            super(beaver, 1.0D, 40);
        }
    }

    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(CanadaItems.BEAVER_SPAWN_EGG);
    }

    @Override
    public boolean isInsideWall() {
        // Same as super but ignoring logs
        // They die too often when bonemealing trees
        if (this.noClip) {
            return false;
        } else {
            var f = this.getDimensions(this.getPose()).width() * 0.8F;
            var box = Box.of(this.getEyePos(), (double)f, 1.0E-6, (double)f);
            return BlockPos.stream(box).anyMatch((pos) -> {
                BlockState blockState = this.getWorld().getBlockState(pos);
                return !blockState.isAir() && !blockState.isIn(BlockTags.LOGS) && blockState.shouldSuffocate(this.getWorld(), pos) && VoxelShapes.matchesAnywhere(blockState.getCollisionShape(this.getWorld(), pos).offset(pos), VoxelShapes.cuboid(box), BooleanBiFunction.AND);
            });
        }
    }

    public boolean isFrenzied() {
        return this.getDataTracker().get(CHOP_FRENZY) > 0;
    }

    public boolean isFatigued() {
        return this.getDataTracker().get(CHOP_FATIGUE) > 0;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        var itemStack = player.getStackInHand(hand);

        var didEat = false;
        if (this.isFatigueRefreshItem(itemStack) && this.isFatigued()) {
            didEat = true;
            if (!this.getWorld().isClient) {
                this.getDataTracker().set(CHOP_FATIGUE, 0);
            }
        }
        if (this.isFrenzyItem(itemStack) && !this.isFrenzied()) {
            didEat = true;
            if (!this.getWorld().isClient) {
                this.getDataTracker().set(CHOP_FRENZY, 20 * 30);
            }
        }

        if (didEat) {
            if (!this.getWorld().isClient) {
                this.eat(player, hand, itemStack);
                this.playEatSound();
                return ActionResult.SUCCESS_SERVER;
            }
            else {
                this.getWorld().addParticleClient(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
                if (itemStack.isOf(CanadaItems.MAPLE_SYRUP_BOTTLE)) {
                    this.getWorld().playSoundFromEntityClient(this, SoundEvents.ITEM_HONEY_BOTTLE_DRINK.value(), SoundCategory.NEUTRAL, 1f, 1f);
                }
                else {
                    this.getWorld().playSoundFromEntityClient(this, CanadaSounds.SOUND_BEAVER_EAT, SoundCategory.NEUTRAL, 1f, 1f);
                }
                return ActionResult.SUCCESS;
            }
        }

        return super.interactMob(player, hand);
    }

    @Override
    public void onDeath(DamageSource source) {
        setChoppingProgress(-1);
        super.onDeath(source);
    }

    public void setChoppingProgress(int progress) {
        if (choppingTree != null) {
            PlayerLookup.tracking(this).forEach(player -> {
                ServerPlayNetworking.send(player, new BeaverChopTreeEffectPayload(this.getId(), progress, choppingTree));
            });            
        }
    }

    public void setChoppingTree(BlockPos pos) {
        this.choppingTree = pos;
    }

    public BlockPos getChoppingTreePos() {
        return choppingTree;
    }

    public void stopChopping() {
        setChoppingProgress(-1);
        setChoppingTree(null);
    }

    public void onChopTree() {
        // Pause for 5 to 10 minutes
        this.getDataTracker().set(CHOP_FATIGUE, this.random.nextBetween(5 * 60 * 20, 10 * 60 * 20));
    }

    public boolean canChopTree() {
        return !this.isBaby() && (this.getDataTracker().get(CHOP_FATIGUE) <= 0 || isFrenzied());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putInt("chopFatigue", this.getDataTracker().get(CHOP_FATIGUE));
        nbt.putInt("chopFrenzy", this.getDataTracker().get(CHOP_FRENZY));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.getDataTracker().set(CHOP_FATIGUE, nbt.getInt("chopFatigue").orElse(0));
        this.getDataTracker().set(CHOP_FRENZY, nbt.getInt("chopFrenzy").orElse(0));
    }
}
