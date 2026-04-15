package xen42.canadamod.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import xen42.canadamod.CanadaItems;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.CanadaSounds;

public class GrizzlyEntity extends PolarBearEntity {

    public GrizzlyEntity(EntityType<? extends PolarBearEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createGrizzlyAttributes() {
		return AnimalEntity.createAnimalAttributes()
			.add(EntityAttributes.MAX_HEALTH, 30.0)
			.add(EntityAttributes.FOLLOW_RANGE, 20.0)
			.add(EntityAttributes.MOVEMENT_SPEED, 0.25)
			.add(EntityAttributes.ATTACK_DAMAGE, 6.0);
    }

    @Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return CanadaMod.GRIZZLY_ENTITY.create(world, SpawnReason.BREEDING);
	}
    
	@Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(CanadaItems.GRIZZLY_SPAWN_EGG);
    }

        @Override
	protected SoundEvent getAmbientSound() {
		return this.isBaby() ? CanadaSounds.ENTITY_GRIZZLY_BEAR_AMBIENT_BABY : CanadaSounds.ENTITY_GRIZZLY_BEAR_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return CanadaSounds.ENTITY_GRIZZLY_BEAR_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return CanadaSounds.ENTITY_GRIZZLY_BEAR_DEATH;
	}

    @Override
	protected void playWarningSound() {
		if (this.warningSoundCooldown <= 0) {
			this.playSound(CanadaSounds.ENTITY_GRIZZLY_BEAR_WARNING);
			this.warningSoundCooldown = 40;
		}
	}
}
