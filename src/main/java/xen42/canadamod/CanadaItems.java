package xen42.canadamod;

import java.util.List;
import java.util.function.Function;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BoatItem;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.SignItem;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class CanadaItems {
    public static final Item PELT = register("pelt", Item::new, new Item.Settings());
    public static final Item FLOUR = register("flour", Item::new, new Item.Settings());
    public static final Item TREE_TAP = register("tree_tap", (settings) -> new BlockItem(CanadaBlocks.TREE_TAP, settings), new Item.Settings());
    public static final Item MAPLE_SAPLING = register("maple_sapling", (settings) -> new BlockItem(CanadaBlocks.MAPLE_SAPLING, settings), new Item.Settings());

    public static final FoodComponent MAPLE_SYRUP_FOOD = new FoodComponent.Builder().nutrition(6).saturationModifier(0.1F).alwaysEdible().build();
    public static final ConsumableComponent MAPLE_SYRUP_CONSUME = ConsumableComponents.drink().consumeSeconds(2.0F)
        .sound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK).consumeEffect(new ApplyEffectsConsumeEffect(List.of(
        new StatusEffectInstance(StatusEffects.SPEED, 600, 1), 
        new StatusEffectInstance(StatusEffects.REGENERATION, 60, 1)
        ))).build();

    public static final FoodComponent SAP_FOOD = new FoodComponent.Builder().nutrition(3).saturationModifier(0.1F).alwaysEdible().build();
    public static final ConsumableComponent SAP_CONSUME = ConsumableComponents.drink().consumeSeconds(2.0F)
        .sound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK).consumeEffect(new ApplyEffectsConsumeEffect(List.of(
        new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 1), 
        new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1)
        ))).build();

    public static final Item MAPLE_SYRUP_BOTTLE = register("maple_syrup_bottle", Item::new, new Item.Settings()
        .recipeRemainder(Items.GLASS_BOTTLE).food(MAPLE_SYRUP_FOOD, MAPLE_SYRUP_CONSUME)
        .useRemainder(Items.GLASS_BOTTLE).maxCount(16));
    public static final Item SAP_BOTTLE = register("sap_bottle", Item::new, new Item.Settings()
        .recipeRemainder(Items.GLASS_BOTTLE).food(SAP_FOOD, SAP_CONSUME)
        .useRemainder(Items.GLASS_BOTTLE).maxCount(16));
    public static final Item LIQUID_RESIN = register("liquid_resin_bottle", Item::new, new Item.Settings()
        .recipeRemainder(Items.GLASS_BOTTLE).food(SAP_FOOD, SAP_CONSUME)
        .useRemainder(Items.GLASS_BOTTLE).maxCount(16));

    public static final Item MAPLE_BOAT = register("maple_boat", settings -> 
        new BoatItem(MapleBoatEntity.MAPLE_BOAT, settings), (new Item.Settings()).maxCount(1));
    public static final Item MAPLE_CHEST_BOAT = register("maple_chest_boat", settings -> 
        new BoatItem(MapleBoatEntity.MAPLE_CHEST_BOAT, settings), (new Item.Settings()).maxCount(1));

    public static Item MAPLE_HANGING_SIGN_ITEM, MAPLE_SIGN_ITEM;

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
        
        MAPLE_SIGN_ITEM = register("maple_sign", settings -> 
            new SignItem(CanadaBlocks.MAPLE_SIGN, CanadaBlocks.MAPLE_WALL_SIGN, settings),
            new Item.Settings().maxCount(16));

        // Add custom items to groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) -> { 
            itemGroup.add(PELT);
            itemGroup.add(FLOUR);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((itemGroup) -> { 
            for (var block : CanadaBlocks.MAPLE_BLOCKS) {
                itemGroup.add(block.asItem());
            }
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register((itemGroup) -> { 
            itemGroup.add(CanadaBlocks.MAPLE_LEAVES.asItem());
            itemGroup.add(CanadaBlocks.MAPLE_SAPLING.asItem());
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> { 
            itemGroup.add(MAPLE_SIGN_ITEM);
            itemGroup.add(MAPLE_HANGING_SIGN_ITEM);
            itemGroup.add(TREE_TAP);
            itemGroup.add(CanadaBlocks.COOKING_POT.asItem());
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((itemGroup) -> { 
            itemGroup.add(CanadaItems.MAPLE_BOAT);
            itemGroup.add(CanadaItems.MAPLE_CHEST_BOAT);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register((itemGroup) -> { 
            itemGroup.add(CanadaItems.MAPLE_SYRUP_BOTTLE);
            itemGroup.add(CanadaItems.SAP_BOTTLE);
            itemGroup.add(CanadaItems.LIQUID_RESIN);
        });
    }
}
