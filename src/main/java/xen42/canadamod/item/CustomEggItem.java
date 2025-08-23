package xen42.canadamod.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import xen42.canadamod.entities.CustomEggEntity;

public class CustomEggItem<e extends AnimalEntity>  extends Item implements ProjectileItem {

	public static final float POWER = 1.5F;
    private EntityType<e> spawnEntityType;

	public CustomEggItem(Item.Settings settings, EntityType<e> duckEntity) {
		super(settings);
        this.spawnEntityType = duckEntity;
	}

    @Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		world.playSound(
			null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
		);
		if (world instanceof ServerWorld serverWorld) {
			ProjectileEntity.spawnWithVelocity(CustomEggEntity::new, serverWorld, itemStack, user, 0.0F, 1.5F, 1.0F);
		}

		user.incrementStat(Stats.USED.getOrCreateStat(this));
		itemStack.decrementUnlessCreative(1, user);
		return ActionResult.SUCCESS;
	}

	@Override
	public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
		return new CustomEggEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
	}
    
}
