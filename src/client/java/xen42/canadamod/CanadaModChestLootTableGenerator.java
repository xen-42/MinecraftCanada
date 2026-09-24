package xen42.canadamod;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.Identifier;

public class CanadaModChestLootTableGenerator extends SimpleFabricLootTableProvider {
	public static final RegistryKey<LootTable> MAPLE_CABIN = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(CanadaMod.MOD_ID, "chests/maple_cabin"));

	public CanadaModChestLootTableGenerator(FabricDataOutput dataOutput,
            CompletableFuture<WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup, LootContextTypes.CHEST);
    }

    @Override
    public String getName() {
        return "Chest Loot Tables";
    }
	 
	@Override
	public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
		lootTableBiConsumer.accept(MAPLE_CABIN, LootTable.builder()

		        .pool(LootPool.builder()
		        	.rolls(ConstantLootNumberProvider.create(1))
					.with(ItemEntry.builder(CanadaItems.MAPLE_SAPLING))
		        )

		        .pool(LootPool.builder()
		        	.rolls(ConstantLootNumberProvider.create(1))
					.with(ItemEntry.builder(CanadaItems.DONAIR))
		        )

		        .pool(LootPool.builder()
		        	.rolls(ConstantLootNumberProvider.create(1))
					.with(ItemEntry.builder(CanadaItems.PIEROGI))
		        )

		        .pool(LootPool.builder()
		        	.rolls(ConstantLootNumberProvider.create(1))
					.with(ItemEntry.builder(CanadaItems.POUTINE))
		        )

		        .pool(LootPool.builder()
		        	.rolls(UniformLootNumberProvider.create(4, 5))
					.with(ItemEntry.builder(CanadaItems.MAPLE_SYRUP_BOTTLE))
		        )

		        .pool(LootPool.builder()
		        	.rolls(UniformLootNumberProvider.create(5, 6))
					.with(ItemEntry.builder(Items.GLASS_BOTTLE))
		        )

		        .pool(LootPool.builder()
		        	.rolls(UniformLootNumberProvider.create(3, 4))
					.with(ItemEntry.builder(CanadaItems.MAPLE_SAP))
		        )

		);
	}
}
