package xen42.canadamod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocation;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import net.minecraft.world.poi.PointOfInterestType;
import xen42.canadamod.block.CookingPotBlockEntity;
import xen42.canadamod.block.MooseSkullBlockEntity;
import xen42.canadamod.entities.BeaverChopTreeEffectPayload;
import xen42.canadamod.entities.BeaverEntity;
import xen42.canadamod.entities.MapleBoatEntity;
import xen42.canadamod.entities.MooseEntity;
import xen42.canadamod.recipe.CookingPotRecipe;
import xen42.canadamod.recipe.CookingPotRecipeDisplay;
import xen42.canadamod.screen.CookingPotScreenHandler;
import xen42.canadamod.world.CanadaConfiguredFeatures;
import xen42.canadamod.world.CanadaPlacedFeatures;

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
	public static final BlockEntityType<MooseSkullBlockEntity> MOOSE_HEAD_ENTITY = registerBlockEntityType(
		"moose_head_entity",
		FabricBlockEntityTypeBuilder.create(MooseSkullBlockEntity::new, 
			new Block[] { CanadaBlocks.MOOSE_HEAD, CanadaBlocks.MOOSE_WALL_HEAD }).build()
	);
	public static <T extends BlockEntityType<?>> T registerBlockEntityType(String path, T blockEntityType) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(CanadaMod.MOD_ID, path), blockEntityType);
	}

	public static final RegistryKey<EntityType<?>> BEAVER_ENTITY_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID,"beaver"));
	public static final EntityType<BeaverEntity> BEAVER_ENTITY = Registry.register(
		Registries.ENTITY_TYPE, 
		Identifier.of(MOD_ID, "beaver"), 
		EntityType.Builder.create(BeaverEntity::new, SpawnGroup.CREATURE).dimensions(0.5f, 0.5f).build(BEAVER_ENTITY_KEY));

	public static final RegistryKey<EntityType<?>> MOOSE_ENTITY_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID,"moose"));
	public static final EntityType<MooseEntity> MOOSE_ENTITY = Registry.register(
		Registries.ENTITY_TYPE, 
		Identifier.of(MOD_ID, "moose"), 
		EntityType.Builder.create(MooseEntity::new, SpawnGroup.CREATURE).dimensions(1.75f, 2.5f).build(MOOSE_ENTITY_KEY));

	private static RegistryEntry<StatusEffect> registerStatusEffect(String id, StatusEffect statusEffect) {
		return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, id), statusEffect);
	}
	public static final RegistryEntry<StatusEffect> BEAVER_EFFECT = registerStatusEffect("beaver_effect",
		(new StatusEffect(StatusEffectCategory.BENEFICIAL, 10187841))
		.addAttributeModifier(EntityAttributes.BLOCK_BREAK_SPEED, Identifier.of(MOD_ID, "effect.beaver_effect"), 3f, Operation.ADD_MULTIPLIED_TOTAL)
		.addAttributeModifier(EntityAttributes.SUBMERGED_MINING_SPEED, Identifier.of(MOD_ID, "effect.beaver_effect"), 2f, Operation.ADD_MULTIPLIED_TOTAL)
	);
	public static final RegistryEntry<StatusEffect> MOOSE_EFFECT = registerStatusEffect("moose_effect",
		(new StatusEffect(StatusEffectCategory.BENEFICIAL, 7079970))
		.addAttributeModifier(EntityAttributes.MAX_HEALTH, Identifier.of(MOD_ID, "effect.moose_effect"), 6.0, Operation.ADD_VALUE)
		.addAttributeModifier(EntityAttributes.KNOCKBACK_RESISTANCE, Identifier.of(MOD_ID, "effect.moose_effect"), 2f, Operation.ADD_MULTIPLIED_TOTAL)
	);

	public static final RegistryKey<Structure> MAPLE_CABIN_KEY = RegistryKey.of(RegistryKeys.STRUCTURE, Identifier.of(MOD_ID, "maple_cabin"));
	public static final StructureType<JigsawStructure> MAPLE_CABIN_TYPE_KEY = Registry.register(Registries.STRUCTURE_TYPE, Identifier.of(MOD_ID, "maple_cabin"),
		() -> JigsawStructure.CODEC);

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

		FabricDefaultAttributeRegistry.register(BEAVER_ENTITY, BeaverEntity.createBeaverAttributes());
		FabricDefaultAttributeRegistry.register(MOOSE_ENTITY, MooseEntity.createMooseAttributes());

		CanadaBlocks.initialize();
		CanadaConfiguredFeatures.onInitialize();
		CanadaItems.initialize();
		MapleBoatEntity.initialize();
		CanadaVillagers.initialize();
		CanadaPlacedFeatures.onInitialize();
		
		BiomeModifications.addFeature(context -> context.getBiomeKey().getValue()
			.equals(Identifier.of(CanadaMod.MOD_ID, "maple_forest")), GenerationStep.Feature.VEGETAL_DECORATION, CanadaPlacedFeatures.MAPLE_FOREST_VEGETATION);

		SpawnRestriction.register(BEAVER_ENTITY, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CanadaMod::canSpawn);
		SpawnRestriction.register(MOOSE_ENTITY, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CanadaMod::canSpawn);

		var mooseBiomes = BiomeSelectors.includeByKey(MAPLE_BIOME_KEY)
			.or(BiomeSelectors.includeByKey(BiomeKeys.TAIGA))
			.or(BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA))
			.or(BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_PINE_TAIGA))
			.or(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_TAIGA));
		BiomeModifications.addSpawn(mooseBiomes, SpawnGroup.CREATURE, MOOSE_ENTITY, 60, 2, 2);

		var beaverBiomes = BiomeSelectors.includeByKey(MAPLE_BIOME_KEY)
			.or(BiomeSelectors.includeByKey(BiomeKeys.RIVER))
			.or(BiomeSelectors.includeByKey(BiomeKeys.SWAMP));
		BiomeModifications.addSpawn(beaverBiomes, SpawnGroup.CREATURE, BEAVER_ENTITY, 100, 4, 4);

		PayloadTypeRegistry.playS2C().register(BeaverChopTreeEffectPayload.PAYLOAD_ID, BeaverChopTreeEffectPayload.CODEC);
	}

	private static boolean canSpawn(EntityType type, ServerWorldAccess access, SpawnReason reason, BlockPos pos, Random random) {
		var ground = access.getBlockState(pos.down());
		var goodGround = ground.isIn(BlockTags.DIRT) || ground.isOf(Blocks.GRASS_BLOCK) || ground.isOf(Blocks.PODZOL) || ground.isOf(Blocks.SNOW);
		var goodLight = access.getLightLevel(pos) > 8;
		return goodGround && goodLight;
	}
}