package xen42.canadamod.entities;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import xen42.canadamod.CanadaMod;

public class MooseEntity extends AnimalEntity {

    public MooseEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    private static final EntityDimensions BABY_BASE_DIMENSIONS = EntityType.COW.getDimensions().scaled(0.5F).withEyeHeight(0.665F);

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25D));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(4, new TemptGoal(this, 1.2D, stack -> this.isBreedingItem(stack), false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createMooseAttributes() {
        return AnimalEntity.createAnimalAttributes().add(EntityAttributes.MAX_HEALTH, 25.0f).add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
            .add(EntityAttributes.WATER_MOVEMENT_EFFICIENCY, 2f);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SEAGRASS);
    }

    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return (MooseEntity)CanadaMod.MOOSE_ENTITY.create(world, SpawnReason.BREEDING);
    }

    @Override
    public EntityDimensions getBaseDimensions(EntityPose pose) {
        return isBaby() ? BABY_BASE_DIMENSIONS : super.getBaseDimensions(pose);
    }
}
