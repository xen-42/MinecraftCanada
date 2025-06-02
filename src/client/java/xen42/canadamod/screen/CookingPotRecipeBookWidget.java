package xen42.canadamod.screen;

import java.util.List;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.recipebook.GhostRecipe;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.NetworkRecipeId;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.context.ContextParameterMap;
import xen42.canadamod.CanadaBlocks;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.recipe.CookingPotRecipeDisplay;

public class CookingPotRecipeBookWidget extends RecipeBookWidget<CookingPotScreenHandler> {
	private static final Text TOGGLE_CRAFTABLE_TEXT = Text.translatable("gui.recipebook.toggleRecipes.craftable");

	private static final List<RecipeBookWidget.Tab> TABS = List.of(
		new RecipeBookWidget.Tab(CanadaBlocks.COOKING_POT.asItem(), CanadaMod.COOKING_POT_RECIPE_BOOK_CATEGORY)
	);

	private static final ButtonTextures TEXTURES = new ButtonTextures(
		Identifier.ofVanilla("recipe_book/filter_enabled"),
		Identifier.ofVanilla("recipe_book/filter_disabled"),
		Identifier.ofVanilla("recipe_book/filter_enabled_highlighted"),
		Identifier.ofVanilla("recipe_book/filter_disabled_highlighted")
	);

    public CookingPotRecipeBookWidget(CookingPotScreenHandler craftingScreenHandler) {
        super(craftingScreenHandler, TABS);
    }

    @Override
    protected Text getToggleCraftableButtonText() {
		return TOGGLE_CRAFTABLE_TEXT;
    }

    @Override
    protected boolean isValid(Slot slot) {
		return this.craftingScreenHandler.getOutputSlot() == slot || this.craftingScreenHandler.getInputSlots().contains(slot) || 
        this.craftingScreenHandler.getContainerSlot() == slot || this.craftingScreenHandler.getFuelSlot() == slot;
    }

    @Override
    protected void populateRecipes(RecipeResultCollection recipeResultCollection, RecipeFinder recipeFinder) {
		recipeResultCollection.populateRecipes(recipeFinder, this::canDisplay);
    }

    private boolean canDisplay(RecipeDisplay display) {
		return true;
	}

    @Override
    protected void setBookButtonTexture() {
		this.toggleCraftableButton.setTextures(TEXTURES);
    }

    @Override
    protected void showGhostRecipe(GhostRecipe ghostRecipe, RecipeDisplay display, ContextParameterMap context) {
		var cookingPotDisplay = (CookingPotRecipeDisplay)display;
		ghostRecipe.addResults(this.craftingScreenHandler.getOutputSlot(), context, cookingPotDisplay.result());

		List<Slot> inputSlots = this.craftingScreenHandler.getInputSlots();
		List<SlotDisplay> ingredients = cookingPotDisplay.ingredients(); 
		for (int i = 0; i < inputSlots.size(); i++) {
			if (i >= ingredients.size()) {
				continue;
			}
			ghostRecipe.addInputs(inputSlots.get(i), context, ingredients.get(i));
		}
		
		ghostRecipe.addInputs(this.craftingScreenHandler.getContainerSlot(), context, cookingPotDisplay.containers());
    }
}
