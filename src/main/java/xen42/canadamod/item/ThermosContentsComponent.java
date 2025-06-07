package xen42.canadamod.item;

import com.mojang.serialization.Codec;

import net.minecraft.item.tooltip.TooltipData;
import com.google.common.collect.Lists;
import com.mojang.serialization.DataResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.screen.slot.Slot;

import org.apache.commons.lang3.math.Fraction;
import org.jetbrains.annotations.Nullable;

/// Modified from BundleContentsComponent because its marked final and can't be extended
public class ThermosContentsComponent implements TooltipData {
    public static final ThermosContentsComponent DEFAULT = new ThermosContentsComponent(List.of());
    public static final Codec<ThermosContentsComponent> CODEC;
    public static final PacketCodec<RegistryByteBuf, ThermosContentsComponent> PACKET_CODEC;
    private static final int ADD_TO_NEW_SLOT = -1;
    public static final int field_52591 = -1;
    final List<ItemStack> stacks;
    final Fraction occupancy;
    final int selectedStackIndex;

    ThermosContentsComponent(List<ItemStack> stacks, Fraction occupancy, int selectedStackIndex) {
        this.stacks = stacks;
        this.occupancy = occupancy;
        this.selectedStackIndex = selectedStackIndex;
    }

    private static DataResult<ThermosContentsComponent> validateOccupancy(List<ItemStack> stacks) {
        try {
            Fraction fraction = calculateOccupancy(stacks);
            return DataResult.success(new ThermosContentsComponent(stacks, fraction, -1));
        } catch (ArithmeticException var2) {
            return DataResult.error(() -> {
            return "Excessive total bundle weight";
            });
        }
    }

    public ThermosContentsComponent(List<ItemStack> stacks) {
        this(stacks, calculateOccupancy(stacks), -1);
    }

    private static Fraction calculateOccupancy(List<ItemStack> stacks) {
        Fraction fraction = Fraction.ZERO;

        ItemStack itemStack;
        for(Iterator var2 = stacks.iterator(); var2.hasNext(); fraction = fraction.add(getOccupancy(itemStack).multiplyBy(Fraction.getFraction(itemStack.getCount(), 1)))) {
            itemStack = (ItemStack)var2.next();
        }

        return fraction;
    }

    public static Fraction getOccupancy(ItemStack stack) {
        return Fraction.getFraction(1, 64);
    }

    public static boolean canBeBundled(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem().canBeNested();
    }

    public int getNumberOfStacksShown() {
        int i = this.size();
        int j = i > 12 ? 11 : 12;
        int k = i % 4;
        int l = k == 0 ? 0 : 4 - k;
        return Math.min(i, j - l);
    }

    public ItemStack get(int index) {
        return (ItemStack)this.stacks.get(index);
    }

    public Stream<ItemStack> stream() {
        return this.stacks.stream().map(ItemStack::copy);
    }

    public Iterable<ItemStack> iterate() {
        return this.stacks;
    }

    public Iterable<ItemStack> iterateCopy() {
        return Lists.transform(this.stacks, ItemStack::copy);
    }

    public int size() {
        return this.stacks.size();
    }

    public Fraction getOccupancy() {
        return this.occupancy;
    }

    public boolean isEmpty() {
        return this.stacks.isEmpty();
    }

    public int getSelectedStackIndex() {
        return this.selectedStackIndex;
    }

