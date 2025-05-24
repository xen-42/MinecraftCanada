package xen42.canadamod;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

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
	}
}