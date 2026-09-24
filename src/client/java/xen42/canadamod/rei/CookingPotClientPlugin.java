package xen42.canadamod.rei;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import xen42.canadamod.CanadaBlocks;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.recipe.CookingPotRecipeDisplay;
import xen42.canadamod.screen.CookingPotHandledScreen;

public class CookingPotClientPlugin implements REIClientPlugin {
    public CookingPotClientPlugin() {
		CanadaMod.LOGGER.info("Creating REI client plugin");
	}
	
	@Override
	public void registerCategories(CategoryRegistry registry) {
		CanadaMod.LOGGER.info("Registering REI categories");
		
		registry.add(new CookingPotCategory());

		CanadaMod.LOGGER.info("Registering REI workstations");
		
		registry.addWorkstations(CookingPotServerPlugin.COOKING_POT_CATEGORY, EntryStacks.of(CanadaBlocks.COOKING_POT));
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {
		CanadaMod.LOGGER.info("Registering REI displays");
		
		registry.beginRecipeFiller(CookingPotRecipeDisplay.class)
			.filterType(CookingPotRecipeDisplay.SERIALIZER)
			.fill(ClientsidedCookingPotREIDisplay::new);
	}
	
	@Override
	public void registerScreens(ScreenRegistry registry) {
		CanadaMod.LOGGER.info("Registering REI screens");

		registry.registerContainerClickArea(new Rectangle(109, 34, 24, 16), CookingPotHandledScreen.class, CookingPotServerPlugin.COOKING_POT_CATEGORY);
	}
	
	@Override
	public void registerTransferHandlers(TransferHandlerRegistry registry) {
		CanadaMod.LOGGER.info("Registering REI transfer handlers");
		
		registry.register(new CookingPotTransferHandler());
	}
}
