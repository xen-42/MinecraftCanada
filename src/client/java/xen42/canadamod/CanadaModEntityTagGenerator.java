package xen42.canadamod;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.EntityTypeTagProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.EntityTypeTags;

public class CanadaModEntityTagGenerator extends EntityTypeTagProvider {

    public CanadaModEntityTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
        super(output, completableFuture);

    }

    @Override
	public String getName() {
		return "CanadaModEntityTagGenerator";
	}

    @Override
    protected void configure(WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(EntityTypeTags.CAN_EQUIP_SADDLE).add(CanadaMod.MOOSE_ENTITY);
        this.getOrCreateTagBuilder(EntityTypeTags.AQUATIC).add(CanadaMod.MOOSE_ENTITY).add(CanadaMod.BEAVER_ENTITY);
    }
    
}
