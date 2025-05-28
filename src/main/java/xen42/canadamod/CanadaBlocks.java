package xen42.canadamod;

import java.util.Optional;
import java.util.function.Function;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.UntintedParticleLeavesBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HangingSignBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import xen42.canadamod.sign.MapleHangingSignBlock;
import xen42.canadamod.sign.MapleHangingSignBlockEntity;
import xen42.canadamod.sign.MapleSignBlock;
import xen42.canadamod.sign.MapleSignEntity;
import xen42.canadamod.sign.MapleWallHangingSignBlock;
import xen42.canadamod.sign.MapleWallSignBlock;

public class CanadaBlocks {
    public static final BlockSetType MAPLE_WOOD_SET = BlockSetType.register(new BlockSetType("maple"));
    public static final WoodType MAPLE_WOOD_TYPE = WoodType.register(new WoodType("maple", MAPLE_WOOD_SET));

	public static Block MAPLE_PLANKS;
    public static Block MAPLE_LOG;
    public static Block MAPLE_LEAVES;
    public static Block STRIPPED_MAPLE_LOG;
    public static Block MAPLE_WOOD;
    public static Block STRIPPED_MAPLE_WOOD;
    public static Block MAPLE_STAIRS;
    public static Block MAPLE_BUTTON;
    public static Block MAPLE_SLAB;
    public static Block MAPLE_DOOR;
    public static Block MAPLE_TRAPDOOR;
    public static Block MAPLE_FENCE;
    public static Block MAPLE_FENCE_GATE;
    public static Block MAPLE_PRESSURE_PLATE;
    public static Block MAPLE_SIGN;
    public static Block MAPLE_WALL_SIGN;
    public static Block MAPLE_HANGING_SIGN;
    public static Block MAPLE_WALL_HANGING_SIGN;

	public static final SaplingGenerator MAPLE_SAPLING_GENERATED = new SaplingGenerator("maple", Optional.empty(), 
		Optional.of(CanadaConfiguredFeatures.MAPLE_CONFIGURED_FEATURE), Optional.of(CanadaConfiguredFeatures.MAPLE_CONFIGURED_FEATURE));
	public static final Block MAPLE_SAPLING = register("maple_sapling", 
        settings -> new SaplingBlock(MAPLE_SAPLING_GENERATED, settings), 
        AbstractBlock.Settings.copy(Blocks.OAK_SAPLING).mapColor(MapColor.RED),
		false);

	public static Block TREE_TAP = register(
			"tree_tap",
			TreeTapBlock::new,
			AbstractBlock.Settings.copy(Blocks.CHAIN),
			false
		);

	public static Block[] MAPLE_BLOCKS;

	public static BlockFamily MAPLE;

    public static BlockEntityType<SignBlockEntity> MAPLE_SIGN_BLOCK_ENTITY;
    public static BlockEntityType<HangingSignBlockEntity> MAPLE_HANGING_SIGN_BLOCK_ENTITY;

	public static SimpleParticleType MAPLE_LEAF_PARTICLE = FabricParticleTypes.simple();

