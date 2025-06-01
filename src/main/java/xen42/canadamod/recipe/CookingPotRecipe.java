package xen42.canadamod.recipe;

import java.util.ArrayList;
import java.util.List;

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
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.world.World;
import xen42.canadamod.CanadaMod;

public class CookingPotRecipe implements Recipe<CookingPotRecipeInput> {
    public final ItemStack result;
    public final String group;
	public final List<Ingredient> ingredients;
    public final boolean requiresBowl;
    public final boolean requiresBottle;
    private final int count;

    public CookingPotRecipe(String group, ItemStack result, int count, List<Ingredient> ingredients, boolean requiresBowl, boolean requiresBottle) {
        this.group = group;
        this.result = result;
        this.count = count;
        this.ingredients = ingredients;
        this.requiresBowl = requiresBowl;
        this.requiresBottle = requiresBottle;
    }

    @Override
    public ItemStack craft(CookingPotRecipeInput input, WrapperLookup registries) {
        return this.result.copy();
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
	public RecipeSerializer<CookingPotRecipe> getSerializer() {
		return CanadaMod.COOKING_POT_RECIPE_SERIALIZER;
	}

	@Override
	public RecipeType<CookingPotRecipe> getType() {
		return CanadaMod.COOKING_POT_RECIPE_TYPE;
	}

    @Override
    public boolean matches(CookingPotRecipeInput input, World world) {
        var flagHasBottle = false;
        var flagHasBowl = false;
        for (var stack : input.stacks) {
            if (stack.isOf(Items.BOWL)) {
                flagHasBowl = true;
                continue;
            }
            if (stack.isOf(Items.GLASS_BOTTLE)) {
                flagHasBottle = true;
                continue;
            }
            if (!this.ingredients.stream().anyMatch(ingredient -> ingredient.test(stack))) {
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
					ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                    Codec.INT.fieldOf("count").forGetter(recipe -> recipe.count),
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
            var count = buf.readInt();
            var ingredientsCount = buf.readInt();
            var ingredients = new ArrayList<Ingredient>();
            for (int i = 0; i < ingredientsCount; i++) {
			    ingredients.add(Ingredient.PACKET_CODEC.decode(buf));
            }
            var requiresBottle = buf.readBoolean();
            var requiresBowl = buf.readBoolean();

			return new CookingPotRecipe(string, result, count, ingredients, requiresBottle, requiresBowl);
		}

		private static void write(RegistryByteBuf buf, CookingPotRecipe recipe) {
			buf.writeString(recipe.group);
			ItemStack.PACKET_CODEC.encode(buf, recipe.result);
            buf.writeInt(recipe.count);
            buf.writeInt(recipe.ingredients.size());
            for (var ingredient : recipe.ingredients) {
			    Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }
            buf.writeBoolean(recipe.requiresBottle);
            buf.writeBoolean(recipe.requiresBowl);
		}
	}
}
