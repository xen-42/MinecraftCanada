package xen42.canadamod;

import com.google.common.collect.ImmutableSet;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapDecorationType;
import net.minecraft.item.map.MapDecorationTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers.Factory;
import net.minecraft.village.TradeOffers.SellMapFactory;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.poi.PointOfInterestType;

public class CanadaVillagers {
    public static final RegistryKey<PointOfInterestType> COOKING_POT_KET = RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Identifier.of(CanadaMod.MOD_ID, "cooking_pot_poi"));
    public static final PointOfInterestType COOKING_POT_POI = registerNewPOI("cooking_pot_poi", CanadaBlocks.COOKING_POT);

    public static final RegistryKey<VillagerProfession> SUGAR_SHACK_VILLAGER_KEY = RegistryKey.of(RegistryKeys.VILLAGER_PROFESSION, Identifier.of(CanadaMod.MOD_ID, "sugar_shack"));
    public static final VillagerProfession SUGAR_SHACK_VILLAGER = registerNewProfession(SUGAR_SHACK_VILLAGER_KEY, COOKING_POT_KET, SoundEvents.ENTITY_VILLAGER_WORK_BUTCHER);

    public static VillagerProfession registerNewProfession(RegistryKey<VillagerProfession> key, RegistryKey<PointOfInterestType> poi, SoundEvent sound) {
        var profession = new VillagerProfession(
                Text.translatable("entity." + key.getValue().getNamespace() + ".villager." + key.getValue().getPath()),
                entry -> entry.matchesKey(poi),
                entry -> entry.matchesKey(poi),
                ImmutableSet.of(),
                ImmutableSet.of(),
                sound
            );
        return Registry.register(Registries.VILLAGER_PROFESSION, key.getValue(), profession);
    }

    public static PointOfInterestType registerNewPOI(String name, Block block) {
        return PointOfInterestHelper.register(Identifier.of(CanadaMod.MOD_ID, name), 1, 1, block);
    }

    private static TradeOffer SellMap(Entity entity, Random random, int price, TagKey<Structure> structure, RegistryEntry<MapDecorationType> decoration, int maxUses, int experience) {
    	return new SellMapFactory(price, structure, 
    		"filled_map." + structure.id().getNamespace() + "." + structure.id().getPath(),
    		decoration, maxUses, experience).create(entity, random);
    }

    public static void initialize() {
        // CUSTOM VILLAGER
        TradeOfferHelper.registerVillagerOffers(SUGAR_SHACK_VILLAGER_KEY, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
				new TradedItem(CanadaItems.SAP, 4),
				new ItemStack(Items.EMERALD, 1), 16, 2, 0.05f));
            factories.add((entity, random) -> new TradeOffer(
				new TradedItem(CanadaItems.MAPLE_SAP, 4),
				new ItemStack(Items.EMERALD, 2), 16, 2, 0.05f));
		});

        TradeOfferHelper.registerVillagerOffers(SUGAR_SHACK_VILLAGER_KEY, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
				new TradedItem(CanadaItems.PELT, 9),
				new ItemStack(Items.EMERALD, 1), 12, 5, 0.05f));
            factories.add((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 2),
                new ItemStack(CanadaItems.CHEESE_CURD, 3), 12, 5, 0.05f));
		});

        TradeOfferHelper.registerVillagerOffers(SUGAR_SHACK_VILLAGER_KEY, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(
				new TradedItem(CanadaItems.ANTLERS, 4),
				new ItemStack(Items.EMERALD, 1), 12, 5, 0.05f));
            factories.add((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(CanadaItems.GRAVY, 1), 12, 5, 0.05f));
		});

        TradeOfferHelper.registerVillagerOffers(SUGAR_SHACK_VILLAGER_KEY, 4, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 3),
                new ItemStack(CanadaItems.MAPLE_SYRUP_BOTTLE, 1), 12, 5, 0.05f));
            factories.add((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 3),
                new ItemStack(CanadaItems.POUTINE, 1), 12, 5, 0.05f));
		});

        TradeOfferHelper.registerVillagerOffers(SUGAR_SHACK_VILLAGER_KEY, 5, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 12),
                new ItemStack(CanadaItems.MOOSE_HELMET, 1), 12, 5, 0.05f));
            factories.add((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 12),
                new ItemStack(CanadaItems.BEAVER_HELMET, 1), 12, 5, 0.05f));
		});

		TradeOfferHelper.registerWanderingTraderOffers(builder -> {
			builder.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_SPECIAL_ITEMS_POOL, 
				new TradeOfferFactory(new TradeOffer(
					new TradedItem(Items.EMERALD, 2),
					new ItemStack(CanadaItems.MAPLE_SAPLING, 1), 12, 20, 0.05f)
				),
                new TradeOfferFactory(new TradeOffer(
					new TradedItem(Items.EMERALD, 4),
					new ItemStack(CanadaItems.MOOSE_HEAD, 1), 12, 20, 0.05f)
				)
			);
		});

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CARTOGRAPHER, 3, factories -> {
			// Map to sugar shack
			factories.add((entity, random) -> SellMap(entity, random, 10,
				CanadaTags.StructureTags.SUGAR_SHACK, MapDecorationTypes.TARGET_X, 4, 15));
		});
    }

    private static class TradeOfferFactory implements Factory {
		private TradeOffer _tradeOffer;

		public TradeOfferFactory(TradeOffer tradeOffer) {
			_tradeOffer = tradeOffer;
		}

		@Override
		public TradeOffer create(Entity arg0, Random arg1) {
			return _tradeOffer.copy();
		}
	}
}
