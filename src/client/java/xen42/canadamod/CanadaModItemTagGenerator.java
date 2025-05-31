package xen42.canadamod;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.ItemTags;

public class CanadaModItemTagGenerator extends ItemTagProvider {
	public CanadaModItemTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture,
			CanadaModBlockTagGenerator blockTagProvider) {
		super(output, registriesFuture, blockTagProvider);
	}

	@Override
	public String getName() {
		return "PeacefulModItemTagGenerator";
	}
	
	@Override
	protected void configure(WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(CanadaTags.ItemTags.MAPLE_LOGS)
            .add(CanadaBlocks.MAPLE_LOG.asItem(), CanadaBlocks.STRIPPED_MAPLE_LOG.asItem(),
				CanadaBlocks.MAPLE_WOOD.asItem(), CanadaBlocks.STRIPPED_MAPLE_WOOD.asItem());
		
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


	}
}