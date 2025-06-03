package xen42.canadamod;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootTableProvider;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
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
        this.register(
            CanadaMod.BEAVER_ENTITY, 
                LootTable.builder()
                .pool(
                    LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(
                            ItemEntry.builder(CanadaItems.PELT)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0F, 1.0F)))
                                .apply(EnchantedCountIncreaseLootFunction.builder(registries, UniformLootNumberProvider.create(0.0F, 1.0F)))
                        )
                )
            );
    }
}
