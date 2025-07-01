package xen42.canadamod;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import com.google.common.collect.Maps;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BoatItem;
import net.minecraft.item.BundleItem;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.SignItem;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;
import xen42.canadamod.entities.MapleBoatEntity;
import xen42.canadamod.item.DispensibleSpawnEggItem;
import xen42.canadamod.item.DurabilityFoodItem;
import xen42.canadamod.item.ThermosContentsComponent;
import xen42.canadamod.item.ThermosItem;

public class CanadaItems {
    public static final Item PELT = register("pelt", Item::new, new Item.Settings());
    public static final Item FLOUR = register("flour", Item::new, new Item.Settings());
    public static final Item CHEESE_CURD = register("cheese_curd", Item::new, new Item.Settings()
        .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.1f).build()));
    public static final Item POUTINE = register("poutine", DurabilityFoodItem::new, new Item.Settings().rarity(Rarity.UNCOMMON)
        .maxCount(1).maxDamage(8).recipeRemainder(Items.BOWL).useRemainder(Items.BOWL)
        .food(new FoodComponent.Builder().nutrition(10).saturationModifier(2f).build()));
    public static final Item DONAIR = register("donair", Item::new, new Item.Settings().rarity(Rarity.UNCOMMON)
        .food(new FoodComponent.Builder().nutrition(6).saturationModifier(2f).build()));
    public static final Item PIEROGI = register("pierogi", Item::new, new Item.Settings().rarity(Rarity.UNCOMMON)
        .food(new FoodComponent.Builder().nutrition(6).saturationModifier(2f).build()));

    public static final Item BEAVER_SPAWN_EGG = register("beaver_spawn_egg", (settings) -> 
        new DispensibleSpawnEggItem(CanadaMod.BEAVER_ENTITY, settings), new Item.Settings());

    public static final Item MOOSE_SPAWN_EGG = register("moose_spawn_egg", (settings) -> 
        new DispensibleSpawnEggItem(CanadaMod.MOOSE_ENTITY, settings), new Item.Settings());

    public static final Item TREE_TAP = register("tree_tap", (settings) -> new BlockItem(CanadaBlocks.TREE_TAP, settings), new Item.Settings());
    public static final Item MAPLE_SAPLING = register("maple_sapling", (settings) -> new BlockItem(CanadaBlocks.MAPLE_SAPLING, settings), new Item.Settings());

    public static final FoodComponent MAPLE_SYRUP_FOOD = new FoodComponent.Builder().nutrition(4).saturationModifier(1.4F).alwaysEdible().build();
    public static final ConsumableComponent MAPLE_SYRUP_CONSUME = ConsumableComponents.drink().consumeSeconds(2.0F)
        .sound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK).consumeEffect(new ApplyEffectsConsumeEffect(List.of(
        new StatusEffectInstance(StatusEffects.HASTE, 600, 1)
    ))).build();

    public static final FoodComponent SAP_FOOD = new FoodComponent.Builder().nutrition(3).saturationModifier(0.1F).alwaysEdible().build();
    public static final ConsumableComponent SAP_CONSUME = ConsumableComponents.drink().consumeSeconds(2.0F)
        .sound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK).consumeEffect(new ApplyEffectsConsumeEffect(List.of(
        new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 1), 
        new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1)
    ))).build();
    
    public static final FoodComponent GRAVY_FOOD = new FoodComponent.Builder().nutrition(4).saturationModifier(1.0F).alwaysEdible().build();
    public static final ConsumableComponent GRAVY_CONSUME = ConsumableComponents.drink().consumeSeconds(2.0F)
        .sound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK).build();

    public static final Item MAPLE_SYRUP_BOTTLE = register("maple_syrup_bottle", Item::new, new Item.Settings()
        .recipeRemainder(Items.GLASS_BOTTLE).food(MAPLE_SYRUP_FOOD, MAPLE_SYRUP_CONSUME)
        .useRemainder(Items.GLASS_BOTTLE).maxCount(1));


    public static final Item MAPLE_SAP = register("maple_sap", Item::new, new Item.Settings().food(SAP_FOOD));
    public static final Item SAP = register("sap", Item::new, new Item.Settings().food(SAP_FOOD, SAP_CONSUME));

    public static final Item RUBBER = register("rubber", Item::new, new Item.Settings());

    private static Map<EquipmentType, Integer> NO_DEFENSE_MAP = Maps.newEnumMap(Map.of(EquipmentType.BOOTS, 0, EquipmentType.LEGGINGS, 0, EquipmentType.CHESTPLATE, 0, EquipmentType.HELMET, 0, EquipmentType.BODY, 0));
    public static final ArmorMaterial NO_ARMOR = new ArmorMaterial(5, NO_DEFENSE_MAP, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, ItemTags.REPAIRS_LEATHER_ARMOR, EquipmentAssetKeys.LEATHER);

    public static final Item MOOSE_HEAD = register("moose_head", (settings) -> {
        return new VerticallyAttachableBlockItem(CanadaBlocks.MOOSE_HEAD, CanadaBlocks.MOOSE_WALL_HEAD, Direction.DOWN, settings);
    }, new Item.Settings().armor(NO_ARMOR, EquipmentType.HELMET).rarity(Rarity.UNCOMMON));

    public static final Item GRAVY = register("gravy", Item::new, new Item.Settings()
        .recipeRemainder(Items.GLASS_BOTTLE).food(GRAVY_FOOD, GRAVY_CONSUME)
        .useRemainder(Items.GLASS_BOTTLE).maxCount(1));

    public static final Item VENISON = register("venison", Item::new, (new Item.Settings()).food(FoodComponents.MUTTON));
    public static final Item COOKED_VENISON = register("cooked_venison", Item::new, (new Item.Settings()).food(FoodComponents.COOKED_MUTTON));
    public static final Item ANTLERS = register("antlers", Item::new, new Item.Settings());

    public static final Item MAPLE_BOAT = register("maple_boat", settings -> 
        new BoatItem(MapleBoatEntity.MAPLE_BOAT, settings), (new Item.Settings()).maxCount(1));
    public static final Item MAPLE_CHEST_BOAT = register("maple_chest_boat", settings -> 
        new BoatItem(MapleBoatEntity.MAPLE_CHEST_BOAT, settings), (new Item.Settings()).maxCount(1));

    private static <T> ComponentType<T> registerContentsComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, builderOperator.apply(ComponentType.builder()).build());
    }
    public static final ComponentType<ThermosContentsComponent> THERMOS_CONTENTS = registerContentsComponent("thermos_contents", (builder) -> {
        return builder.codec(ThermosContentsComponent.CODEC).packetCodec(ThermosContentsComponent.PACKET_CODEC).cache();
    });
    public static final Item THERMOS = register("thermos",
        settings -> new ThermosItem(settings), (new Item.Settings()).maxCount(1).component(THERMOS_CONTENTS, ThermosContentsComponent.DEFAULT));

    public static final Item BEAVER_HELMET = register("beaver_helmet", Item::new, 
        new Item.Settings().armor(NO_ARMOR, EquipmentType.HELMET).rarity(Rarity.UNCOMMON));
    public static final Item MOOSE_HELMET = register("moose_helmet", Item::new, 
        new Item.Settings().armor(NO_ARMOR, EquipmentType.HELMET).rarity(Rarity.UNCOMMON));

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
            itemGroup.add(ANTLERS);
            itemGroup.add(FLOUR);
            itemGroup.add(SAP);
            itemGroup.add(MAPLE_SAP);
            itemGroup.add(RUBBER);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((itemGroup) -> { 
            for (var block : CanadaBlocks.MAPLE_BLOCKS) {
                itemGroup.add(block.asItem());
            }
            itemGroup.add(CanadaBlocks.RUBBER_BLOCK);
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
            itemGroup.add(MOOSE_HEAD);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((itemGroup) -> { 
            itemGroup.add(MAPLE_BOAT);
            itemGroup.add(MAPLE_CHEST_BOAT);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register((itemGroup) -> { 
            itemGroup.add(MAPLE_SYRUP_BOTTLE);
            itemGroup.add(GRAVY);
            itemGroup.add(CHEESE_CURD);
            itemGroup.add(POUTINE);
            itemGroup.add(DONAIR);
            itemGroup.add(PIEROGI);
            itemGroup.add(VENISON);
            itemGroup.add(COOKED_VENISON);
            itemGroup.add(THERMOS);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register((itemGroup) -> {
            itemGroup.add(BEAVER_SPAWN_EGG);
            itemGroup.add(MOOSE_SPAWN_EGG);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> {
            itemGroup.add(BEAVER_HELMET);
            itemGroup.add(MOOSE_HELMET);
        });
    }
}
