package xen42.canadamod;

import com.ibm.icu.text.AlphabeticIndex.Bucket;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.FuelRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import xen42.canadamod.recipe.CookingPotRecipe;
import xen42.canadamod.recipe.CookingPotRecipeInput;
import xen42.canadamod.screen.CookingPotScreenHandler;

public class CookingPotBlockEntity extends LockableContainerBlockEntity implements RecipeInputProvider, SidedInventory {
    protected DefaultedList<ItemStack> inventory;
    private BlockState _blockState;

    public int cookTimeRemaining;
    public int cookTimeTotal;

    public int burnTimeRemaining;
    public int burnTimeTotal;

    public  RegistryKey<Recipe<?>> currentRecipeID;

    private final ServerRecipeManager.MatchGetter<CookingPotRecipeInput, ? extends CookingPotRecipe> matchGetter;

    protected final PropertyDelegate propertyDelegate;

    protected CookingPotBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(CanadaMod.COOKING_POT_ENTITY, blockPos, blockState);
        this.propertyDelegate = new ArrayPropertyDelegate(4);

        this.inventory = DefaultedList.ofSize(8, ItemStack.EMPTY);
        _blockState = blockState;
        this.matchGetter = ServerRecipeManager.createCachedMatchGetter(CanadaMod.COOKING_POT_RECIPE_TYPE);
    }

    private CookingPotScreenHandler _handler;

    public boolean isCooking() {
        return cookTimeRemaining > 0;
    }

    public boolean hasFuel() {
        return burnTimeRemaining > 0;
    }

    @Override
    public int size() {
        return 8;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        _handler = new CookingPotScreenHandler(syncId, playerInventory, ScreenHandlerContext.EMPTY, this, this.propertyDelegate);
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
        this.cookTimeRemaining = nbt.getShort("cookTimeRemaining").orElse((short)0);
        this.cookTimeTotal = nbt.getShort("cookTimeTotal").orElse((short)0);
        this.burnTimeRemaining = nbt.getShort("burnTimeRemaining").orElse((short)0);
        this.burnTimeTotal = nbt.getShort("burnTimeTotal").orElse((short)0);
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
        nbt.putShort("cookTimeRemaining", (short)this.cookTimeRemaining);
        nbt.putShort("cookTimeTotal", (short)this.cookTimeTotal);
        nbt.putShort("burnTimeRemaining", (short)this.burnTimeRemaining);
        nbt.putShort("burnTimeTotal", (short)this.burnTimeTotal);
    }

    public static void tick(ServerWorld world, BlockPos pos, BlockState state, CookingPotBlockEntity blockEntity) {
        var wasBurning = blockEntity.isCooking();

        // Todo: only check this when the inventory actually changed
        var inputRecipe = getRecipeFromInputs(blockEntity, world);
        var activeRecipe = getCurrentRecipe(blockEntity, world);
        var currentOutput = blockEntity.getStack(CookingPotScreenHandler.OUTPUT_SLOT);
        var currentRecipeResult = inputRecipe == null ? ItemStack.EMPTY : inputRecipe.value().result;

        var canAcceptOutput = blockEntity.canAcceptOutput(currentOutput, currentRecipeResult);

        if (currentRecipeResult == ItemStack.EMPTY) {
            blockEntity.cookTimeRemaining = 0;
            blockEntity.cookTimeTotal = 0;
        }

        // Only progress or use fuel if we actually can create an output
        if (canAcceptOutput) {
            var fuelStack = blockEntity.getStack(CookingPotScreenHandler.FUEL_SLOT);
            if (!blockEntity.hasFuel() && !fuelStack.isEmpty()) {
                blockEntity.burnTimeRemaining = world.getFuelRegistry().getFuelTicks(fuelStack);
                blockEntity.burnTimeTotal = blockEntity.burnTimeRemaining;
                if (fuelStack.getRecipeRemainder().isEmpty()) {
                    fuelStack.decrement(1);
                }
                else {
                    blockEntity.inventory.set(CookingPotScreenHandler.FUEL_SLOT, fuelStack.getRecipeRemainder());
                }
            }

            if (blockEntity.isCooking() && blockEntity.hasFuel()) {
                blockEntity.cookTimeRemaining--;
                blockEntity.burnTimeRemaining-=5;
            }
        }

        var didUpdate = false;

        // Just finished crafting
        if (wasBurning && !blockEntity.isCooking()) {
            didUpdate = craftRecipe(blockEntity, world);
        }

        var shouldBeCooking = inputRecipe != null;
        var isCooking = blockEntity.isCooking() && activeRecipe == inputRecipe;

        if (shouldBeCooking && !isCooking) {
            // Debug: 2 seconds to craft
            blockEntity.cookTimeRemaining = 40;
            blockEntity.cookTimeTotal = 40;
            setCurrentRecipe(inputRecipe, blockEntity, world);
        }
        else if (!shouldBeCooking && isCooking) {
            blockEntity.cookTimeRemaining = 0;
            setCurrentRecipe(null, blockEntity, world);
        }

        var isLit = state.get(CookingPotBlock.LIT);
        var shouldBeLit = blockEntity.isCooking() && blockEntity.hasFuel() && canAcceptOutput;
        if (isLit != shouldBeLit) {
            world.setBlockState(pos, state.with(CookingPotBlock.LIT, shouldBeLit));
            didUpdate = true;
        }

        if (didUpdate) {
            markDirty(world, pos, state);
        }

        blockEntity.propertyDelegate.set(0, blockEntity.burnTimeRemaining);
        blockEntity.propertyDelegate.set(1, blockEntity.burnTimeTotal);
        blockEntity.propertyDelegate.set(2, blockEntity.cookTimeRemaining);
        blockEntity.propertyDelegate.set(3, blockEntity.cookTimeTotal);
    }

    @SuppressWarnings("unchecked")
    private static RecipeEntry<CookingPotRecipe> getRecipeFromInputs(CookingPotBlockEntity blockEntity, ServerWorld world) {
        var input = new CookingPotRecipeInput(blockEntity.inventory);
        var recipeEntry = blockEntity.matchGetter.getFirstMatch(input, world).orElse(null);
        return (RecipeEntry<CookingPotRecipe>)recipeEntry;
    }

    private static void setCurrentRecipe(RecipeEntry<CookingPotRecipe> recipe, CookingPotBlockEntity blockEntity, ServerWorld world) {
        blockEntity.currentRecipeID = recipe == null ? null : recipe.id();
    }

    @SuppressWarnings("unchecked")
    private static RecipeEntry<CookingPotRecipe> getCurrentRecipe(CookingPotBlockEntity blockEntity, ServerWorld world) {
        var recipe = world.getRecipeManager().get(blockEntity.currentRecipeID).orElse(null);
        if (recipe == null) {
            return null;
        }
        else {
            return (RecipeEntry<CookingPotRecipe>)recipe;
        }
    }

    private boolean canAcceptOutput(ItemStack currentOutput, ItemStack resultItem) {
        return currentOutput.isEmpty() || 
                (currentOutput.isOf(resultItem.getItem()) && currentOutput.getMaxCount() > currentOutput.getCount() + resultItem.getCount());
    }

    /// Called when crafting bar time is up
    private static boolean craftRecipe(CookingPotBlockEntity blockEntity, ServerWorld world) {
        var recipeEntry = getRecipeFromInputs(blockEntity, world);
        if (recipeEntry == null) {
            return false;
        }
        var recipe = recipeEntry.value();

        if (recipe != null) {
            var resultItem = recipe.result;

            var currentOutput = blockEntity.getStack(CookingPotScreenHandler.OUTPUT_SLOT);

            if (blockEntity.canAcceptOutput(currentOutput, resultItem)) {
                var finalResult = resultItem.copy();
                if (!currentOutput.isEmpty()) {
                    finalResult = new ItemStack(finalResult.getItem(), finalResult.getCount() + currentOutput.getCount());
                }

                blockEntity.setStack(CookingPotScreenHandler.OUTPUT_SLOT, finalResult);
                for (var slotIndex : new int[] { 2, 3, 4, 5, 6 }) {
                    var slot = blockEntity.inventory.get(slotIndex);

                    if (!slot.isEmpty()) {
                        if (slot.getRecipeRemainder().isEmpty()) {
                            slot.decrement(1);
                        }
                        else {
                            blockEntity.inventory.set(slotIndex, slot.getRecipeRemainder());
                        }
                    }
                }

                setCurrentRecipe(null, blockEntity, world);

                return true;
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

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return (slot == CookingPotScreenHandler.OUTPUT_SLOT) || stack.isOf(Items.BUCKET);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        if (CookingPotScreenHandler.isContainer(stack)) {
            return slot == CookingPotScreenHandler.CONTAINER_SLOT;
        }
        else if (world.getFuelRegistry().isFuel(stack)) {
            return slot == CookingPotScreenHandler.FUEL_SLOT;
        }

        return false;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return new int[] { CookingPotScreenHandler.OUTPUT_SLOT, 3, 4, 5, 6 };
        }
        else {
            return new int[] { CookingPotScreenHandler.CONTAINER_SLOT, CookingPotScreenHandler.FUEL_SLOT };
        }
    }
}
