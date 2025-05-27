package xen42.canadamod;

import java.util.function.Function;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BoatItem;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class CanadaItems {
    public static final Item PELT = register("pelt", Item::new, new Item.Settings());

    public static final Item MAPLE_BOAT = register("maple_boat", settings -> 
        new BoatItem(MapleBoatEntity.MAPLE_BOAT, settings), (new Item.Settings()).maxCount(1));
    public static final Item MAPLE_CHEST_BOAT = register("maple_chest_boat", settings -> 
        new BoatItem(MapleBoatEntity.MAPLE_CHEST_BOAT, settings), (new Item.Settings()).maxCount(1));
    
    public static Item MAPLE_HANGING_SIGN_ITEM;

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
		// Create the item key.
		RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(CanadaMod.MOD_ID, name));

		// Create the item instance.
		Item item = itemFactory.apply(settings.registryKey(itemKey));

		// Register the item.
		Registry.register(Registries.ITEM, itemKey, item);

		return item;
	}

    public static void initialize() {
        MAPLE_HANGING_SIGN_ITEM = register("maple_hanging_sign", settings -> 
            new HangingSignItem(CanadaBlocks.MAPLE_HANGING_SIGN, CanadaBlocks.MAPLE_WALL_HANGING_SIGN, settings),
            new Item.Settings().maxCount(16));

        // Add custom items to groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) -> { 
            itemGroup.add(PELT);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((itemGroup) -> { 
            for (var block : CanadaBlocks.MAPLE_BLOCKS) {
                itemGroup.add(block.asItem());
            }
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> { 
            itemGroup.add(CanadaBlocks.MAPLE_SIGN.asItem());
            itemGroup.add(MAPLE_HANGING_SIGN_ITEM);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((itemGroup) -> { 
            itemGroup.add(CanadaItems.MAPLE_BOAT);
            itemGroup.add(CanadaItems.MAPLE_CHEST_BOAT);
        });
    }
}
