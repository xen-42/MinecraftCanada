package xen42.canadamod;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.gen.structure.Structure;

public abstract class CanadaModLanguageProvider extends FabricLanguageProvider {
    public CanadaModLanguageProvider(FabricDataOutput output, String languageCode, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    	super(output, languageCode, registryLookup);
    }

	public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
		generate(registryLookup, new ModTranslationBuilder(translationBuilder));
	}
	
	public String processValue(String value) {
		return value;
	}

	public abstract void generate(RegistryWrapper.WrapperLookup registryLookup, ModTranslationBuilder translationBuilder);
	
	public class ModTranslationBuilder implements TranslationBuilder {
		private final TranslationBuilder original;
		
		public ModTranslationBuilder(TranslationBuilder original) {
			this.original = original;
		}
		
		@Override
		public void add(String key, String value) {
			original.add(key, processValue(value));
		}
		
		@Override
		public void add(Block block, String value) {
			add(block.asItem(), value);
		}

		public void addVillagerProfession(RegistryKey<VillagerProfession> key, String value) {
			add("entity." + key.getValue().getNamespace() + ".villager." + key.getValue().getPath(), value);
		}

		public void addFilledMap(TagKey<Structure> structure, String value) {
			add("filled_map." + structure.id().getNamespace() + "." + structure.id().getPath(), value);
		}
	}
	
	public static class English extends CanadaModLanguageProvider {

		public English(FabricDataOutput output, String languageCode, CompletableFuture<WrapperLookup> registryLookup) {
			super(output, languageCode, registryLookup);
		}

		public English(FabricDataOutput output, CompletableFuture<WrapperLookup> registryLookup) {
			this(output, "en_us", registryLookup);
		}

		@Override
		public void generate(WrapperLookup registryLookup, ModTranslationBuilder translationBuilder) {
			translationBuilder.add(CanadaItems.PELT, "Pelt");
			translationBuilder.add(CanadaBlocks.MAPLE_PLANKS, "Maple Planks");
			translationBuilder.add(CanadaBlocks.MAPLE_BUTTON, "Maple Button");
			translationBuilder.add(CanadaBlocks.MAPLE_DOOR, "Maple Door");
			translationBuilder.add(CanadaBlocks.MAPLE_LEAVES, "Maple Leaves");
			translationBuilder.add(CanadaBlocks.MAPLE_LOG, "Maple Log");
			translationBuilder.add(CanadaBlocks.MAPLE_WOOD, "Maple Wood");
			translationBuilder.add(CanadaBlocks.STRIPPED_MAPLE_LOG, "Stripped Maple Wood");
			translationBuilder.add(CanadaBlocks.STRIPPED_MAPLE_WOOD, "Stripped Maple Wood");
			translationBuilder.add(CanadaBlocks.MAPLE_SLAB, "Maple Slab");
			translationBuilder.add(CanadaBlocks.MAPLE_STAIRS, "Maple Stairs");
			translationBuilder.add(CanadaBlocks.MAPLE_TRAPDOOR, "Maple Trapdoor");
			translationBuilder.add(CanadaBlocks.MAPLE_HANGING_SIGN, "Maple Hanging Sign");
			translationBuilder.add(CanadaBlocks.MAPLE_SIGN, "Maple Sign");
		}
	}
	
	public static class EnglishUpsideDown extends English {
		private static final String NORMAL_CHARS = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_,;.?!/\\'\"";
		private static final String UPSIDE_DOWN_CHARS = " ɐqɔpǝɟᵷɥᴉɾʞꞁɯuodbɹsʇnʌʍxʎzⱯᗺƆᗡƎℲ⅁HIՐꞰꞀWNOԀὉᴚS⟘∩ΛMXʎZ0⥝ᘔƐ߈ϛ9ㄥ86‾'⸵˙¿¡\\/,„";

		public EnglishUpsideDown(FabricDataOutput output, CompletableFuture<WrapperLookup> registryLookup) {
			super(output, "en_ud", registryLookup);
		}

		@Override
		public String processValue(String value) {
			return toUpsideDown(value);
		}

		private static String toUpsideDown(String name) {
			StringBuilder builder = new StringBuilder();

			for (int i = name.length() - 1; i >= 0; i--) {
				if (i > 2 && name.substring(i - 3, i + 1).equals("%1$s")) {
					builder.append(name, i - 3, i + 1);
					i -= 4;
					continue;
				}

				if (i < 0)
					continue;

				char normalChar = name.charAt(i);
				int normalIndex = NORMAL_CHARS.indexOf(normalChar);
				if (normalIndex < 0) {
					builder.append(normalChar);
				} else {
					char upsideDown = UPSIDE_DOWN_CHARS.charAt(normalIndex);
					builder.append(upsideDown);
				}
			}

			return builder.toString();
		}
		
	}
}