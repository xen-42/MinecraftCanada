package xen42.canadamod;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import xen42.canadamod.recipe.CookingPotRecipeJsonBuilder;

public class CanadaModRecipeGenerator extends FabricRecipeProvider {
    public CanadaModRecipeGenerator(FabricDataOutput generator, CompletableFuture<WrapperLookup> registriesFuture) {
        super(generator, registriesFuture);
    }

    @Override
    public String getName() {
        return "CanadaModRecipeGenerator";
    }

    @Override
    public Identifier getRecipeIdentifier(Identifier identifier) {
        return Identifier.of(CanadaMod.MOD_ID, identifier.getPath());
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {            
            @Override
            public void generate() {
                createShaped(RecipeCategory.MISC, Items.LEATHER)
                    .pattern("XX")
                    .pattern("XX")
                    .input('X', CanadaItems.PELT)
                    .criterion(hasItem(CanadaItems.PELT), conditionsFromItem(CanadaItems.PELT))
                    .offerTo(exporter);
                
                createShaped(RecipeCategory.TOOLS, CanadaItems.TREE_TAP)
                    .pattern("XX")
                    .pattern("Y ")
                    .input('X', Items.COPPER_INGOT)
                    .input('Y', Items.BUCKET)
                    .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                    .criterion(hasItem(Items.BUCKET), conditionsFromItem(Items.BUCKET))
                    .offerTo(exporter);
                
                createShapeless(RecipeCategory.BUILDING_BLOCKS, CanadaBlocks.MAPLE_PLANKS, 4)
                    .input(CanadaTags.ItemTags.MAPLE_LOGS)
                    .criterion(hasTag(CanadaTags.ItemTags.MAPLE_LOGS), conditionsFromTag(CanadaTags.ItemTags.MAPLE_LOGS))
                    .offerTo(exporter);
                
                createStairsRecipe(CanadaBlocks.MAPLE_STAIRS.asItem(), Ingredient.ofItem(CanadaBlocks.MAPLE_PLANKS.asItem()))
                    .criterion(hasItem(CanadaBlocks.MAPLE_PLANKS), conditionsFromItem(CanadaBlocks.MAPLE_PLANKS))
                    .offerTo(exporter);

                createButtonRecipe(CanadaBlocks.MAPLE_BUTTON.asItem(), Ingredient.ofItem(CanadaBlocks.MAPLE_PLANKS.asItem()))
                    .criterion(hasItem(CanadaBlocks.MAPLE_PLANKS), conditionsFromItem(CanadaBlocks.MAPLE_PLANKS))
                    .offerTo(exporter);

                createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, CanadaBlocks.MAPLE_SLAB.asItem(), Ingredient.ofItem(CanadaBlocks.MAPLE_PLANKS.asItem()))
                    .criterion(hasItem(CanadaBlocks.MAPLE_PLANKS), conditionsFromItem(CanadaBlocks.MAPLE_PLANKS))
                    .offerTo(exporter);

                createDoorRecipe(CanadaBlocks.MAPLE_DOOR.asItem(), Ingredient.ofItem(CanadaBlocks.MAPLE_PLANKS.asItem()))
                    .criterion(hasItem(CanadaBlocks.MAPLE_PLANKS), conditionsFromItem(CanadaBlocks.MAPLE_PLANKS))
                    .offerTo(exporter);

                createFenceGateRecipe(CanadaBlocks.MAPLE_FENCE_GATE.asItem(), Ingredient.ofItem(CanadaBlocks.MAPLE_PLANKS.asItem()))
                    .criterion(hasItem(CanadaBlocks.MAPLE_PLANKS), conditionsFromItem(CanadaBlocks.MAPLE_PLANKS))
                    .offerTo(exporter);

                createFenceRecipe(CanadaBlocks.MAPLE_FENCE.asItem(), Ingredient.ofItem(CanadaBlocks.MAPLE_PLANKS.asItem()))
                    .criterion(hasItem(CanadaBlocks.MAPLE_PLANKS), conditionsFromItem(CanadaBlocks.MAPLE_PLANKS))
                    .offerTo(exporter);

                createPressurePlateRecipe(RecipeCategory.BUILDING_BLOCKS, CanadaBlocks.MAPLE_PRESSURE_PLATE.asItem(), Ingredient.ofItem(CanadaBlocks.MAPLE_PLANKS.asItem()))
                    .criterion(hasItem(CanadaBlocks.MAPLE_PLANKS), conditionsFromItem(CanadaBlocks.MAPLE_PLANKS))
                    .offerTo(exporter);

                createSignRecipe(CanadaBlocks.MAPLE_SIGN.asItem(), Ingredient.ofItem(CanadaBlocks.MAPLE_PLANKS.asItem()))
                    .criterion(hasItem(CanadaBlocks.MAPLE_PLANKS), conditionsFromItem(CanadaBlocks.MAPLE_PLANKS))
                    .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                    .offerTo(exporter);
                
                createTrapdoorRecipe(CanadaBlocks.MAPLE_TRAPDOOR.asItem(), Ingredient.ofItem(CanadaBlocks.MAPLE_PLANKS.asItem()))
                    .criterion(hasItem(CanadaBlocks.MAPLE_PLANKS), conditionsFromItem(CanadaBlocks.MAPLE_PLANKS))
                    .offerTo(exporter);

                createShaped(RecipeCategory.MISC, CanadaBlocks.MAPLE_HANGING_SIGN)
                    .pattern("X X")
                    .pattern("YYY")
                    .pattern("YYY")
                    .input('X', Items.CHAIN)
                    .input('Y', CanadaTags.ItemTags.MAPLE_LOGS)
                    .criterion(hasTag(CanadaTags.ItemTags.MAPLE_LOGS), conditionsFromTag(CanadaTags.ItemTags.MAPLE_LOGS))
                    .criterion(hasItem(Items.CHAIN), conditionsFromItem(Items.CHAIN))
                    .offerTo(exporter);

                offerBoatRecipe(CanadaItems.MAPLE_BOAT, CanadaBlocks.MAPLE_PLANKS);
                offerChestBoatRecipe(CanadaItems.MAPLE_CHEST_BOAT, CanadaBlocks.MAPLE_PLANKS);

                createShaped(RecipeCategory.BUILDING_BLOCKS, CanadaBlocks.MAPLE_WOOD)
                    .pattern("XX")
                    .pattern("XX")
                    .input('X', CanadaBlocks.MAPLE_LOG)
                    .criterion(hasItem(CanadaBlocks.MAPLE_LOG), conditionsFromItem(CanadaBlocks.MAPLE_LOG))
                    .offerTo(exporter);
                
                createShaped(RecipeCategory.BUILDING_BLOCKS, CanadaBlocks.STRIPPED_MAPLE_WOOD)
                    .pattern("XX")
                    .pattern("XX")
                    .input('X', CanadaBlocks.STRIPPED_MAPLE_LOG)
                    .criterion(hasItem(CanadaBlocks.STRIPPED_MAPLE_LOG), conditionsFromItem(CanadaBlocks.STRIPPED_MAPLE_LOG))
                    .offerTo(exporter);

                createShapeless(RecipeCategory.FOOD, CanadaItems.FLOUR)
                    .input(Items.WHEAT)
                    .criterion(hasItem(Items.WHEAT), conditionsFromItem(Items.WHEAT))
                    .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, Items.BONE_MEAL)
                    .input(CanadaItems.ANTLERS)
                    .criterion(hasItem(CanadaItems.ANTLERS), conditionsFromItem(CanadaItems.ANTLERS))
                    .offerTo(exporter);
                
                createCookingPotRecipe(registryLookup, Items.RABBIT_STEW, 1)
                    .input(Items.RABBIT, this)
                    .input(ConventionalItemTags.VEGETABLE_FOODS, this)
                    .requiresBowl()
                    .offerTo(exporter);

                createCookingPotRecipe(registryLookup, Items.MUSHROOM_STEW, 1)
                    .input(ConventionalItemTags.MUSHROOMS, this)
                    .requiresBowl()
                    .offerTo(exporter);

                createCookingPotRecipe(registryLookup, Items.BEETROOT_SOUP, 1)
                    .input(Items.BEETROOT, this)
                    .input(Items.BEETROOT, this)
                    .input(Items.BEETROOT, this)
                    .input(Items.BEETROOT, this)
                    .requiresBowl()
                    .offerTo(exporter);

                createCookingPotRecipe(registryLookup, Items.CAKE, 1)
                    .input(Items.MILK_BUCKET, this)
                    .input(Items.EGG, this)
                    .input(Items.SUGAR, this)
                    .input(CanadaItems.FLOUR, this)
                    .offerTo(exporter);

                createCookingPotRecipe(registryLookup, Items.PUMPKIN_PIE, 3)
                    .input(Blocks.PUMPKIN, this)
                    .input(ItemTags.EGGS, this)
                    .input(Items.SUGAR, this)
                    .offerTo(exporter);

                createCookingPotRecipe(registryLookup, Items.BREAD, 3)
                    .input(CanadaItems.FLOUR, this)
                    .input(CanadaItems.FLOUR, this)
                    .input(CanadaItems.FLOUR, this)
                    .input(Items.WATER_BUCKET, this)
                    .offerTo(exporter);   

                createCookingPotRecipe(registryLookup, Items.SLIME_BALL, 3)
                    .input(CanadaItems.FLOUR, this)
                    .input(CanadaItems.SAP, this)
                    .offerTo(exporter);   
                    
                createCookingPotRecipe(registryLookup, CanadaItems.GRAVY, 1)
                    .input(CanadaItems.FLOUR, this)
                    .input(Items.CHICKEN, this)
                    .input(CanadaTags.ItemTags.RED_MEAT, this)
                    .input(Items.WATER_BUCKET, this)
                    .requiresBottle()
                    .offerTo(exporter);   
                
                createCookingPotRecipe(registryLookup, CanadaItems.CHEESE_CURD, 3)
                    .input(Items.MILK_BUCKET, this)
                    .input(Items.FERMENTED_SPIDER_EYE, this)
                    .offerTo(exporter);   

                createCookingPotRecipe(registryLookup, CanadaItems.RUBBER, 4)
                    .input(CanadaItems.SAP, this)
                    .input(CanadaItems.SAP, this)
                    .input(CanadaItems.SAP, this)
                    .input(Items.FERMENTED_SPIDER_EYE, this)
                    .offerTo(exporter);   

                createCookingPotRecipe(registryLookup, CanadaItems.MAPLE_SYRUP_BOTTLE, 1)
                    .input(CanadaItems.MAPLE_SAP, this)
                    .requiresBottle()
                    .offerTo(exporter);

                createCookingPotRecipe(registryLookup, CanadaItems.POUTINE, 1)
                    .input(CanadaItems.GRAVY, this)
                    .input(Items.POTATO, this)
                    .input(CanadaItems.CHEESE_CURD, this)
                    .requiresBowl()
                    .offerTo(exporter);

                createShaped(RecipeCategory.FOOD, CanadaItems.THERMOS)
                    .pattern(" X ")
                    .pattern("Y Y")
                    .pattern("XXX")
                    .input('X', Items.IRON_INGOT)
                    .input('Y', CanadaItems.RUBBER)
                    .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                    .criterion(hasItem(CanadaItems.RUBBER), conditionsFromItem(CanadaItems.RUBBER))
                    .offerTo(exporter);

                createShaped(RecipeCategory.FOOD, CanadaBlocks.RUBBER_BLOCK)
                    .pattern("XX")
                    .pattern("XX")
                    .input('X', CanadaItems.RUBBER)
                    .criterion(hasItem(CanadaItems.RUBBER), conditionsFromItem(CanadaItems.RUBBER))
                    .offerTo(exporter);

                offerSmelting(List.of(CanadaItems.VENISON), RecipeCategory.FOOD, CanadaItems.COOKED_VENISON, 0.35f, 200, CanadaItems.VENISON.getName().toString());
            }

            public static CookingPotRecipeJsonBuilder createCookingPotRecipe(WrapperLookup registryLookup, ItemConvertible output, int count) {
                return new CookingPotRecipeJsonBuilder(registryLookup.getOrThrow(RegistryKeys.ITEM), output, count);
            }
        };
    }

    private String hasTag(TagKey<Item> tag) {
        return "has_" + tag.getName();
    }
}
