package xen42.canadamod.recipe;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import xen42.canadamod.CanadaMod;

public class CookingPotRecipeJsonBuilder implements CraftingRecipeJsonBuilder {

	private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<String, AdvancementCriterion<?>>();
	private final ItemStack result;
    @Nullable
    private String group;
    private final ArrayList<Ingredient> _inputsList;
    private boolean _requiresBowl;
    private boolean _requiresBottle;
    private final RegistryEntryLookup<Item> _registryLookup;

    public CookingPotRecipeJsonBuilder(RegistryEntryLookup<Item> registryLookup, ItemConvertible result, int count) {
        this.result = new ItemStack(result, count);

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

	public CookingPotRecipeJsonBuilder input(TagKey<Item> tag, RecipeGenerator recipeGenerator) {
		this._inputsList.add(Ingredient.fromTag(_registryLookup.getOrThrow(tag)));
        return this.criterion("has_" + tag.getName(), recipeGenerator.conditionsFromTag(tag));
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
    public CookingPotRecipeJsonBuilder group(@Nullable String group) {
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

        var recipe = new CookingPotRecipe(this.group, this.result, this._inputsList, this._requiresBowl, this._requiresBottle);

		Advancement.Builder builder = exporter.getAdvancementBuilder()
			.criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey))
			.rewards(AdvancementRewards.Builder.recipe(recipeKey))
			.criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        
		this.criteria.forEach(builder::criterion);

		exporter.accept(recipeKey, recipe, builder.build(recipeKey.getValue().withPrefixedPath("recipes/cooking_pot/")));
    }

    @Override
    public void offerTo(RecipeExporter exporter) {
		this.offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, getItemId(this.getOutputItem())));
    }

    @Override
	public void offerTo(RecipeExporter exporter, String recipePath) {
		Identifier identifier = getItemId(this.getOutputItem());
		Identifier identifier2 = Identifier.of(CanadaMod.MOD_ID, recipePath);
		if (identifier2.equals(identifier)) {
			throw new IllegalStateException("Recipe " + recipePath + " should remove its 'save' argument as it is equal to default one");
		} else {
			this.offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, identifier2));
		}
	}

    public static Identifier getItemId(ItemConvertible item) {
		return Identifier.of(CanadaMod.MOD_ID, Registries.ITEM.getId(item.asItem()).getPath());
	}
}
