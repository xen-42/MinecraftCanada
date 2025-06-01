package xen42.canadamod;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
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
                
                createCookingPotRecipe(registryLookup, Items.RABBIT_STEW, 1)
                    .input(Items.RABBIT, this)
                    // Todo: change to tag for a vegetable
                    .input(Items.CARROT, this)
                    .input(Items.POTATO, this)
                    .requiresBowl()
                    .offerTo(exporter);
                
                createShapeless(RecipeCategory.FOOD, CanadaItems.FLOUR)
                    .input(Items.WHEAT)
                    .criterion(hasItem(Items.WHEAT), conditionsFromItem(Items.WHEAT))
                    .offerTo(exporter);

                createCookingPotRecipe(registryLookup, Items.CAKE, 1)
                    .input(Items.MILK_BUCKET, this)
                    .input(Items.EGG, this)
                    .input(Items.SUGAR, this)
                    .input(CanadaItems.FLOUR, this)
                    .offerTo(exporter);

                createCookingPotRecipe(registryLookup, Items.PUMPKIN_PIE, 3)
                    .input(Blocks.PUMPKIN, this)
                    .input(Items.EGG, this)
                    .input(Items.SUGAR, this)
                    .offerTo(exporter);
                
                createCookingPotRecipe(registryLookup, Items.BEETROOT_SOUP, 1)
                    .input(Items.BEETROOT, this)
                    .input(Items.BEETROOT, this)
                    .requiresBowl()
                    .offerTo(exporter);

                createCookingPotRecipe(registryLookup, Items.SLIME_BALL, 3)
                    .input(CanadaItems.FLOUR, this)
                    .input(Items.WATER_BUCKET, this)
                    .input(Items.LIME_DYE, this)
                    .offerTo(exporter);                    
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
