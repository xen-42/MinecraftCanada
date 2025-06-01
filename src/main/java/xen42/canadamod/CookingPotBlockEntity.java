package xen42.canadamod;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xen42.canadamod.recipe.CookingPotRecipe;
import xen42.canadamod.recipe.CookingPotRecipeInput;
import xen42.canadamod.screen.CookingPotScreenHandler;

public class CookingPotBlockEntity extends LockableContainerBlockEntity implements RecipeInputProvider {

    protected DefaultedList<ItemStack> inventory;
    private BlockState _blockState;

    int litTimeRemaining;
    int litTimeTotal;
    RecipeEntry currentRecipe;

    private final ServerRecipeManager.MatchGetter<CookingPotRecipeInput, ? extends CookingPotRecipe> matchGetter;

    protected CookingPotBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(CanadaMod.COOKING_POT_ENTITY, blockPos, blockState);
        this.inventory = DefaultedList.ofSize(8, ItemStack.EMPTY);
        _blockState = blockState;
        this.matchGetter = ServerRecipeManager.createCachedMatchGetter(CanadaMod.COOKING_POT_RECIPE_TYPE);
    }

    private CookingPotScreenHandler _handler;

    public boolean isBurning() {
        return litTimeRemaining > 0;
    }

    @Override
    public int size() {
        return 8;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        _handler = new CookingPotScreenHandler(syncId, playerInventory, this);
        return _handler;
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
        this.litTimeRemaining = nbt.getShort("lit_time_remaining").orElse((short)0);
        this.litTimeTotal = nbt.getShort("litTimeTotal").orElse((short)0);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) { 
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, this.inventory, registries);
        nbt.putShort("lit_time_remaining", (short)this.litTimeRemaining);
        nbt.putShort("litTimeTotal", (short)this.litTimeTotal);
    }

    public static void tick(ServerWorld world, BlockPos pos, BlockState state, CookingPotBlockEntity blockEntity) {
        if (blockEntity.isBurning()) {
            blockEntity.litTimeRemaining--;
        }

        var didUpdate = false;

        didUpdate = craftRecipe(blockEntity, world);

        var isLit = state.get(CookingPotBlock.LIT);
        if (isLit != blockEntity.isBurning()) {
            world.setBlockState(pos, state.with(CookingPotBlock.LIT, isLit));
            didUpdate = true;
        }

        if (didUpdate) {
            markDirty(world, pos, state);
        }
    }

    private static boolean craftRecipe(CookingPotBlockEntity blockEntity, ServerWorld world) {
        if (blockEntity.getStack(CookingPotScreenHandler.OUTPUT_SLOT).isEmpty()) {
            var input = new CookingPotRecipeInput(blockEntity.inventory);
            var recipeEntry = blockEntity.matchGetter.getFirstMatch(input, world).orElse(null);
            if (recipeEntry != null) {
                var resultItem = recipeEntry.value().result;

                var currentOutput = blockEntity.getStack(CookingPotScreenHandler.OUTPUT_SLOT);
                 var canAcceptOutput = currentOutput.isEmpty() || 
                    (currentOutput.isOf(resultItem.getItem()) && currentOutput.getMaxCount() < currentOutput.getCount() + resultItem.getCount());

                if (canAcceptOutput) {
                    var finalResult = resultItem.copy();
                    if (!currentOutput.isEmpty()) {
                        finalResult = new ItemStack(finalResult.getItem(), finalResult.getCount() + currentOutput.getCount());
                    }

                    blockEntity.setStack(CookingPotScreenHandler.OUTPUT_SLOT, finalResult);
                    for (var slotIndex : new int[] { 1, 2, 3, 4, 5, 6 }) {
                        var slot = blockEntity.inventory.get(slotIndex);
                        if (!slot.isEmpty()) {
                            slot.decrement(1);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void provideRecipeInputs(RecipeFinder finder) {
        for (ItemStack itemStack : this.inventory) {
            finder.addInput(itemStack); 
        }
    }
}
