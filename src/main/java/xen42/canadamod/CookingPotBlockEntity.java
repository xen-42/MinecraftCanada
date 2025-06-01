package xen42.canadamod;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import xen42.canadamod.screen.CookingPotScreenHandler;

public class CookingPotBlockEntity extends LockableContainerBlockEntity {

    protected DefaultedList<ItemStack> inventory;
    private BlockState _blockState;

    protected CookingPotBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(CanadaMod.COOKING_POT_ENTITY, blockPos, blockState);
        this.inventory = DefaultedList.ofSize(8, ItemStack.EMPTY);
        _blockState = blockState;
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new CookingPotScreenHandler(syncId, playerInventory, this);
    }

    @Override
    protected Text getContainerName() {
        return ((CookingPotBlock)(this._blockState.getBlock())).getTitle();
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean supports(BlockState state) {
        return CanadaMod.COOKING_POT_ENTITY.supports(state);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.inventory = DefaultedList.ofSize(size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory, registries);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) { 
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, this.inventory, registries);
    }
}
