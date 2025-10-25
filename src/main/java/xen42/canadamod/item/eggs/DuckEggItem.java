package xen42.canadamod.item.eggs;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import xen42.canadamod.entities.eggs.DuckEggEntity;

public class DuckEggItem extends CustomEggItem {
    public DuckEggItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ProjectileEntity createEntity(World world, LivingEntity owner, ItemStack stack) {
		return new DuckEggEntity(world, owner, stack);
	}

	@Override
	public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
		return new DuckEggEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
	}
}
