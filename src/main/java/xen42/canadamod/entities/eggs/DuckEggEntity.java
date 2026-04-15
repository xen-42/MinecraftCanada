package xen42.canadamod.entities.eggs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xen42.canadamod.CanadaItems;
import xen42.canadamod.CanadaMod;

public class DuckEggEntity extends CustomEggEntity {

    public DuckEggEntity(EntityType<? extends ThrownItemEntity> entity, World world) {
        super(world);
    }

    public DuckEggEntity(World world) {
        super(world);
    }

	public DuckEggEntity(World world, LivingEntity owner, ItemStack stack) {
        super(world, owner, stack);
    }

    public DuckEggEntity(World world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, stack);
    }

    @Override
	protected Item getDefaultItem() {
		return CanadaItems.DUCK_EGG;
	}

    @Override
	public EntityType<? extends AnimalEntity> getHatchedEntity() {
		return CanadaMod.DUCK_ENTITY;
	}
    
}
