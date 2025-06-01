package xen42.canadamod.screen;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.book.RecipeBookType;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import xen42.canadamod.CanadaBlocks;
import xen42.canadamod.CanadaMod;

public class CookingPotScreenHandler extends AbstractRecipeScreenHandler {

    public final static int OUTPUT_SLOT = 0;
    public final static int FUEL_SLOT = 1;
    public final static int CONTAINER_SLOT = 2;    
    public final static int INPUT_SLOTS_START = 3;
    public final static int INPUT_SLOTS_END = 7;

    public static final int MAX_WIDTH_AND_HEIGHT = 2;

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
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public CookingPotScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, inventory);
    }

    public CookingPotScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, null);
    }

    public CookingPotScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, Inventory inventory) {
        super(CanadaMod.COOKING_POT_SCREEN_HANDLER_TYPE, syncId);
        this.inventory = inventory == null ? new CookingPotSimpleInventory(this, 8) : inventory;
        this.context = context;
        this.player = playerInventory.player;

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
    }

    @Override
    public PostFillAction fillInputSlots(boolean craftAll, boolean creative, RecipeEntry<?> recipe, ServerWorld world, PlayerInventory inventory) {
        return AbstractRecipeScreenHandler.PostFillAction.NOTHING;
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
        return ItemStack.EMPTY;
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
