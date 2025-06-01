package xen42.canadamod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import xen42.canadamod.screen.CookingPotScreenHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CanadaMod implements ModInitializer {
	public static final String MOD_ID = "canadamod";

	public static final RegistryKey<Biome> MAPLE_BIOME_KEY = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(CanadaMod.MOD_ID, "maple_forest"));

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

		CanadaConfiguredFeatures.onInitialize();
		CanadaBlocks.initialize();
		CanadaItems.initialize();
		MapleBoatEntity.initialize();
		CanadaPlacedFeatures.onInitialize();
		
		BiomeModifications.addFeature(context -> context.getBiomeKey().getValue()
			.equals(Identifier.of(CanadaMod.MOD_ID, "maple_forest")), GenerationStep.Feature.VEGETAL_DECORATION, CanadaPlacedFeatures.MAPLE_FOREST_VEGETATION);
	}
}