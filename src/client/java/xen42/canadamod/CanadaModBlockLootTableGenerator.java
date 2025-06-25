package xen42.canadamod;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class CanadaModBlockLootTableGenerator extends FabricBlockLootTableProvider {

    protected CanadaModBlockLootTableGenerator(FabricDataOutput dataOutput,
            CompletableFuture<WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(CanadaBlocks.MAPLE_BUTTON);
        addDrop(CanadaBlocks.MAPLE_DOOR, block -> doorDrops(block));
        addDrop(CanadaBlocks.MAPLE_FENCE);
        addDrop(CanadaBlocks.MAPLE_FENCE_GATE);
        addDrop(CanadaBlocks.MAPLE_HANGING_SIGN);
        addDrop(CanadaBlocks.MAPLE_LOG);
        addDrop(CanadaBlocks.MAPLE_PLANKS);
        addDrop(CanadaBlocks.MAPLE_PRESSURE_PLATE);
        addDrop(CanadaBlocks.MAPLE_SAPLING);
        addDrop(CanadaBlocks.MAPLE_SIGN);
        addDrop(CanadaBlocks.MAPLE_STAIRS);
        addDrop(CanadaBlocks.MAPLE_SLAB, block -> slabDrops(block));
        addDrop(CanadaBlocks.MAPLE_TRAPDOOR);
        addDrop(CanadaBlocks.MAPLE_WALL_HANGING_SIGN, block -> drops(CanadaBlocks.MAPLE_HANGING_SIGN));
        addDrop(CanadaBlocks.MAPLE_WALL_SIGN, block -> drops(CanadaBlocks.MAPLE_SIGN));
        addDrop(CanadaBlocks.MAPLE_WOOD);
        addDrop(CanadaBlocks.MAPLE_LEAVES, block -> leavesDrops(block, CanadaBlocks.MAPLE_SAPLING, SAPLING_DROP_CHANCE));   
        addDrop(CanadaBlocks.STRIPPED_MAPLE_LOG);
        addDrop(CanadaBlocks.STRIPPED_MAPLE_WOOD);
        addDrop(CanadaBlocks.TREE_TAP, block -> drops(CanadaItems.TREE_TAP));
        addDrop(CanadaBlocks.COOKING_POT);
        addDrop(CanadaBlocks.RUBBER_BLOCK);
        addDrop(CanadaBlocks.POTTED_MAPLE_SAPLING, block -> pottedPlantDrops(CanadaItems.MAPLE_SAPLING));
        addDrop(CanadaBlocks.MOOSE_HEAD, drops(CanadaItems.MOOSE_HEAD));
        addDrop(CanadaBlocks.MOOSE_WALL_HEAD, drops(CanadaItems.MOOSE_HEAD));
    }
}
