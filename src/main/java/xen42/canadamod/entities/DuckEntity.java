package xen42.canadamod.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import xen42.canadamod.CanadaItems;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.CanadaSounds;
import xen42.canadamod.CanadaTags;

public class DuckEntity extends ChickenEntity {

    public DuckEntity(EntityType<? extends ChickenEntity> entityType, World world) {
        super(entityType, world);
        // Override that chickens don't like water
        this.setPathfindingPenalty(PathNodeType.WATER, 0f);
    }
    
    public static DefaultAttributeContainer.Builder createDuckAttributes() {
        return AnimalEntity.createAnimalAttributes()
            .add(EntityAttributes.MAX_HEALTH, 4.0f)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
            .add(EntityAttributes.WATER_MOVEMENT_EFFICIENCY, 2f)
            .add(EntityAttributes.OXYGEN_BONUS);
    }

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4));
		this.goalSelector.add(1, new FleeEntityGoal(this, FoxEntity.class, 8.0F, 1.6, 1.4, entity -> true));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
		this.goalSelector.add(3, new TemptGoal(this, 1.0, stack -> isBreedingItem(stack), false));
		this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(7, new LookAroundGoal(this));
	}

    @Override
	public ChickenEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		ChickenEntity chickenEntity = CanadaMod.DUCK_ENTITY.create(serverWorld, SpawnReason.BREEDING);

		return chickenEntity;
	}

    @Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.isIn(CanadaTags.ItemTags.DUCK_FOOD);
	}

    @Override
	public void tickMovement() {
		super.tickMovement();
		
		this.fallDistance = 0.0;

		this.lastFlapProgress = this.flapProgress;
		this.lastMaxWingDeviation = this.maxWingDeviation;
		this.maxWingDeviation = this.maxWingDeviation + (this.isOnGround() && !this.isTouchingWater() ? -1.0F : 4.0F) * 0.3F;
		this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0F, 1.0F);
		if (!this.isOnGround() && this.flapSpeed < 1.0F && !this.isTouchingWater()) {
			this.flapSpeed = 1.0F;
		}

		this.flapSpeed *= 0.9F;
		Vec3d vec3d = this.getVelocity();
		if (!this.isOnGround() && vec3d.y < 0.0) {
			this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
		}

		this.flapProgress = this.flapProgress + this.flapSpeed * 2.0F;
		if (this.getWorld() instanceof ServerWorld serverWorld && this.isAlive() && !this.isBaby() && !this.hasJockey() && --this.eggLayTime <= 0) {
            var item = this.dropItem(serverWorld, CanadaItems.DUCK_EGG);
            item.setPosition(this.getPos());

            this.playSound(CanadaSounds.SOUND_DUCK_PLOP, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
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

    @Override
	protected SoundEvent getAmbientSound() {
		return CanadaSounds.SOUND_DUCK_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return CanadaSounds.SOUND_DUCK_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return CanadaSounds.SOUND_DUCK_DEATH;
	}

    @Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(CanadaSounds.SOUND_DUCK_FOOTSTEP, 0.15F, 1.0F);
	}

	@Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(CanadaItems.DUCK_SPAWN_EGG);
    }
}
