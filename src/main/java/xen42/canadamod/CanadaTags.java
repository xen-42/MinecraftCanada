package xen42.canadamod;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;

public class CanadaTags {
	public class BlockTags {
		public static final TagKey<Block> MAPLE_LOGS = ofBlock("maple_logs");
	}
	
	public class ItemTags {
        public static final TagKey<Item> MAPLE_LOGS = ofItem("maple_logs");
        public static final TagKey<Item> RED_MEAT = ofItem("red_meat");
        public static final TagKey<Item> SAP = ofItem("sap");
	}
	
	public class StructureTags {
		public static final TagKey<Structure> SUGAR_SHACK = ofStructure("sugar_shack");
	}

	public static TagKey<Block> ofBlock(String name) {
		return TagKey.of(RegistryKeys.BLOCK, Identifier.of(CanadaMod.MOD_ID, name));
	}

	public static TagKey<Item> ofItem(String name) {
		return TagKey.of(RegistryKeys.ITEM, Identifier.of(CanadaMod.MOD_ID, name));
	}
	
	public static TagKey<EntityType<?>> ofEntity(String name) {
		return TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CanadaMod.MOD_ID, name));
	}
	
	public static TagKey<Structure> ofStructure(String name) {
		return TagKey.of(RegistryKeys.STRUCTURE, Identifier.of(CanadaMod.MOD_ID, name));
	}
}