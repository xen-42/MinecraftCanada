package xen42.canadamod;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

public class CanadaModItemTagGenerator extends ItemTagProvider {
	public CanadaModItemTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture,
			CanadaModBlockTagGenerator blockTagProvider) {
		super(output, registriesFuture, blockTagProvider);
	}

	@Override
	public String getName() {
		return "CanadaModItemTagGenerator";
	}
	
	@Override
	protected void configure(WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(CanadaTags.ItemTags.MAPLE_LOGS)
            .add(CanadaBlocks.MAPLE_LOG.asItem(), CanadaBlocks.STRIPPED_MAPLE_LOG.asItem(),
				CanadaBlocks.MAPLE_WOOD.asItem(), CanadaBlocks.STRIPPED_MAPLE_WOOD.asItem());
		
		this.getOrCreateTagBuilder(ItemTags.SAPLINGS).add(CanadaBlocks.MAPLE_SAPLING.asItem());
		
		this.getOrCreateTagBuilder(ItemTags.CHEST_BOATS).add(CanadaItems.MAPLE_CHEST_BOAT);
		this.getOrCreateTagBuilder(ItemTags.BOATS).add(CanadaItems.MAPLE_BOAT);

		this.getOrCreateTagBuilder(ItemTags.PLANKS).add(CanadaBlocks.MAPLE_PLANKS.asItem());
		this.getOrCreateTagBuilder(ItemTags.LEAVES).add(CanadaBlocks.MAPLE_LEAVES.asItem());
		this.getOrCreateTagBuilder(ItemTags.LOGS).add(CanadaBlocks.MAPLE_LOG.asItem(),
			CanadaBlocks.MAPLE_WOOD.asItem(), CanadaBlocks.STRIPPED_MAPLE_LOG.asItem(), CanadaBlocks.STRIPPED_MAPLE_WOOD.asItem());
		this.getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN).add(CanadaBlocks.MAPLE_LOG.asItem(),
			CanadaBlocks.MAPLE_WOOD.asItem(), CanadaBlocks.STRIPPED_MAPLE_LOG.asItem(), CanadaBlocks.STRIPPED_MAPLE_WOOD.asItem());
		this.getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS).add(CanadaBlocks.MAPLE_BUTTON.asItem());
		this.getOrCreateTagBuilder(ItemTags.WOODEN_DOORS).add(CanadaBlocks.MAPLE_DOOR.asItem());
		this.getOrCreateTagBuilder(ItemTags.WOODEN_FENCES).add(CanadaBlocks.MAPLE_FENCE.asItem());
		this.getOrCreateTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(CanadaBlocks.MAPLE_PRESSURE_PLATE.asItem());
		this.getOrCreateTagBuilder(ItemTags.WOODEN_SLABS).add(CanadaBlocks.MAPLE_SLAB.asItem());
		this.getOrCreateTagBuilder(ItemTags.WOODEN_STAIRS).add(CanadaBlocks.MAPLE_STAIRS.asItem());
		this.getOrCreateTagBuilder(ItemTags.WOODEN_TRAPDOORS).add(CanadaBlocks.MAPLE_TRAPDOOR.asItem());

		this.getOrCreateTagBuilder(CanadaTags.ItemTags.RAW_MEAT).add(CanadaItems.VENISON);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.SAP).add(CanadaItems.SAP, CanadaItems.MAPLE_SAP);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.RUBBER).add(CanadaItems.RUBBER);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.CHEESE).add(CanadaItems.CHEESE_CURD);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.FLOUR).add(CanadaItems.FLOUR);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.FOODS)
			.add(CanadaItems.COOKED_VENISON, CanadaItems.DONAIR, CanadaItems.PIEROGI, CanadaItems.GRAVY, 
				CanadaItems.CHEESE_CURD, CanadaItems.POUTINE, CanadaItems.MAPLE_SYRUP_BOTTLE);

		this.getOrCreateTagBuilder(ItemTags.HEAD_ARMOR).add(CanadaItems.BEAVER_HELMET, CanadaItems.MOOSE_HELMET);
		this.getOrCreateTagBuilder(ItemTags.HEAD_ARMOR_ENCHANTABLE).add(CanadaItems.BEAVER_HELMET, CanadaItems.MOOSE_HELMET);

		this.getOrCreateTagBuilder(ItemTags.SKULLS).add(CanadaItems.MOOSE_HEAD);

	}
}