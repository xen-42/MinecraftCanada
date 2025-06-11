package xen42.canadamod;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.BlockStateModelGenerator.CrossType;

public class CanadaModModelGenerator extends FabricModelProvider {
    public CanadaModModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(CanadaItems.PELT, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.FLOUR, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.MAPLE_BOAT, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.MAPLE_CHEST_BOAT, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.MAPLE_SAPLING, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.TREE_TAP, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.MAPLE_SYRUP_BOTTLE, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.SAP, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.MAPLE_SAP, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.RUBBER, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.GRAVY, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.CHEESE_CURD, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.POUTINE, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.BEAVER_SPAWN_EGG, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.MOOSE_SPAWN_EGG, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.VENISON, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.COOKED_VENISON, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.ANTLERS, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.THERMOS, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.PIEROGI, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.DONAIR, Models.GENERATED);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCubeAllModelTexturePool(CanadaBlocks.MAPLE_PLANKS).family(CanadaBlocks.MAPLE);
        blockStateModelGenerator.createLogTexturePool(CanadaBlocks.MAPLE_LOG).log(CanadaBlocks.MAPLE_LOG).wood(CanadaBlocks.MAPLE_WOOD);
        blockStateModelGenerator.createLogTexturePool(CanadaBlocks.STRIPPED_MAPLE_LOG).log(CanadaBlocks.STRIPPED_MAPLE_LOG).wood(CanadaBlocks.STRIPPED_MAPLE_WOOD);
        blockStateModelGenerator.registerSimpleCubeAll(CanadaBlocks.MAPLE_LEAVES);
        blockStateModelGenerator.registerHangingSign(CanadaBlocks.STRIPPED_MAPLE_LOG, CanadaBlocks.MAPLE_HANGING_SIGN, CanadaBlocks.MAPLE_WALL_HANGING_SIGN);
        blockStateModelGenerator.registerTintableCrossBlockState(CanadaBlocks.MAPLE_SAPLING, CrossType.NOT_TINTED);

        blockStateModelGenerator.registerSimpleCubeAll(CanadaBlocks.RUBBER_BLOCK);
    }

    @Override
    public String getName() {
        return "CanadaModModelGenerator";
    }
}
