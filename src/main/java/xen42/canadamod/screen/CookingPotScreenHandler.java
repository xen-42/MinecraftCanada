package xen42.canadamod.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.book.RecipeBookType;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import xen42.canadamod.CanadaBlocks;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.recipe.CookingPotRecipe;

public class CookingPotScreenHandler extends AbstractRecipeScreenHandler {

    public final static int OUTPUT_SLOT = 0;
    public final static int FUEL_SLOT = 1;
    public final static int CONTAINER_SLOT = 2;    
    public final static int INPUT_SLOTS_START = 3;
    public final static int INPUT_SLOTS_END = 6;

    public static final int INVENTORY_SLOTS_START = 7;
    public static final int INVENTORY_SLOTS_END = 33;
    public static final int HOTBAR_SLOTS_START = 34;
    public static final int HOTBAR_SLOTS_END = 42;

    public static final int MAX_WIDTH_AND_HEIGHT = 2;

    private final PropertyDelegate propertyDelegate;

    public Inventory inventory;

    public ScreenHandlerContext context;

    private final PlayerEntity player;

    private Slot[] _slots;
    private Slot _fuelSlot;
    private Slot _containerSlot;
    private Slot _outputSlot;

    public List<Slot> getInputSlots() {
        return Arrays.asList(_slots);
    }

    public Slot getFuelSlot() {
        return _fuelSlot;
    }

    public Slot getContainerSlot() {
        return _containerSlot;
    }

    public Slot getOutputSlot() {
        return _outputSlot;
    }

