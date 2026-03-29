package xen42.canadamod.rei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.InputIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.screen.ScreenHandler;
import xen42.canadamod.recipe.CookingPotRecipe;
import xen42.canadamod.recipe.CookingPotRecipeDisplay;

public class CookingPotREIDisplay extends BasicDisplay implements SimpleGridMenuDisplay {
	public static final DisplaySerializer<CookingPotREIDisplay> SERIALIZER = DisplaySerializer.of(
			RecordCodecBuilder.mapCodec(instance -> instance.group(
					EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(CookingPotREIDisplay::getInputEntries),
					EntryIngredient.codec().listOf().fieldOf("containers").forGetter(CookingPotREIDisplay::getContainerEntries),
					EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(CookingPotREIDisplay::getOutputEntries)
			).apply(instance, CookingPotREIDisplay::new)),
			PacketCodec.tuple(
					EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
					CookingPotREIDisplay::getInputEntries,
					EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
					CookingPotREIDisplay::getContainerEntries,
					EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
					CookingPotREIDisplay::getOutputEntries,
					CookingPotREIDisplay::new
			), false);

	public static final int WIDTH = 2;
	public static final int HEIGHT = 2;
	public static final int WIDTH_X_HEIGHT = WIDTH * HEIGHT;
	
	protected List<EntryIngredient> containers;
	
	public CookingPotREIDisplay(CookingPotRecipeDisplay recipe) {
		this(EntryIngredients.ofSlotDisplays(recipe.ingredients()),
				List.of(EntryIngredients.ofSlotDisplay(recipe.containers())),
				List.of(EntryIngredients.ofSlotDisplay(recipe.result())));
	}
	
	static List<EntryIngredient> ingredientsFromRecipe(CookingPotRecipe recipe){
		List<EntryIngredient> ingredients = new ArrayList<EntryIngredient>();
		for (Optional<Ingredient> optionalIngredient : recipe.getIngredients()) {
			if (optionalIngredient.isPresent()) {
				ingredients.add(EntryIngredients.ofIngredient(optionalIngredient.get()));
			}
		}
		return ingredients;
	}
	
	public CookingPotREIDisplay(RecipeEntry<CookingPotRecipe> recipe) {
		this(recipe.value());
	}
	
	public CookingPotREIDisplay(CookingPotRecipe recipe) {
		this(ingredientsFromRecipe(recipe), recipe.container().isEmpty() ? List.of() : List.of(EntryIngredients.of(recipe.container())), List.of(EntryIngredients.of(recipe.result())));
	}
	
	public CookingPotREIDisplay(List<EntryIngredient> inputs, List<EntryIngredient> containers, List<EntryIngredient> outputs) {
		super(inputs, outputs);
		this.containers = containers;
	}

	@Override
	public List<EntryIngredient> getInputEntries() {
		return inputs;
	}
	
	public boolean hasContainer() {
		var containerEntries = getContainerEntries();
		if (containerEntries == null || containerEntries.isEmpty()) return false;
		
		var containerIngredient = containerEntries.get(0);
		if (containerIngredient == null || containerIngredient.isEmpty()) return false;
		
		var containerStack = containerIngredient.get(0);
		if (containerStack == null || containerStack.isEmpty()) return false;
		
		return true;
	}
	
	public EntryIngredient getContainerEntry() {
		if (!hasContainer()) return EntryIngredient.empty();
		
		return getContainerEntries().get(0);
	}

	public List<EntryIngredient> getContainerEntries() {
		return containers;
	}

	@Override
	public List<EntryIngredient> getOutputEntries() {
		return outputs;
	}

	@Override
	public DisplaySerializer<? extends Display> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return CookingPotServerPlugin.COOKING_POT_CATEGORY;
	}
	
	static int getSlotWithSize(int recipeWidth, int index, int craftingGridWidth, int craftingGridHeight) {
		int x = index % recipeWidth;
		int y = (index - x) / recipeWidth;
		return craftingGridWidth * y + x;
	}
	
	public int getSlotWithSize(int index, int craftingGridWidth, int craftingGridHeight) {
		return getSlotWithSize(getInputWidth(craftingGridWidth, craftingGridHeight), index, craftingGridWidth, craftingGridHeight);
	}
	
	public List<EntryIngredient> getOrganisedInputEntries(int menuWidth, int menuHeight) {
		List<EntryIngredient> list = new ArrayList<EntryIngredient>(menuWidth * menuHeight);
		for (int i = 0; i < menuWidth * menuHeight; i++) {
			list.add(EntryIngredient.empty());
		}
		for (int i = 0; i < getInputEntries().size(); i++) {
			list.set(getSlotWithSize(i, menuWidth, menuHeight), getInputEntries().get(i));
		}
		return list;
	}
	
	public List<InputIngredient<EntryStack<?>>> getInputAndContainerIngredients(@Nullable ScreenHandler menu, @Nullable PlayerEntity player) {
		var inputIngredients = getInputIngredients(menu, player);
		InputIngredient<EntryStack<?>> containerIngredient = !hasContainer() ? InputIngredient.empty(WIDTH_X_HEIGHT) : InputIngredient.of(WIDTH_X_HEIGHT, WIDTH_X_HEIGHT, getContainerEntry());
		inputIngredients.add(containerIngredient);
		return inputIngredients;
	}
	
	@Override
	public List<InputIngredient<EntryStack<?>>> getInputIngredients(@Nullable ScreenHandler menu, @Nullable PlayerEntity player) {
		return getInputIngredients(WIDTH, HEIGHT);
	}
	
	public List<InputIngredient<EntryStack<?>>> getInputIngredients(int craftingWidth, int craftingHeight) {
		int inputWidth = getInputWidth(craftingWidth, craftingHeight);
		int inputHeight = getInputHeight(craftingWidth, craftingHeight);
		
		Map<IntIntPair, InputIngredient<EntryStack<?>>> grid = new HashMap<>();
		
		List<EntryIngredient> inputEntries = getInputEntries();
		for (int i = 0; i < inputEntries.size(); i++) {
			EntryIngredient stacks = inputEntries.get(i);
			if (stacks.isEmpty()) {
				continue;
			}
			int index = getSlotWithSize(inputWidth, i, craftingWidth);
			int x = i % inputWidth;
			int y = i / inputHeight;
			grid.put(new IntIntImmutablePair(x, y), InputIngredient.of(index, craftingWidth * y + x, stacks));
		}
		
		List<InputIngredient<EntryStack<?>>> list = new ArrayList<>((craftingHeight * craftingWidth));
		for (int i = 0, n = (craftingWidth * craftingHeight); i < n; i++) {
			list.add(InputIngredient.empty(i));
		}

		for (int y = 0; y < craftingHeight; y++) {
			for (int x = 0; x < craftingWidth; x++) {
				InputIngredient<EntryStack<?>> ingredient = grid.get(new IntIntImmutablePair(x, y));
				if (ingredient != null) {
					int index = craftingWidth * y + x;
					list.set(index, ingredient);
				}
			}
		}
		
		return list;
	}
	
	@Nullable
	public static CookingPotREIDisplay of(RecipeEntry<? extends CookingPotRecipe> holder) {
		CookingPotRecipe recipe = holder.value();
		if (recipe instanceof CookingPotRecipe) {
			return new CookingPotREIDisplay(recipe);
		} else if (!recipe.isIgnoredInRecipeBook()) {
			for (RecipeDisplay d : recipe.getDisplays()) {
				if (d instanceof CookingPotRecipeDisplay display) {
					return new CookingPotREIDisplay(display);
				}
			}
		}
		
		return null;
	}
}
