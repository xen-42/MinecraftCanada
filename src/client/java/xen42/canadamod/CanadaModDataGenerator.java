package xen42.canadamod;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CanadaModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(CanadaModModelGenerator::new);
		pack.addProvider(CanadaModRecipeGenerator::new);
		pack.addProvider(CanadaModLanguageProvider.English::new);
		pack.addProvider(CanadaModLanguageProvider.EnglishUpsideDown::new);
		CanadaModBlockTagGenerator blockTagProvider = pack.addProvider(CanadaModBlockTagGenerator::new);
		pack.addProvider((output, registries) -> new CanadaModItemTagGenerator(output, registries, blockTagProvider));
		pack.addProvider(CanadaModEntityLootTableGenerator::new);
	}
}
