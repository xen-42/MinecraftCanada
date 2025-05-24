package xen42.canadamod;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

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

                createShaped(RecipeCategory.MISC, CanadaBlocks.MAPLE_HANGING_SIGN)
                    .pattern("X X")
                    .pattern("YYY")
                    .pattern("YYY")
                    .input('X', Items.CHAIN)
                    .input('Y', CanadaTags.ItemTags.MAPLE_LOGS)
                    .criterion(hasTag(CanadaTags.ItemTags.MAPLE_LOGS), conditionsFromTag(CanadaTags.ItemTags.MAPLE_LOGS))
                    .criterion(hasItem(Items.CHAIN), conditionsFromItem(Items.CHAIN))
                    .offerTo(exporter);
                
                offerSmelting(List.of(CanadaBlocks.MAPLE_LOG, CanadaBlocks.STRIPPED_MAPLE_LOG, CanadaBlocks.MAPLE_WOOD, CanadaBlocks.STRIPPED_MAPLE_WOOD),
                    RecipeCategory.MISC, 
                    Items.CHARCOAL, 0.15f, 200, Items.CHARCOAL.getName().toString());
            }
        };
    }

    private String hasTag(TagKey<Item> tag) {
        return "has_" + tag.getName();
    }
}
