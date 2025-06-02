package xen42.canadamod.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jetbrains.annotations.VisibleForTesting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.IngredientPlacement;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.recipe.display.SlotDisplay.StackSlotDisplay;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.world.World;
import xen42.canadamod.CanadaBlocks;
import xen42.canadamod.CanadaMod;

public class CookingPotRecipe implements Recipe<CookingPotRecipeInput> {
    public final ItemStack result;
    public final String group;
	public final List<Ingredient> ingredients;
    public final boolean requiresBowl;
    public final boolean requiresBottle;

    public CookingPotRecipe(String group, ItemStack result, List<Ingredient> ingredients, boolean requiresBowl, boolean requiresBottle) {
        this.group = group;
        this.result = result;
        this.ingredients = ingredients;
        this.requiresBowl = requiresBowl;
        this.requiresBottle = requiresBottle;
    }

    @Override
    public ItemStack craft(CookingPotRecipeInput input, WrapperLookup registries) {
        return this.result.copy();
    }

	@VisibleForTesting
	public List<Optional<Ingredient>> getIngredients() {
		return this.ingredients.stream().map(x -> Optional.of(x)).collect(Collectors.toList());
	}

	@Override
	public IngredientPlacement getIngredientPlacement() {
		return IngredientPlacement.forShapeless(this.ingredients);
	}

	@Override
	public RecipeBookCategory getRecipeBookCategory() {
		return CanadaMod.COOKING_POT_RECIPE_BOOK_CATEGORY;
	}

	@Override
	public RecipeSerializer<? extends CookingPotRecipe> getSerializer() {
		return CanadaMod.COOKING_POT_RECIPE_SERIALIZER;
	}

	@Override
	public RecipeType<CookingPotRecipe> getType() {
		return CanadaMod.COOKING_POT_RECIPE_TYPE;
	}

    @Override
	public boolean isIgnoredInRecipeBook() {
		return false;
	}

    @Override
	public boolean showNotification() {
		return true;
	}

    @Override
	public String getGroup() {
		return this.group;
	}

	@Override
	public List<RecipeDisplay> getDisplays() {
        var containerItem = this.requiresBottle ? Items.GLASS_BOTTLE : (this.requiresBowl ? Items.BOWL : null);
        var containerDisplay = containerItem == null ? SlotDisplay.EmptySlotDisplay.INSTANCE : new SlotDisplay.ItemSlotDisplay(containerItem);
        var ingredientDisplays = this.ingredients.stream().map(Ingredient::toDisplay).toList();
        var cookingPotItem = CanadaBlocks.COOKING_POT.asItem();
		return List.of(
            new CookingPotRecipeDisplay(ingredientDisplays, containerDisplay,
                new StackSlotDisplay(this.result), new SlotDisplay.ItemSlotDisplay(cookingPotItem))
        );
	}

    @Override
    public boolean matches(CookingPotRecipeInput input, World world) {
        var flagHasBottle = false;
        var flagHasBowl = false;
        // Skip output and fuel slots
        for (int i = 2; i < input.stacks.size(); i++) {
            var stack = input.stacks.get(i);
            if (stack.isOf(Items.BOWL)) {
                flagHasBowl = true;
            }
            else if (stack.isOf(Items.GLASS_BOTTLE)) {
                flagHasBottle = true;
            }
            else {
                if (!ingredients.stream().anyMatch(ingredient -> stack.isEmpty() || Ingredient.matches(Optional.of(ingredient), stack))) {
                    return false;
                }
            }

        }
        for (var ingredient : ingredients) {
            if (!input.stacks.stream().anyMatch(stack -> Ingredient.matches(Optional.of(ingredient), stack))) {
                return false;
            }
        }
        if (this.requiresBottle != flagHasBottle) {
            return false;
        }
        if (this.requiresBowl != flagHasBowl) {
            return false;
        }

        return true;
    }

    public static class Serializer implements RecipeSerializer<CookingPotRecipe> {
		public static final MapCodec<CookingPotRecipe> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
					ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
					Ingredient.CODEC.listOf(1, 4).fieldOf("ingredients").forGetter(recipe -> recipe.ingredients),
                    Codec.BOOL.optionalFieldOf("requiresBowl", false).forGetter(recipe -> recipe.requiresBowl),
                    Codec.BOOL.optionalFieldOf("requiresBottle", false).forGetter(recipe -> recipe.requiresBottle)
				)
				.apply(instance, CookingPotRecipe::new)
		);
		public static final PacketCodec<RegistryByteBuf, CookingPotRecipe> PACKET_CODEC = PacketCodec.ofStatic(
				CookingPotRecipe.Serializer::write, CookingPotRecipe.Serializer::read
		);

		@Override
		public MapCodec<CookingPotRecipe> codec() {
			return CODEC;
		}

		@Deprecated
		@Override
		public PacketCodec<RegistryByteBuf, CookingPotRecipe> packetCodec() {
			return PACKET_CODEC;
		}

		private static CookingPotRecipe read(RegistryByteBuf buf) {
			var string = buf.readString();
			var result = ItemStack.PACKET_CODEC.decode(buf);
            var ingredientsCount = buf.readInt();
            var ingredients = new ArrayList<Ingredient>();
            for (int i = 0; i < ingredientsCount; i++) {
			    ingredients.add(Ingredient.PACKET_CODEC.decode(buf));
            }
            var requiresBottle = buf.readBoolean();
            var requiresBowl = buf.readBoolean();

			return new CookingPotRecipe(string, result, ingredients, requiresBottle, requiresBowl);
		}

		private static void write(RegistryByteBuf buf, CookingPotRecipe recipe) {
			buf.writeString(recipe.group);
			ItemStack.PACKET_CODEC.encode(buf, recipe.result);
            buf.writeInt(recipe.ingredients.size());
            for (var ingredient : recipe.ingredients) {
			    Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }
            buf.writeBoolean(recipe.requiresBottle);
            buf.writeBoolean(recipe.requiresBowl);
		}
	}
}
