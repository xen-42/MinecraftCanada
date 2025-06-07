package xen42.canadamod.item;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ThermosItem extends BundleItem {
    @Nullable
    private ItemStack currentFoodStack;

    @Nullable
    private ConsumableComponent currentConsumableComponent;

    public ThermosItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        currentFoodStack = getFirstFood(user, itemStack);
        currentConsumableComponent = (ConsumableComponent)currentFoodStack.get(DataComponentTypes.CONSUMABLE);
        if (currentConsumableComponent != null) {
            return currentConsumableComponent.consume(user, itemStack, hand);
        } 

        return ActionResult.PASS;
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (currentConsumableComponent != null) {
            var finishedConsumption = currentConsumableComponent.finishConsumption(world, user, currentFoodStack.copy());
            
            // If it has a remainder give that instead
            var remainder = currentFoodStack.copy().finishUsing(world, user);
            if (remainder != currentFoodStack) {
                finishedConsumption = remainder;
            }

            BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)stack.get(DataComponentTypes.BUNDLE_CONTENTS);
            BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(bundleContentsComponent);
            builder.removeSelected();
            
            builder.add(finishedConsumption);
            stack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());

            currentConsumableComponent = null;
            currentFoodStack = null;
        }
        return stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        if (currentConsumableComponent != null) {
            return currentConsumableComponent.useAction();
        } 
        else {
            return UseAction.NONE;
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        if (currentConsumableComponent != null) {
            return currentConsumableComponent.getConsumeTicks();
        } else {
            return 0;
        }
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        this.currentConsumableComponent = null;
        return false;
    }

    private ItemStack getFirstFood(PlayerEntity player, ItemStack stack) {
        BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)stack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if (bundleContentsComponent != null && !bundleContentsComponent.isEmpty()) {
            Optional<ItemStack> optional = getFirstBundledStack(stack, player, bundleContentsComponent);
            if (optional.isPresent()) {
                return optional.get();
            } 
        }
        return ItemStack.EMPTY;
    }

    private static Optional<ItemStack> getFirstBundledStack(ItemStack stack, PlayerEntity player, BundleContentsComponent contents) {
        BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(contents);
        ItemStack itemStack = builder.removeSelected();
        if (itemStack != null) {
            return Optional.of(itemStack);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (currentConsumableComponent != null && currentConsumableComponent.shouldSpawnParticlesAndPlaySounds(remainingUseTicks)) {
            currentConsumableComponent.spawnParticlesAndPlaySound(user.getRandom(), user, currentFoodStack, 5);
        }
    }
}
