package xen42.canadamod;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootTableProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class CanadaModEntityLootTableGenerator extends FabricEntityLootTableProvider {
    protected CanadaModEntityLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public String getName() {
        return "CanadaModEntityLootTableGenerator";
    }

    @Override
    public void generate() {

    }
}