    public boolean hasSelectedStack() {
        return this.selectedStackIndex != -1;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof ThermosContentsComponent)) {
            return false;
        } else {
            ThermosContentsComponent ThermosContentsComponent = (ThermosContentsComponent)o;
            return this.occupancy.equals(ThermosContentsComponent.occupancy) && ItemStack.stacksEqual(this.stacks, ThermosContentsComponent.stacks);
        }
    }

    public int hashCode() {
        return ItemStack.listHashCode(this.stacks);
    }

    public String toString() {
        return "ThermosContents" + String.valueOf(this.stacks);
    }

    static {
        CODEC = ItemStack.CODEC.listOf().flatXmap(ThermosContentsComponent::validateOccupancy, (component) -> {
            return DataResult.success(component.stacks);
        });
        PACKET_CODEC = ItemStack.PACKET_CODEC.collect(PacketCodecs.toList()).xmap(ThermosContentsComponent::new, (component) -> {
            return component.stacks;
        });
    }

    public class Builder {
        private final List<ItemStack> stacks;
        private Fraction occupancy;
        private int selectedStackIndex;

        public Builder(ThermosContentsComponent base) {
            this.stacks = new ArrayList(base.stacks);
            this.occupancy = base.occupancy;
            this.selectedStackIndex = base.selectedStackIndex;
        }

        public Builder clear() {
            this.stacks.clear();
            this.occupancy = Fraction.ZERO;
            this.selectedStackIndex = -1;
            return this;
        }

        private int getInsertionIndex(ItemStack stack) {
            if (!stack.isStackable()) {
                return -1;
            } else {
                for(int i = 0; i < this.stacks.size(); ++i) {
                    if (ItemStack.areItemsAndComponentsEqual((ItemStack)this.stacks.get(i), stack)) {
                    return i;
                    }
                }

                return -1;
            }
        }

        private int getMaxAllowed(ItemStack stack) {
            Fraction fraction = Fraction.ONE.subtract(this.occupancy);
            return Math.max(fraction.divideBy(ThermosContentsComponent.getOccupancy(stack)).intValue(), 0);
        }

        public int add(ItemStack stack) {
            if (!ThermosContentsComponent.canBeBundled(stack)) {
                return 0;
            } else {
                int i = Math.min(stack.getCount(), this.getMaxAllowed(stack));
                if (i == 0) {
                    return 0;
                } else {
                    this.occupancy = this.occupancy.add(ThermosContentsComponent.getOccupancy(stack).multiplyBy(Fraction.getFraction(i, 1)));
                    int j = this.getInsertionIndex(stack);
                    if (j != -1) {
                    ItemStack itemStack = (ItemStack)this.stacks.remove(j);
                    ItemStack itemStack2 = itemStack.copyWithCount(itemStack.getCount() + i);
                    stack.decrement(i);
                    this.stacks.add(0, itemStack2);
                    } else {
                    this.stacks.add(0, stack.split(i));
                    }

                    return i;
                }
            }
        }

        public int add(Slot slot, PlayerEntity player) {
            ItemStack itemStack = slot.getStack();
            int i = this.getMaxAllowed(itemStack);
            return ThermosContentsComponent.canBeBundled(itemStack) ? this.add(slot.takeStackRange(itemStack.getCount(), i, player)) : 0;
        }

        public void setSelectedStackIndex(int selectedStackIndex) {
            this.selectedStackIndex = this.selectedStackIndex != selectedStackIndex && !this.isOutOfBounds(selectedStackIndex) ? selectedStackIndex : -1;
        }

        private boolean isOutOfBounds(int index) {
            return index < 0 || index >= this.stacks.size();
        }

        @Nullable
        public ItemStack removeSelected() {
            if (this.stacks.isEmpty()) {
                return null;
            } else {
                int i = this.isOutOfBounds(this.selectedStackIndex) ? 0 : this.selectedStackIndex;
                ItemStack itemStack = ((ItemStack)this.stacks.remove(i)).copy();
                this.occupancy = this.occupancy.subtract(ThermosContentsComponent.getOccupancy(itemStack).multiplyBy(Fraction.getFraction(itemStack.getCount(), 1)));
                this.setSelectedStackIndex(-1);
                return itemStack;
            }
        }

        public Fraction getOccupancy() {
            return this.occupancy;
        }

        public ThermosContentsComponent build() {
            return new ThermosContentsComponent(List.copyOf(this.stacks), this.occupancy, this.selectedStackIndex);
        }
        }
}
