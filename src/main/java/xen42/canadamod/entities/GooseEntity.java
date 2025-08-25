package xen42.canadamod.entities;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.UniversalAngerGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import xen42.canadamod.CanadaItems;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.CanadaSounds;

public class GooseEntity extends ChickenEntity implements Angerable {

	private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
	private int angerTime;
	@Nullable
	private UUID angryAt;

	private static final TrackedData<Boolean> ANGRY = DataTracker.registerData(GooseEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(ANGRY, false);
	}

	public GooseEntity(EntityType<? extends ChickenEntity> entityType, World world) {
        super(entityType, world);
        // Override that chickens don't like water
        this.setPathfindingPenalty(PathNodeType.WATER, 0f);
    }
    
    public static DefaultAttributeContainer.Builder createGooseAttributes() {
        return AnimalEntity.createAnimalAttributes()
            .add(EntityAttributes.MAX_HEALTH, 8.0f)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
            .add(EntityAttributes.WATER_MOVEMENT_EFFICIENCY, 2f)
            .add(EntityAttributes.OXYGEN_BONUS)
			.add(EntityAttributes.ATTACK_DAMAGE, 0.5);
    }

	@Override
	protected void eat(PlayerEntity player, Hand hand, ItemStack stack) {
		super.eat(player, hand, stack);
		this.setHealth(this.getMaxHealth());
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new GooseEntity.AttackGoal());
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4));
		this.goalSelector.add(1, new FleeEntityGoal(this, PlayerEntity.class, 8.0F, 1.6, 1.4, 
			entity -> this.getHealth() <= 4 && !((PlayerEntity)entity).isHolding(Items.BREAD)));
		this.goalSelector.add(1, new FleeEntityGoal(this, FoxEntity.class, 8.0F, 1.6, 1.4, entity -> !this.isAngry()));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
		this.goalSelector.add(3, new TemptGoal(this, 1.0, stack -> stack.isOf(Items.BREAD), false));
		this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(7, new LookAroundGoal(this));

		this.targetSelector.add(1, new GooseEntity.GooseRevengeGoal());
		this.targetSelector.add(3, new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
		this.targetSelector.add(5, new UniversalAngerGoal<>(this, false));
	}

    @Override
	public ChickenEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		ChickenEntity chickenEntity = CanadaMod.GOOSE_ENTITY.create(serverWorld, SpawnReason.BREEDING);

		return chickenEntity;
	}

    @Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.isOf(Items.BREAD);
	}

    @Override
	public void tickMovement() {
		super.tickMovement();
		this.lastFlapProgress = this.flapProgress;
		this.lastMaxWingDeviation = this.maxWingDeviation;

		var shouldFlap = (!this.isOnGround() && !this.isTouchingWater()) || this.isAngry();
		
		this.maxWingDeviation = this.maxWingDeviation + (!shouldFlap ? -1.0F : 4.0F) * 0.3F;
		this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0F, 1.0F);
		if (shouldFlap && this.flapSpeed < 1.0F) {
			this.flapSpeed = 1.0F;
		}

		this.flapSpeed *= 0.9F;
		Vec3d vec3d = this.getVelocity();
		if (!this.isOnGround() && vec3d.y < 0.0) {
			this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
		}

		this.flapProgress = this.flapProgress + this.flapSpeed * 2.0F;
		if (this.getWorld() instanceof ServerWorld serverWorld && this.isAlive() && !this.isBaby() && !this.hasJockey() && --this.eggLayTime <= 0) {
            var item = this.dropItem(serverWorld, CanadaItems.GOOSE_EGG);
            item.setPosition(this.getPos());

            this.playSound(CanadaSounds.SOUND_GOOSE_PLOP, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.emitGameEvent(GameEvent.ENTITY_PLACE);

			this.eggLayTime = this.random.nextInt(6000) + 6000;
		}
	}

	@Override
	public void travel(Vec3d movementInput) {
		super.travel(movementInput);
		if (this.isTouchingWater()) {
			this.setVelocity(this.getVelocity().x, this.getVelocity().y - 0.03f, this.getVelocity().z);
		}
	}

    // TODO: Get duck sounds
    @Override
	protected SoundEvent getAmbientSound() {
		return CanadaSounds.SOUND_GOOSE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return CanadaSounds.SOUND_GOOSE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return CanadaSounds.SOUND_GOOSE_DEATH;
	}

	public SoundEvent getWarningSound() {
		return CanadaSounds.SOUND_GOOSE_WARNING;
	}

    @Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(CanadaSounds.SOUND_DUCK_FOOTSTEP, 0.15F, 1.0F);
	}

	@Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(CanadaItems.GOOSE_SPAWN_EGG);
    }

	@Override
	public void tick() {
		super.tick();
		if (!this.getWorld().isClient) {
			this.tickAngerLogic((ServerWorld)this.getWorld(), false);
			// Make them give up if hurt too much
			if (this.getHealth() < 2 && this.isAngry()) {
				this.stopAnger();
			}
		}
	}

	@Override
	public void stopAnger() {
		this.dataTracker.set(ANGRY, false);
		Angerable.super.stopAnger();
	}

	@Override
	public void tickAngerLogic(ServerWorld world, boolean angerPersistent) {
		if (this.getHealth() <= 4) {
			this.stopAnger();
		}
		else {
			Angerable.super.tickAngerLogic(world, angerPersistent);
		}
	}

	@Override
	public boolean shouldAngerAt(LivingEntity entity, ServerWorld world) {
		if (this.getHealth() <= 4) {
			return false;
		}
		else {
			return Angerable.super.shouldAngerAt(entity, world);
		}
	}

	public boolean isAngry() {
		return this.dataTracker.get(ANGRY);
	}

	class AttackGoal extends MeleeAttackGoal {
		public AttackGoal() {
			super(GooseEntity.this, 1.25, true);
		}

		@Override
		public void tick() {
			super.tick();
			if (GooseEntity.this.getHealth() <= 4) {
				this.stop();
			}
		}

		@Override
		protected void attack(LivingEntity target) {
			if (this.canAttack(target)) {
				this.resetCooldown();
				this.mob.tryAttack(getServerWorld(this.mob), target);
			} else if (this.mob.squaredDistanceTo(target) < (target.getWidth() + 4.0F) * (target.getWidth() + 4.0F)) {
				if (this.isCooledDown()) {
					this.resetCooldown();
				}

				if (this.getCooldown() <= 5 && this.mob.getRandom().nextInt(2) == 0) {
					GooseEntity.this.playSound(GooseEntity.this.getWarningSound());
				}
			} else {
				this.resetCooldown();
			}
		}
	}

	class GooseRevengeGoal extends RevengeGoal {
		public GooseRevengeGoal() {
			super(GooseEntity.this);
		}

		@Override
		public void start() {
			super.start();
			if (GooseEntity.this.isBaby()) {
				this.callSameTypeForRevenge();
				this.stop();
			}
		}

		@Override
		protected void setMobEntityTarget(MobEntity mob, LivingEntity target) {
			if (mob instanceof GooseEntity && !mob.isBaby()) {
				super.setMobEntityTarget(mob, target);
			}
		}

		@Override
		public void tick() {
			super.tick();
			if (GooseEntity.this.getHealth() <= 4) {
				this.stop();
			}
		}
	}
	
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.readAngerFromNbt(this.getWorld(), nbt);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		this.writeAngerToNbt(nbt);
	}

	@Override
	public void chooseRandomAngerTime() {
		this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
	}

	@Override
	public void setAngerTime(int angerTime) {
		this.angerTime = angerTime;
		if (angerTime > 0) {
			this.dataTracker.set(ANGRY, true);
		}
	}

	@Override
	public int getAngerTime() {
		return this.angerTime;
	}

	@Override
	public void setAngryAt(@Nullable UUID angryAt) {
		this.angryAt = angryAt;
		if (angryAt != null) {
			this.dataTracker.set(ANGRY, true);
		}
	}

	@Nullable
	@Override
	public UUID getAngryAt() {
		return this.angryAt;
	}
}
