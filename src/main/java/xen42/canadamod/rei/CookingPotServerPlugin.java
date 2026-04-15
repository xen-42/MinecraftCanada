package xen42.canadamod.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import net.minecraft.util.Identifier;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.recipe.CookingPotRecipe;

public class CookingPotServerPlugin implements REICommonPlugin {
	public static final Identifier COOKING_POT = Identifier.of(CanadaMod.MOD_ID, "plugins/cooking_pot");
	public static final CategoryIdentifier<CookingPotREIDisplay> COOKING_POT_CATEGORY = CategoryIdentifier.of(CanadaMod.MOD_ID, "plugins/cooking_pot");

	public CookingPotServerPlugin() {
		CanadaMod.LOGGER.info("Creating REI server plugin");
	}
	
	@Override
	public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
		CanadaMod.LOGGER.info("Registering REI display serializers");

		registry.register(COOKING_POT, CookingPotREIDisplay.SERIALIZER);
	}

	@Override
	public void registerDisplays(ServerDisplayRegistry registry) {
		CanadaMod.LOGGER.info("Registering REI server displays");

		registry.beginRecipeFiller(CookingPotRecipe.class)
				.filterType(CanadaMod.COOKING_POT_RECIPE_TYPE)
				.fill(CookingPotREIDisplay::of);
	}
}
