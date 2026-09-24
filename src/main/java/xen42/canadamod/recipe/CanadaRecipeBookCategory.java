package xen42.canadamod.recipe;

import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.util.Identifier;

public class CanadaRecipeBookCategory extends RecipeBookCategory {
	public Identifier id;
	
	public CanadaRecipeBookCategory(Identifier id) {
		this.id = id;
	}
	
	public String toString() {
		return id.getNamespace().toUpperCase() + "_" + id.getPath().toUpperCase();
	}
}