	public static void initialize() {
		MAPLE_PLANKS = register(
			"maple_planks",
			Block::new,
			AbstractBlock.Settings.copy(Blocks.OAK_PLANKS),
			true
		);
		MAPLE_LOG = register(
			"maple_log",
			PillarBlock::new,
			AbstractBlock.Settings.copy(Blocks.OAK_LOG),
			true
		);
		Registry.register(Registries.PARTICLE_TYPE,
					Identifier.of(CanadaMod.MOD_ID, "maple"),
					MAPLE_LEAF_PARTICLE);
		MAPLE_LEAVES = register(
			"maple_leaves",
			(settings) -> new UntintedParticleLeavesBlock(0.01F, MAPLE_LEAF_PARTICLE, settings),
			AbstractBlock.Settings.copy(Blocks.OAK_LEAVES),
			true
		);
		STRIPPED_MAPLE_LOG = register(
			"stripped_maple_log",
			PillarBlock::new,
			AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG),
			true
		);
		MAPLE_WOOD = register(
			"maple_wood",
			PillarBlock::new,
			AbstractBlock.Settings.copy(Blocks.OAK_WOOD),
			true
		);
		STRIPPED_MAPLE_WOOD = register(
			"stripped_maple_wood",
			PillarBlock::new,
			AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD),
			true
		);
		MAPLE_STAIRS = register(
			"maple_stairs",
			(settings) -> new StairsBlock(MAPLE_PLANKS.getDefaultState(), settings),
			AbstractBlock.Settings.copy(Blocks.OAK_STAIRS),
			true
		);
		MAPLE_BUTTON = register(
			"maple_button",
			(settings) -> new ButtonBlock(MAPLE_WOOD_SET, 30, settings),
			AbstractBlock.Settings.copy(Blocks.OAK_BUTTON),
			true
		);
		MAPLE_SLAB = register(
			"maple_slab",
			SlabBlock::new,
			AbstractBlock.Settings.copy(Blocks.OAK_SLAB),
			true
		);
		MAPLE_DOOR = register(
			"maple_door",
			(settings) -> new DoorBlock(MAPLE_WOOD_SET, settings),
			AbstractBlock.Settings.copy(Blocks.OAK_DOOR),
			true
		);
		MAPLE_TRAPDOOR = register(
			"maple_trapdoor",
			(settings) -> new TrapdoorBlock(MAPLE_WOOD_SET, settings),
			AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR),
			true
		);
		MAPLE_FENCE = register(
			"maple_fence",
			FenceBlock::new,
			AbstractBlock.Settings.copy(Blocks.OAK_FENCE),
			true
		);
		MAPLE_FENCE_GATE = register(
			"maple_fence_gate",
			(settings) -> new FenceGateBlock(MAPLE_WOOD_TYPE, settings),
			AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE),
			true
		);
		MAPLE_PRESSURE_PLATE = register(
			"maple_pressure_plate",
			(settings) -> new PressurePlateBlock(MAPLE_WOOD_SET, settings),
			AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE),
			true
		);
		MAPLE_SIGN = register(
			"maple_sign",
			MapleSignBlock::new,
			AbstractBlock.Settings.copy(Blocks.OAK_SIGN),
			false
		);
		MAPLE_WALL_SIGN = register(
			"maple_wall_sign",
			MapleWallSignBlock::new,
			AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN),
			false
		);
		MAPLE_HANGING_SIGN = register(
			"maple_hanging_sign",
			MapleHangingSignBlock::new,
			AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN),
			false
		);
		MAPLE_WALL_HANGING_SIGN = register(
			"maple_hanging_wall_sign",
			MapleWallHangingSignBlock::new,
			AbstractBlock.Settings.copy(Blocks.OAK_WALL_HANGING_SIGN),
			false
		);

		MAPLE = BlockFamilies.register(MAPLE_PLANKS)
			.button(MAPLE_BUTTON)
			.door(MAPLE_DOOR)
			.fence(MAPLE_FENCE)
			.fenceGate(MAPLE_FENCE_GATE)
			.pressurePlate(MAPLE_PRESSURE_PLATE)
			.trapdoor(MAPLE_TRAPDOOR)
			.slab(MAPLE_SLAB)
			.stairs(MAPLE_STAIRS)
			.sign(MAPLE_SIGN, MAPLE_WALL_SIGN)
			.group("wooden")
			.unlockCriterionName("has_planks")
			.build();

		MAPLE_BLOCKS = new Block[] {
			MAPLE_LOG, MAPLE_WOOD, STRIPPED_MAPLE_LOG, STRIPPED_MAPLE_WOOD, MAPLE_PLANKS, MAPLE_STAIRS,
			MAPLE_SLAB, MAPLE_FENCE, MAPLE_FENCE_GATE, MAPLE_DOOR, MAPLE_TRAPDOOR, MAPLE_PRESSURE_PLATE, MAPLE_BUTTON
		};

		StrippableBlockRegistry.register(CanadaBlocks.MAPLE_LOG, CanadaBlocks.STRIPPED_MAPLE_LOG);
		StrippableBlockRegistry.register(CanadaBlocks.MAPLE_WOOD, CanadaBlocks.STRIPPED_MAPLE_WOOD);

		FuelRegistryEvents.BUILD.register((builder, context) -> {
            int time = context.baseSmeltTime(); 
			for (var woodItem : new Block[] { MAPLE_LOG, MAPLE_WOOD, STRIPPED_MAPLE_LOG, STRIPPED_MAPLE_WOOD, MAPLE_PLANKS,
												MAPLE_TRAPDOOR, MAPLE_FENCE_GATE, MAPLE_FENCE }) {
				builder.add(woodItem, time);
			}
			for (var woodItem : new Block[] { MAPLE_STAIRS, MAPLE_SLAB, MAPLE_PRESSURE_PLATE }) {
				builder.add(woodItem, time / 2);
			}
			for (var woodItem : new Block[] { MAPLE_DOOR, MAPLE_SIGN, MAPLE_HANGING_SIGN }) {
				builder.add(woodItem, time * 2 / 3);
			}
			builder.add(MAPLE_BUTTON, time / 3);
        });

        MAPLE_SIGN_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(CanadaMod.MOD_ID, "maple_sign"),
			FabricBlockEntityTypeBuilder.<SignBlockEntity>create(MapleSignEntity::new, MAPLE_SIGN, MAPLE_WALL_SIGN).build());
		
		MAPLE_HANGING_SIGN_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(CanadaMod.MOD_ID, "maple_hanging_sign"),
			FabricBlockEntityTypeBuilder.<HangingSignBlockEntity>create(MapleHangingSignBlockEntity::new, MAPLE_HANGING_SIGN, MAPLE_WALL_HANGING_SIGN).build());
	}

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
		// Create a registry key for the block
		RegistryKey<Block> blockKey = keyOfBlock(name);
		// Create the block instance
		Block block = blockFactory.apply(settings.registryKey(blockKey));

		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
		if (shouldRegisterItem) {
			// Items need to be registered with a different type of registry key, but the ID
			// can be the same.
			RegistryKey<Item> itemKey = keyOfItem(name);

			BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
			Registry.register(Registries.ITEM, itemKey, blockItem);
		}

		return Registry.register(Registries.BLOCK, blockKey, block);
	}

	private static RegistryKey<Block> keyOfBlock(String name) {
		return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(CanadaMod.MOD_ID, name));
	}

	private static RegistryKey<Item> keyOfItem(String name) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(CanadaMod.MOD_ID, name));
	}
}