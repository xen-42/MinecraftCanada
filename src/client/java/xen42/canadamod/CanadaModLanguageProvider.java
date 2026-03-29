package xen42.canadamod;

import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.StringUtils;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.gen.structure.Structure;
import xen42.canadamod.entities.MapleBoatEntity;

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
		
		public void add(NoteBlockInstrument instrument, String value) {
			var serializedName = instrument.asString();
			String key;
			if(instrument.canBePitched()) {
				key = "gui.ipp.jei.instrument.%s".formatted(serializedName);
			} else {
				key = "gui.ipp.jei.mob.%s".formatted(serializedName);
			}
			add(key, value);
			add("jade.instrument.%s".formatted(serializedName), value);
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
			translationBuilder.add(CanadaItems.FLOUR, "Flour");
			
			translationBuilder.add(CanadaBlocks.MAPLE_PLANKS, "Maple Planks");
			translationBuilder.add(CanadaBlocks.MAPLE_BUTTON, "Maple Button");
			translationBuilder.add(CanadaBlocks.MAPLE_DOOR, "Maple Door");
			translationBuilder.add(CanadaBlocks.MAPLE_LEAVES, "Maple Leaves");
			translationBuilder.add(CanadaBlocks.MAPLE_LOG, "Maple Log");
			translationBuilder.add(CanadaBlocks.MAPLE_WOOD, "Maple Wood");
			translationBuilder.add(CanadaBlocks.STRIPPED_MAPLE_LOG, "Stripped Maple Log");
			translationBuilder.add(CanadaBlocks.STRIPPED_MAPLE_WOOD, "Stripped Maple Wood");
			translationBuilder.add(CanadaBlocks.MAPLE_SLAB, "Maple Slab");
			translationBuilder.add(CanadaBlocks.MAPLE_STAIRS, "Maple Stairs");
			translationBuilder.add(CanadaBlocks.MAPLE_TRAPDOOR, "Maple Trapdoor");
			translationBuilder.add(CanadaBlocks.MAPLE_HANGING_SIGN, "Maple Hanging Sign");
			translationBuilder.add(CanadaBlocks.MAPLE_SIGN, "Maple Sign");
			translationBuilder.add(CanadaBlocks.MAPLE_FENCE, "Maple Fence");
			translationBuilder.add(CanadaBlocks.MAPLE_FENCE_GATE, "Maple Fence Gate");
			translationBuilder.add(CanadaBlocks.MAPLE_PRESSURE_PLATE, "Maple Pressure Plate");

			translationBuilder.add(MapleBoatEntity.MAPLE_BOAT, "Maple Boat");
			translationBuilder.add(MapleBoatEntity.MAPLE_CHEST_BOAT, "Maple Boat with Chest");
			translationBuilder.add(CanadaItems.MAPLE_BOAT, "Maple Boat");
			translationBuilder.add(CanadaItems.MAPLE_CHEST_BOAT, "Maple Boat with Chest");

			translationBuilder.add(CanadaItems.TREE_TAP, "Tree Tap");
			translationBuilder.add(CanadaItems.MAPLE_SYRUP_BOTTLE, "Maple Syrup");
			translationBuilder.add(CanadaItems.MAPLE_SAPLING, "Maple Sapling");
			
			translationBuilder.add(CanadaItems.GRAVY, "Gravy");
			translationBuilder.add(CanadaItems.SAP, "Sap");
			translationBuilder.add(CanadaItems.MAPLE_SAP, "Maple Sap");
			translationBuilder.add(CanadaItems.RUBBER, "Rubber");
			translationBuilder.add(CanadaItems.CHEESE_CURD, "Cheese Curd");
			translationBuilder.add(CanadaItems.POUTINE, "Poutine");
			translationBuilder.add(CanadaItems.DONAIR, "Donair");
			translationBuilder.add(CanadaItems.PIEROGI, "Pierogi");
			translationBuilder.add(CanadaItems.BUTTER_TART, "Butter Tart");

			translationBuilder.add(CanadaBlocks.COOKING_POT, "Cooking Pot");
			translationBuilder.add("block." + CanadaMod.MOD_ID + ".cooking_pot", "Cooking Pot");

			translationBuilder.add(CanadaItems.BEAVER_SPAWN_EGG, "Beaver Spawn Egg");
			translationBuilder.add(CanadaItems.MOOSE_SPAWN_EGG, "Moose Spawn Egg");
			translationBuilder.add(CanadaItems.DUCK_SPAWN_EGG, "Duck Spawn Egg");
			translationBuilder.add(CanadaItems.GOOSE_SPAWN_EGG, "Goose Spawn Egg");
			translationBuilder.add(CanadaItems.GRIZZLY_SPAWN_EGG, "Grizzly Bear Spawn Egg");

			translationBuilder.add(CanadaItems.DUCK_EGG, "Duck Egg");
			translationBuilder.add(CanadaItems.GOOSE_EGG, "Goose Egg");

			translationBuilder.add(CanadaItems.ANTLERS, "Antler");
			translationBuilder.add(CanadaItems.VENISON, "Venison");
			translationBuilder.add(CanadaItems.COOKED_VENISON, "Cooked Venison");
			translationBuilder.add(CanadaItems.WATERFOWL, "Waterfowl");
			translationBuilder.add(CanadaItems.COOKED_WATERFOWL, "Cooked Waterfowl");
			
			translationBuilder.add(CanadaMod.MOOSE_ENTITY, "Moose");
			translationBuilder.add(CanadaMod.BEAVER_ENTITY, "Beaver");
			translationBuilder.add(CanadaMod.DUCK_ENTITY, "Duck");
			translationBuilder.add(CanadaMod.GOOSE_ENTITY, "Goose");
			translationBuilder.add(CanadaMod.GRIZZLY_ENTITY, "Grizzly Bear");

			translationBuilder.add(CanadaItems.THERMOS, "Thermos");
			translationBuilder.add(CanadaItems.BEAVER_HELMET, "Beaver Pelt Hat");
			translationBuilder.add(CanadaItems.MOOSE_HELMET, "Moose Antler Hat");
			translationBuilder.add(CanadaItems.MOOSE_HEAD, "Moose Head");

			translationBuilder.add(CanadaBlocks.RUBBER_BLOCK, "Rubber Block");

			translationBuilder.add("item.canadamod.thermos.empty.description", "Can hold a mixed stack of soups, drinks, and potions");
			translationBuilder.add("effect.canadamod.beaver_effect", "Busy Beaver");
			translationBuilder.add("effect.canadamod.moose_effect", "Moose's Strength");

			translationBuilder.add(CanadaTags.StructureTags.MAPLE_CABIN, "Maple Forest Cabin");
			translationBuilder.addFilledMap(CanadaTags.StructureTags.MAPLE_CABIN, "Maple Forest Cabin");
			
			translationBuilder.add(CanadaTags.ItemTags.MAPLE_LOGS, "Maple Logs");
			translationBuilder.add(CanadaTags.ItemTags.SAP, "Sap");
			translationBuilder.add(CanadaTags.ItemTags.RUBBER, "Rubber");
			translationBuilder.add(CanadaTags.ItemTags.CHEESE, "Cheese");
			translationBuilder.add(CanadaTags.ItemTags.CHEESE_FOODS, "Cheese");
			translationBuilder.add(CanadaTags.ItemTags.DAIRY_FOODS, "Dairy");
			translationBuilder.add(CanadaTags.ItemTags.FLOUR, "Flour");
			translationBuilder.add(CanadaTags.ItemTags.FLOUR_FOODS, "Flour");
			translationBuilder.add(CanadaTags.ItemTags.WHEAT_FLOUR, "Wheat Flour");
			translationBuilder.add(CanadaTags.ItemTags.SYRUP_DRINKS, "Syrup Drinks");
			translationBuilder.add(CanadaTags.ItemTags.SAUCES, "Sauces");
			translationBuilder.add(CanadaTags.BlockTags.MAPLE_LOGS, "Maple Logs");
			translationBuilder.addVillagerProfession(CanadaVillagers.SUGAR_SHACK_VILLAGER_KEY, "Lumberjack");

			translationBuilder.add(CanadaNoteBlockInstruments.MOOSE.get(), "Moose");

			translationBuilder.add("biome.canadamod.maple_forest", "Maple Forest");

			translationBuilder.add(CanadaSounds.SOUND_BEAVER_AMBIENT, "Beaver grunts");
			translationBuilder.add(CanadaSounds.SOUND_BEAVER_DEATH, "Beaver dies");
			translationBuilder.add(CanadaSounds.SOUND_BEAVER_HURT, "Beaver hurts");
			translationBuilder.add(CanadaSounds.SOUND_BEAVER_EAT, "Beaver eats");

			translationBuilder.add(CanadaSounds.SOUND_MOOSE_AMBIENT, "Moose calls");
			translationBuilder.add(CanadaSounds.SOUND_MOOSE_ANGRY, "Moose grunts angrily");
			translationBuilder.add(CanadaSounds.SOUND_MOOSE_DEATH, "Moose dies");
			translationBuilder.add(CanadaSounds.SOUND_MOOSE_HURT, "Moose hurts");
			translationBuilder.add(CanadaSounds.SOUND_MOOSE_GROW_ANTLER, "Moose grows antler");
			translationBuilder.add(CanadaSounds.SOUND_MOOSE_STRIP_ANTLER, "Moose loses antler");

			translationBuilder.add(CanadaSounds.ENTITY_GRIZZLY_BEAR_AMBIENT, "Grizzly Bear groans");
			translationBuilder.add(CanadaSounds.ENTITY_GRIZZLY_BEAR_AMBIENT_BABY, "Grizzly Bear hums");
			translationBuilder.add(CanadaSounds.ENTITY_GRIZZLY_BEAR_DEATH, "Grizzly Bear dies");
			translationBuilder.add(CanadaSounds.ENTITY_GRIZZLY_BEAR_HURT, "Grizzly Bear hurts");
			translationBuilder.add(CanadaSounds.ENTITY_GRIZZLY_BEAR_WARNING, "Grizzly Bear roars");
			
			translationBuilder.add(CanadaSounds.SOUND_DUCK_AMBIENT, "Duck quacks");
			translationBuilder.add(CanadaSounds.SOUND_DUCK_HURT, "Duck hurts");
			translationBuilder.add(CanadaSounds.SOUND_DUCK_DEATH, "Duck dies");
			translationBuilder.add(CanadaSounds.SOUND_DUCK_PLOP, "Duck plops");
			translationBuilder.add(CanadaSounds.SOUND_DUCK_FOOTSTEP, "Footsteps");
			
			translationBuilder.add(CanadaSounds.SOUND_GOOSE_PLOP, "Goose plops");
			translationBuilder.add(CanadaSounds.SOUND_GOOSE_AMBIENT, "Goose quacks");
			translationBuilder.add(CanadaSounds.SOUND_GOOSE_HURT, "Goose hurts");
			translationBuilder.add(CanadaSounds.SOUND_GOOSE_DEATH, "Goose dies");
			translationBuilder.add(CanadaSounds.SOUND_GOOSE_WARNING, "Goose hisses");
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