    public CookingPotScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, null, new ArrayPropertyDelegate(4));
    }

    public CookingPotScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, Inventory inventory, PropertyDelegate delegate) {
        super(CanadaMod.COOKING_POT_SCREEN_HANDLER_TYPE, syncId);
        this.inventory = inventory == null ? new CookingPotSimpleInventory(this, 8) : inventory;
        this.context = context;
        this.player = playerInventory.player;
        
        checkSize(this.inventory, 8);
        this.propertyDelegate = delegate;

        _outputSlot = this.addSlot(new OutputSlot(this, this.inventory, OUTPUT_SLOT, 141, 35));
        _fuelSlot = this.addSlot(new FuelSlot(this, this.inventory, FUEL_SLOT, 80, 61));
        _containerSlot = this.addSlot(new ContainerSlot(this, this.inventory, CONTAINER_SLOT, 23, 35));

        _slots = new Slot[] {
            this.addSlot(new CustomSlot(this, this.inventory, INPUT_SLOTS_START, 71, 8)),
            this.addSlot(new CustomSlot(this, this.inventory, INPUT_SLOTS_START+1, 89, 8)),
            this.addSlot(new CustomSlot(this, this.inventory, INPUT_SLOTS_START+2, 71, 26)),
            this.addSlot(new CustomSlot(this, this.inventory, INPUT_SLOTS_START+3, 89, 26))
        };

        this.addPlayerSlots(playerInventory, 8, 84);

        this.addProperties(this.propertyDelegate);
    }

    private int getSlotWithIngredient(Ingredient ingredient) {
        for (int i = 0; i < slots.size(); i++) {
            if (Ingredient.matches(Optional.of(ingredient), this.slots.get(i).getStack())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public PostFillAction fillInputSlots(boolean craftAll, boolean creative, RecipeEntry<?> recipeEntry, ServerWorld world, PlayerInventory inventory) {
        var recipe = (CookingPotRecipe)recipeEntry.value();

        // Clear stuff
        this.quickMove(player, CONTAINER_SLOT);
        for (int i = INPUT_SLOTS_START; i <= INPUT_SLOTS_END; i++) {
            this.quickMove(player, i);
        }

        var outputSlot = getSlot(OUTPUT_SLOT);
        if (!outputSlot.getStack().isEmpty() && !outputSlot.getStack().isOf(recipe.result.getItem())) {
            this.quickMove(player, OUTPUT_SLOT);
        }

        var bottleSlot = getSlotWithIngredient(Ingredient.ofItem(Items.GLASS_BOTTLE));
        var bowlSlot = getSlotWithIngredient(Ingredient.ofItem(Items.BOWL));
        var ingredientSlots = new int[recipe.ingredients.size()];
        
        for (int j = 0; j < recipe.ingredients.size(); j++) {
            ingredientSlots[j] = getSlotWithIngredient(recipe.ingredients.get(j));
        }

        if ((recipe.requiresBottle && bottleSlot == -1) || (recipe.requiresBowl && bowlSlot == -1) || Arrays.stream(ingredientSlots).anyMatch(x -> x == -1)) {
            return AbstractRecipeScreenHandler.PostFillAction.PLACE_GHOST_RECIPE;
        }
        else {
            if (recipe.requiresBottle) {
                this.quickMove(player, bottleSlot);
            }
            if (recipe.requiresBowl) {
                this.quickMove(player, bowlSlot);
            }
            for (var slot : ingredientSlots) {
                this.quickMove(player, slot);
            }
            return AbstractRecipeScreenHandler.PostFillAction.NOTHING;
        }
    }

    public boolean isCooking() {
        return this.getCookProgress() > 0f;
    }

    public float getFuelProgress() {
        return (float)this.propertyDelegate.get(0) / (float)this.propertyDelegate.get(1); 
    }

    public float getCookProgress() {
        return (float)this.propertyDelegate.get(2) / (float)this.propertyDelegate.get(3); 
    }

    @Override
    public RecipeBookType getCategory() {
        return RecipeBookType.CRAFTING; // Return crafting because making a RecipeBookType is impossible.
    }

    @Override
    public void populateRecipeFinder(RecipeFinder finder) {
        if (this.inventory instanceof RecipeInputProvider recipeInputProvider) {
            recipeInputProvider.provideRecipeInputs(finder);
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, CanadaBlocks.COOKING_POT);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slotAtIndex = this.slots.get(slot);
        if (slotAtIndex != null && slotAtIndex.hasStack() && slotAtIndex.canTakeItems(player)) {
            ItemStack itemStackAtIndex = slotAtIndex.getStack();
            itemStack = itemStackAtIndex.copy();
            if (slot == OUTPUT_SLOT) {
                itemStackAtIndex.getItem().onCraftByPlayer(itemStackAtIndex, player);
                if (!this.insertItem(itemStackAtIndex, INVENTORY_SLOTS_START, HOTBAR_SLOTS_END, true)) {
                    return ItemStack.EMPTY;
                }

                slotAtIndex.onQuickTransfer(itemStackAtIndex, itemStack);
            } else if (slot >= INVENTORY_SLOTS_START && slot < HOTBAR_SLOTS_END) {
                if (isContainer(itemStackAtIndex)) {
                    this.insertItem(itemStackAtIndex, CONTAINER_SLOT, CONTAINER_SLOT + 1, false);
                }
                else if (isFuel(itemStackAtIndex)) {
                    this.insertItem(itemStackAtIndex, FUEL_SLOT, FUEL_SLOT + 1, false);
                }
                else {
                    if (!this.insertItem(itemStackAtIndex, INPUT_SLOTS_START, INVENTORY_SLOTS_START, false)) {
                        if (slot < HOTBAR_SLOTS_START) {
                            if (!this.insertItem(itemStackAtIndex, HOTBAR_SLOTS_START, HOTBAR_SLOTS_END, false)) {
                                return ItemStack.EMPTY;
                            }
                        } else if (!this.insertItem(itemStackAtIndex, INVENTORY_SLOTS_START, HOTBAR_SLOTS_START, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (!this.insertItem(itemStackAtIndex, INVENTORY_SLOTS_START, HOTBAR_SLOTS_END, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStackAtIndex.isEmpty()) {
                slotAtIndex.setStack(ItemStack.EMPTY);
            } else {
                slotAtIndex.markDirty();
            }

            if (itemStackAtIndex.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slotAtIndex.onTakeItem(player, itemStackAtIndex);
            if (slot == OUTPUT_SLOT) {
                player.dropItem(itemStackAtIndex, false);
            }
        }

        return itemStack;
    }

    public boolean isFuel(ItemStack item) {
        return this.player.getWorld().getFuelRegistry().isFuel(item);
    }

    public boolean isContainer(ItemStack item) {
        return item.isOf(Items.BOWL) || item.isOf(Items.GLASS_BOTTLE);
    }

    private class CookingPotSimpleInventory extends SimpleInventory implements RecipeInputInventory {
        private ScreenHandler _screen;

        CookingPotSimpleInventory(CookingPotScreenHandler screen, int size) {
            super(size);
            _screen = screen;
        }

        public void markDirty() {
            _screen.onContentChanged(this);
            super.markDirty();
        }

        @Override
        public int getWidth() {
            return MAX_WIDTH_AND_HEIGHT;
        }

        @Override
        public int getHeight() {
            return MAX_WIDTH_AND_HEIGHT;
        }
    }

    private class CustomSlot extends Slot {
        protected CookingPotScreenHandler _handler;
        public CustomSlot(CookingPotScreenHandler handler, Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
            _handler = handler;
        }

        @Override
        public void markDirty() {
            super.markDirty();
            _handler.onContentChanged(_handler.inventory);
            _handler.inventory.markDirty();
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return !_handler.isContainer(stack);
        }
    }

    private class ContainerSlot extends CustomSlot {
        public ContainerSlot(CookingPotScreenHandler handler, Inventory inventory, int index, int x, int y) {
            super(handler, inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return _handler.isContainer(stack);
        }
    }

    private class OutputSlot extends CustomSlot {
        public OutputSlot(CookingPotScreenHandler handler, Inventory inventory, int index, int x, int y) {
            super(handler, inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }
    }

    private class FuelSlot extends CustomSlot {
        public FuelSlot(CookingPotScreenHandler handler, Inventory inventory, int index, int x, int y) {
            super(handler, inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return _handler.isFuel(stack);
        }
    }
}
