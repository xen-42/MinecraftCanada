package xen42.canadamod;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

public class CanadaModItemTagGenerator extends ItemTagProvider {
	public CanadaModItemTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture,
			CanadaModBlockTagGenerator blockTagProvider) {
		super(output, registriesFuture, blockTagProvider);
	}

	@Override
	public String getName() {
		return "Item Tags";
	}
	
	@Override
	protected void configure(WrapperLookup wrapperLookup) {
		this.copy(CanadaTags.BlockTags.MAPLE_LOGS, CanadaTags.ItemTags.MAPLE_LOGS);

		this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
		
		this.getOrCreateTagBuilder(ItemTags.CHEST_BOATS).add(CanadaItems.MAPLE_CHEST_BOAT);
		this.getOrCreateTagBuilder(ItemTags.BOATS).add(CanadaItems.MAPLE_BOAT);

		this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
		this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
		this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
		this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
		this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
		this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
		this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
		this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
		this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
		this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
		this.copy(BlockTags.FENCES, ItemTags.FENCES);
		this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
		this.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
		this.copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
		this.copy(ConventionalBlockTags.WOODEN_FENCES, ConventionalItemTags.WOODEN_FENCES);
		this.copy(ConventionalBlockTags.FENCES, ConventionalItemTags.FENCES);
		this.copy(ConventionalBlockTags.WOODEN_FENCE_GATES, ConventionalItemTags.WOODEN_FENCE_GATES);
		this.copy(ConventionalBlockTags.FENCE_GATES, ConventionalItemTags.FENCE_GATES);
		this.copy(ConventionalBlockTags.STRIPPED_LOGS, ConventionalItemTags.STRIPPED_LOGS);
		this.copy(ConventionalBlockTags.STRIPPED_WOODS, ConventionalItemTags.STRIPPED_WOODS);

		this.getOrCreateTagBuilder(ItemTags.MEAT).add(CanadaItems.VENISON, CanadaItems.COOKED_VENISON, CanadaItems.WATERFOWL, CanadaItems.COOKED_WATERFOWL);
		this.getOrCreateTagBuilder(ConventionalItemTags.RAW_MEAT_FOODS).add(CanadaItems.VENISON, CanadaItems.WATERFOWL);
		this.getOrCreateTagBuilder(ConventionalItemTags.COOKED_MEAT_FOODS).add(CanadaItems.COOKED_VENISON, CanadaItems.COOKED_WATERFOWL);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.SAUCES_GRAVY).add(CanadaItems.GRAVY);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.CONDIMENTS_GRAVY).add(CanadaItems.GRAVY);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.SAUCES).addTag(CanadaTags.ItemTags.SAUCES_GRAVY);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.CONDIMENTS).addTag(CanadaTags.ItemTags.CONDIMENTS_GRAVY);
		this.getOrCreateTagBuilder(ConventionalItemTags.SOUP_FOODS).add(CanadaItems.POUTINE);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.SAP).add(CanadaItems.SAP, CanadaItems.MAPLE_SAP);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.RUBBER).add(CanadaItems.RUBBER);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.CHEESE).add(CanadaItems.CHEESE_CURD);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.CHEESE_FOODS).add(CanadaItems.CHEESE_CURD);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.DAIRY_FOODS).add(CanadaItems.CHEESE_CURD);

		this.getOrCreateTagBuilder(CanadaTags.ItemTags.FLOUR).add(CanadaItems.FLOUR);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.FLOUR_FOODS).add(CanadaItems.FLOUR);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.WHEAT_FLOUR).add(CanadaItems.FLOUR);

		this.getOrCreateTagBuilder(ConventionalItemTags.FOODS)
			.add(CanadaItems.COOKED_VENISON, CanadaItems.DONAIR, CanadaItems.PIEROGI, CanadaItems.GRAVY, 
				CanadaItems.CHEESE_CURD, CanadaItems.POUTINE, CanadaItems.MAPLE_SYRUP_BOTTLE, CanadaItems.COOKED_WATERFOWL, CanadaItems.BUTTER_TART)
			.addTags(CanadaTags.ItemTags.FLOUR_FOODS, CanadaTags.ItemTags.CHEESE_FOODS, CanadaTags.ItemTags.SAUCES);

		this.getOrCreateTagBuilder(CanadaTags.ItemTags.SYRUP_DRINKS).add(CanadaItems.MAPLE_SYRUP_BOTTLE);
		this.getOrCreateTagBuilder(ConventionalItemTags.DRINKS).addTag(CanadaTags.ItemTags.SYRUP_DRINKS);
		this.getOrCreateTagBuilder(ConventionalItemTags.DRINK_CONTAINING_BOTTLE).add(CanadaItems.MAPLE_SYRUP_BOTTLE);

		this.getOrCreateTagBuilder(CanadaTags.ItemTags.HATS).add(CanadaItems.BEAVER_HELMET, CanadaItems.MOOSE_HELMET);
		this.getOrCreateTagBuilder(ItemTags.EQUIPPABLE_ENCHANTABLE).addTag(CanadaTags.ItemTags.HATS);
		this.getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE).addTag(CanadaTags.ItemTags.HATS);
		this.getOrCreateTagBuilder(ItemTags.HEAD_ARMOR_ENCHANTABLE).addTag(CanadaTags.ItemTags.HATS);
		this.getOrCreateTagBuilder(ItemTags.FREEZE_IMMUNE_WEARABLES).addTag(CanadaTags.ItemTags.HATS);
		this.getOrCreateTagBuilder(ConventionalItemTags.ARMORS).addTag(CanadaTags.ItemTags.HATS);

		this.getOrCreateTagBuilder(ItemTags.SKULLS).add(CanadaItems.MOOSE_HEAD);
		this.getOrCreateTagBuilder(ItemTags.NOTEBLOCK_TOP_INSTRUMENTS).add(CanadaItems.MOOSE_HEAD);

		this.getOrCreateTagBuilder(CanadaTags.ItemTags.REPAIRS_PELT_ARMOR).add(CanadaItems.PELT);

		this.getOrCreateTagBuilder(ItemTags.EGGS).add(CanadaItems.DUCK_EGG, CanadaItems.GOOSE_EGG);
		this.getOrCreateTagBuilder(ConventionalItemTags.EGGS).add(CanadaItems.DUCK_EGG, CanadaItems.GOOSE_EGG);

		this.getOrCreateTagBuilder(CanadaTags.ItemTags.BEAVER_BREED_FOOD).add(Items.STICK);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.BEAVER_FRENZY_FOOD).add(CanadaItems.DONAIR, CanadaItems.POUTINE, CanadaItems.PIEROGI);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.BEAVER_FATIGUE_REFRESH_FOOD).add(CanadaItems.MAPLE_SYRUP_BOTTLE);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.DUCK_FOOD).add(Items.BREAD);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.GOOSE_FOOD).add(Items.BREAD);
		this.getOrCreateTagBuilder(CanadaTags.ItemTags.MOOSE_FOOD).add(Items.SEAGRASS).addTag(ItemTags.LEAVES);

	}
}