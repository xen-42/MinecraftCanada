package xen42.canadamod;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class CanadaModBlockTagGenerator extends BlockTagProvider {
    public CanadaModBlockTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "PeacefulModBlockTagGenerator";
    }
    
    @Override
    protected void configure(WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(CanadaTags.BlockTags.MAPLE_LOGS)
            .add(CanadaBlocks.MAPLE_LOG, CanadaBlocks.STRIPPED_MAPLE_LOG, CanadaBlocks.MAPLE_WOOD, CanadaBlocks.STRIPPED_MAPLE_WOOD);
    }
}