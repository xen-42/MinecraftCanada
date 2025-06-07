package xen42.canadamod.item;

import java.util.Optional;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.consume.UseAction;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.math.Fraction;

import org.jetbrains.annotations.Nullable;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import xen42.canadamod.CanadaItems;
import xen42.canadamod.CanadaMod;

public class ThermosItem extends Item {
    @Nullable
    private ItemStack currentFoodStack;

    @Nullable
    private ConsumableComponent currentConsumableComponent;

    private int selectedIndex = -1;

    public ThermosItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        selectFirstFood(itemStack, user);

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

            ThermosContentsComponent thermosContentsComponent = (ThermosContentsComponent)stack.get(CanadaItems.THERMOS_CONTENTS);
            ThermosContentsComponent.Builder builder = thermosContentsComponent.new Builder(thermosContentsComponent);

            CanadaMod.LOGGER.info("" + selectedIndex);
            builder.setSelectedStackIndex(selectedIndex);
            builder.removeSelected();
            
            builder.add(finishedConsumption);
            stack.set(CanadaItems.THERMOS_CONTENTS, builder.build());

            currentConsumableComponent = null;
            currentFoodStack = null;
            selectedIndex = -1;
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
        currentFoodStack = null;
        selectedIndex = -1;
        return false;
    }

    private void selectFirstFood(ItemStack stack, PlayerEntity player) {
        var thermosContentsComponent = stack.get(CanadaItems.THERMOS_CONTENTS);
        var builder = thermosContentsComponent.new Builder(thermosContentsComponent);

        for (int index = 0; index < 64; index++) {
            builder.setSelectedStackIndex(index);
            var itemStack = builder.removeSelected();

            if (itemStack != null && itemStack.get(DataComponentTypes.CONSUMABLE) != null) {
                currentFoodStack = itemStack;
                currentConsumableComponent = itemStack.get(DataComponentTypes.CONSUMABLE);
                selectedIndex = index;
                return;
            } 
        }

        currentFoodStack = null;
        currentConsumableComponent = null;
        selectedIndex = -1;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (currentConsumableComponent != null && currentConsumableComponent.shouldSpawnParticlesAndPlaySounds(remainingUseTicks)) {
            currentConsumableComponent.spawnParticlesAndPlaySound(user.getRandom(), user, currentFoodStack, 5);
        }
    }

    /// BELOW IS PURE BUNDLE ITEM

    public static final int TOOLTIP_STACKS_COLUMNS = 4;
    public static final int TOOLTIP_STACKS_ROWS = 3;
    public static final int MAX_TOOLTIP_STACKS_SHOWN = 12;
    public static final int MAX_TOOLTIP_STACKS_SHOWN_WHEN_TOO_MANY_TYPES = 11;
    private static final int FULL_ITEM_BAR_COLOR = ColorHelper.fromFloats(1.0F, 1.0F, 0.33F, 0.33F);
    private static final int ITEM_BAR_COLOR = ColorHelper.fromFloats(1.0F, 0.44F, 0.53F, 1.0F);

    public static float getAmountFilled(ItemStack stack) {
        ThermosContentsComponent thermosContentsComponent = (ThermosContentsComponent)stack.getOrDefault(CanadaItems.THERMOS_CONTENTS, ThermosContentsComponent.DEFAULT);
        return thermosContentsComponent.getOccupancy().floatValue();
    }

    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        ThermosContentsComponent thermosContentsComponent = (ThermosContentsComponent)stack.get(CanadaItems.THERMOS_CONTENTS);
        if (thermosContentsComponent == null) {
            return false;
        } else {
            ItemStack itemStack = slot.getStack();
            ThermosContentsComponent.Builder builder = thermosContentsComponent.new Builder(thermosContentsComponent);
            if (clickType == ClickType.LEFT && !itemStack.isEmpty()) {
                if (builder.add(slot, player) > 0) {
                    playInsertSound(player);
                } else {
                    playInsertFailSound(player);
                }

                stack.set(CanadaItems.THERMOS_CONTENTS, builder.build());
                this.onContentChanged(player);
                return true;
            } else if (clickType == ClickType.RIGHT && itemStack.isEmpty()) {
                ItemStack itemStack2 = builder.removeSelected();
                if (itemStack2 != null) {
                    ItemStack itemStack3 = slot.insertStack(itemStack2);
                    if (itemStack3.getCount() > 0) {
                        builder.add(itemStack3);
                    } else {
                        playRemoveOneSound(player);
                    }
                }

                stack.set(CanadaItems.THERMOS_CONTENTS, builder.build());
                this.onContentChanged(player);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.LEFT && otherStack.isEmpty()) {
            setSelectedStackIndex(stack, -1);
            return false;
        } else {
            ThermosContentsComponent thermosContentsComponent = (ThermosContentsComponent)stack.get(CanadaItems.THERMOS_CONTENTS);
            if (thermosContentsComponent == null) {
                return false;
            } else {
                ThermosContentsComponent.Builder builder = thermosContentsComponent.new Builder(thermosContentsComponent);
                if (clickType == ClickType.LEFT && !otherStack.isEmpty()) {
                    if (slot.canTakePartial(player) && builder.add(otherStack) > 0) {
                        playInsertSound(player);
                    } else {
                        playInsertFailSound(player);
                    }

                    stack.set(CanadaItems.THERMOS_CONTENTS, builder.build());
                    this.onContentChanged(player);
                    return true;
                } else if (clickType == ClickType.RIGHT && otherStack.isEmpty()) {
                    if (slot.canTakePartial(player)) {
                        ItemStack itemStack = builder.removeSelected();
                        if (itemStack != null) {
                            playRemoveOneSound(player);
                            cursorStackReference.set(itemStack);
                        }
                    }

                    stack.set(CanadaItems.THERMOS_CONTENTS, builder.build());
                    this.onContentChanged(player);
                    return true;
                } else {
                    setSelectedStackIndex(stack, -1);
                    return false;
                }
            }
        }
    }

    public boolean isItemBarVisible(ItemStack stack) {
        ThermosContentsComponent thermosContentsComponent = (ThermosContentsComponent)stack.getOrDefault(CanadaItems.THERMOS_CONTENTS, ThermosContentsComponent.DEFAULT);
        return thermosContentsComponent.getOccupancy().compareTo(Fraction.ZERO) > 0;
    }

    public int getItemBarStep(ItemStack stack) {
        ThermosContentsComponent thermosContentsComponent = (ThermosContentsComponent)stack.getOrDefault(CanadaItems.THERMOS_CONTENTS, ThermosContentsComponent.DEFAULT);
        return Math.min(1 + MathHelper.multiplyFraction(thermosContentsComponent.getOccupancy(), 12), 13);
    }

    public int getItemBarColor(ItemStack stack) {
        ThermosContentsComponent thermosContentsComponent = (ThermosContentsComponent)stack.getOrDefault(CanadaItems.THERMOS_CONTENTS, ThermosContentsComponent.DEFAULT);
        return thermosContentsComponent.getOccupancy().compareTo(Fraction.ONE) >= 0 ? FULL_ITEM_BAR_COLOR : ITEM_BAR_COLOR;
    }

    public static void setSelectedStackIndex(ItemStack stack, int selectedStackIndex) {
        ThermosContentsComponent thermosContentsComponent = (ThermosContentsComponent)stack.get(CanadaItems.THERMOS_CONTENTS);
        if (thermosContentsComponent != null) {
            ThermosContentsComponent.Builder builder = thermosContentsComponent.new Builder(thermosContentsComponent);
            builder.setSelectedStackIndex(selectedStackIndex);
            stack.set(CanadaItems.THERMOS_CONTENTS, builder.build());
        }
    }

    public static boolean hasSelectedStack(ItemStack stack) {
        ThermosContentsComponent ThermosContentsComponent = (ThermosContentsComponent)stack.get(CanadaItems.THERMOS_CONTENTS);
        return ThermosContentsComponent != null && ThermosContentsComponent.getSelectedStackIndex() != -1;
    }

    public static int getSelectedStackIndex(ItemStack stack) {
        ThermosContentsComponent thermosContentsComponent = (ThermosContentsComponent)stack.getOrDefault(CanadaItems.THERMOS_CONTENTS, ThermosContentsComponent.DEFAULT);
        return thermosContentsComponent.getSelectedStackIndex();
    }

    public static ItemStack getSelectedStack(ItemStack stack) {
        ThermosContentsComponent thermosContentsComponent = (ThermosContentsComponent)stack.get(CanadaItems.THERMOS_CONTENTS);
        return thermosContentsComponent != null && thermosContentsComponent.getSelectedStackIndex() != -1 ? thermosContentsComponent.get(thermosContentsComponent.getSelectedStackIndex()) : ItemStack.EMPTY;
    }

    public static int getNumberOfStacksShown(ItemStack stack) {
        ThermosContentsComponent thermosContentsComponent = (ThermosContentsComponent)stack.getOrDefault(CanadaItems.THERMOS_CONTENTS, ThermosContentsComponent.DEFAULT);
        return thermosContentsComponent.getNumberOfStacksShown();
    }

    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        TooltipDisplayComponent tooltipDisplayComponent = (TooltipDisplayComponent)stack.getOrDefault(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplayComponent.DEFAULT);
        return !tooltipDisplayComponent.shouldDisplay(CanadaItems.THERMOS_CONTENTS) ? Optional.empty() : Optional.ofNullable((ThermosContentsComponent)stack.get(CanadaItems.THERMOS_CONTENTS)).map(ThermosTooltipData::new);
    }

    public void onItemEntityDestroyed(ItemEntity entity) {
        ThermosContentsComponent thermosContentsComponent = (ThermosContentsComponent)entity.getStack().get(CanadaItems.THERMOS_CONTENTS);
        if (thermosContentsComponent != null) {
            entity.getStack().set(CanadaItems.THERMOS_CONTENTS, ThermosContentsComponent.DEFAULT);
            ItemUsage.spawnItemContents(entity, thermosContentsComponent.iterateCopy());
        }
    }

    private static void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    private static void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    private static void playInsertFailSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_INSERT_FAIL, 1.0F, 1.0F);
    }

    private void onContentChanged(PlayerEntity user) {
        ScreenHandler screenHandler = user.currentScreenHandler;
        if (screenHandler != null) {
            screenHandler.onContentChanged(user.getInventory());
        }

    }
}
