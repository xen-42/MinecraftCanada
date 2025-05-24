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
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public String getName() {
        return "CanadaModModelGenerator";
    }
}
