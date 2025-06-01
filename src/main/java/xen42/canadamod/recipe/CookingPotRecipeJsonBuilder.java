package xen42.canadamod.recipe;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import xen42.canadamod.CanadaBlocks;
import xen42.canadamod.CanadaMod;

public class CookingPotRecipeJsonBuilder implements CraftingRecipeJsonBuilder {

	private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<String, AdvancementCriterion<?>>();
	private final ItemStack result;
    @Nullable
    private String group;
    private final ArrayList<Ingredient> _inputsList;
    private boolean _requiresBowl;
    private boolean _requiresBottle;
    private final int _count;
    // For later I should make it work with tags oh well
    private final RegistryEntryLookup<Item> _registryLookup;

    public CookingPotRecipeJsonBuilder(RegistryEntryLookup<Item> registryLookup, ItemConvertible result, int count) {
        this.result = new ItemStack(result);
        this._count = count;

        this._registryLookup = registryLookup;
        _inputsList = new ArrayList<>();
    }

    public CookingPotRecipeJsonBuilder requiresBowl() {
        this._requiresBowl = true;
        this._requiresBottle = false;
        return this;
    }

    public CookingPotRecipeJsonBuilder requiresBottle() {
        this._requiresBottle = true;
        this._requiresBowl = false;
        return this;
    }

	public CookingPotRecipeJsonBuilder input(ItemConvertible item, RecipeGenerator recipeGenerator) {
		this._inputsList.add(Ingredient.ofItem(item));
        return this.criterion(RecipeGenerator.hasItem(item), recipeGenerator.conditionsFromItem(item));
	}

    @Override
    public CookingPotRecipeJsonBuilder criterion(String name, AdvancementCriterion<?> criterion) {
		this.criteria.put(name, criterion);
		return this;
    }

    @Override
    public Item getOutputItem() {
        return this.result.getItem();
    }

    @Override
    public CookingPotRecipeJsonBuilder group(String group) {
		this.group = group;
		return this;
    }

    @Override
    public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> recipeKey) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeKey.getValue());
        }

        if (_inputsList.size() > 4) {
            throw new IllegalStateException("Too many inputs!");
        }

        if (group == null) {
            group = "";
        }

        var recipe = new CookingPotRecipe(this.group, this.result, this._count, this._inputsList, this._requiresBowl, this._requiresBottle);

		Advancement.Builder builder = exporter.getAdvancementBuilder()
			.criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey))
			.rewards(AdvancementRewards.Builder.recipe(recipeKey))
			.criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        
		this.criteria.forEach(builder::criterion);

		exporter.accept(recipeKey, recipe, builder.build(recipeKey.getValue().withPrefixedPath("recipes/cooking_pot/")));
    }
}
