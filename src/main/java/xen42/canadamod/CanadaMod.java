package xen42.canadamod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import xen42.canadamod.entities.BeaverEntity;
import xen42.canadamod.recipe.CookingPotRecipe;
import xen42.canadamod.recipe.CookingPotRecipeDisplay;
import xen42.canadamod.screen.CookingPotScreenHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CanadaMod implements ModInitializer {
	public static final String MOD_ID = "canadamod";

	public static final RegistryKey<Biome> MAPLE_BIOME_KEY = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(CanadaMod.MOD_ID, "maple_forest"));

	public static final RegistryKey<RecipeType<?>> COOKING_POT_RECIPE_TYPE_KEY = RegistryKey.of(RegistryKeys.RECIPE_TYPE, Identifier.of(MOD_ID, "cooking_pot"));
	public static final RecipeType<CookingPotRecipe> COOKING_POT_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, Identifier.of(MOD_ID, "cooking_pot"), new RecipeType<CookingPotRecipe>() {
		public String toString() {
			return "cooking_pot";
		}
	});
	public static final RecipeSerializer<CookingPotRecipe> COOKING_POT_RECIPE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(MOD_ID, "cooking_pot"), new CookingPotRecipe.Serializer());
	public static final RecipeDisplay.Serializer<CookingPotRecipeDisplay> COOKING_POT_RECIPE_DISPLAY = Registry.register(Registries.RECIPE_DISPLAY, Identifier.of(MOD_ID, "cooking_pot"), CookingPotRecipeDisplay.SERIALIZER);
	public static final RecipeBookCategory COOKING_POT_RECIPE_BOOK_CATEGORY = Registry.register(Registries.RECIPE_BOOK_CATEGORY, 
		Identifier.of(MOD_ID, "cooking_pot"), new RecipeBookCategory() {
		public String toString() {
			return "COOKING_POT";
		}
	});

	public static final ScreenHandlerType<CookingPotScreenHandler> COOKING_POT_SCREEN_HANDLER_TYPE = Registry.register(
		Registries.SCREEN_HANDLER,
		Identifier.of(MOD_ID, "cooking_pot"),
		new ScreenHandlerType<CookingPotScreenHandler>(CookingPotScreenHandler::new, null));

	public static final BlockEntityType<CookingPotBlockEntity> COOKING_POT_ENTITY = registerBlockEntityType(
		"cooking_pot_entity",
		FabricBlockEntityTypeBuilder.create(CookingPotBlockEntity::new, 
			new Block[] { CanadaBlocks.COOKING_POT }).build()
	);
	public static <T extends BlockEntityType<?>> T registerBlockEntityType(String path, T blockEntityType) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(CanadaMod.MOD_ID, path), blockEntityType);
	}

	public static final RegistryKey<EntityType<?>> BEAVER_ENTITY_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID,"beaver"));
	public static final EntityType<BeaverEntity> BEAVER_ENTITY = Registry.register(
		Registries.ENTITY_TYPE, 
		Identifier.of(MOD_ID, "beaver"), 
		EntityType.Builder.create(BeaverEntity::new, SpawnGroup.CREATURE).dimensions(0.5f, 1.5f).build(BEAVER_ENTITY_KEY));


	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Loading Canada mod!");

		FabricDefaultAttributeRegistry.register(BEAVER_ENTITY, BeaverEntity.createMobAttributes());

		CanadaBlocks.initialize();
		CanadaConfiguredFeatures.onInitialize();
		CanadaItems.initialize();
		MapleBoatEntity.initialize();
		CanadaPlacedFeatures.onInitialize();
		
		BiomeModifications.addFeature(context -> context.getBiomeKey().getValue()
			.equals(Identifier.of(CanadaMod.MOD_ID, "maple_forest")), GenerationStep.Feature.VEGETAL_DECORATION, CanadaPlacedFeatures.MAPLE_FOREST_VEGETATION);
	}
}