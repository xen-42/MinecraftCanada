package xen42.canadamod;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class CanadaModModelGenerator extends FabricModelProvider {
    public CanadaModModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(CanadaItems.PELT, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.MAPLE_BOAT, Models.GENERATED);
        itemModelGenerator.register(CanadaItems.MAPLE_CHEST_BOAT, Models.GENERATED);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCubeAllModelTexturePool(CanadaBlocks.MAPLE_PLANKS).family(CanadaBlocks.MAPLE);
        blockStateModelGenerator.createLogTexturePool(CanadaBlocks.MAPLE_LOG).log(CanadaBlocks.MAPLE_LOG).wood(CanadaBlocks.MAPLE_WOOD);
        blockStateModelGenerator.createLogTexturePool(CanadaBlocks.STRIPPED_MAPLE_LOG).log(CanadaBlocks.STRIPPED_MAPLE_LOG).wood(CanadaBlocks.STRIPPED_MAPLE_WOOD);
        blockStateModelGenerator.registerSimpleCubeAll(CanadaBlocks.MAPLE_LEAVES);
    }

    @Override
    public String getName() {
        return "CanadaModModelGenerator";
    }
}
