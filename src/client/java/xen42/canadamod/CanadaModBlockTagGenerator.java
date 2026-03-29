package xen42.canadamod;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

public class CanadaModBlockTagGenerator extends BlockTagProvider {
    public CanadaModBlockTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "Block Tags";
    }
    
    @Override
    protected void configure(WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(CanadaTags.BlockTags.MAPLE_LOGS)
            .add(CanadaBlocks.MAPLE_LOG, CanadaBlocks.STRIPPED_MAPLE_LOG, CanadaBlocks.MAPLE_WOOD, CanadaBlocks.STRIPPED_MAPLE_WOOD);
        
        // Extends to BlockTags.LOGS
        this.getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
            .addTag(CanadaTags.BlockTags.MAPLE_LOGS);
        
        this.getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS)
            .add(CanadaBlocks.MAPLE_BUTTON);
        
        this.getOrCreateTagBuilder(BlockTags.WOODEN_DOORS)
            .add(CanadaBlocks.MAPLE_DOOR);
        
        this.getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES)
            .add(CanadaBlocks.MAPLE_PRESSURE_PLATE);
        
        this.getOrCreateTagBuilder(BlockTags.WOODEN_SLABS)
            .add(CanadaBlocks.MAPLE_SLAB);
        
        this.getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS)
            .add(CanadaBlocks.MAPLE_STAIRS);
        
        this.getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS)
            .add(CanadaBlocks.MAPLE_TRAPDOOR);

        // Both extend to ALL_SIGNS
        this.getOrCreateTagBuilder(BlockTags.STANDING_SIGNS)
            .add(CanadaBlocks.MAPLE_SIGN);

        this.getOrCreateTagBuilder(BlockTags.WALL_SIGNS)
            .add(CanadaBlocks.MAPLE_WALL_SIGN);

        // Both extend to ALL_HANGING_SIGNS
        this.getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS) 
            .add(CanadaBlocks.MAPLE_HANGING_SIGN);

        this.getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS)
            .add(CanadaBlocks.MAPLE_WALL_HANGING_SIGN);
        
        this.getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
            .add(CanadaBlocks.MAPLE_PLANKS)
            .add(CanadaBlocks.MAPLE_HANGING_SIGN)
            .add(CanadaBlocks.MAPLE_WALL_HANGING_SIGN)
            .add(CanadaBlocks.MAPLE_SIGN)
            .add(CanadaBlocks.MAPLE_WALL_SIGN)
            .add(CanadaBlocks.MAPLE_TRAPDOOR)
            .add(CanadaBlocks.MAPLE_FENCE);
        
        this.getOrCreateTagBuilder(BlockTags.PLANKS)
            .add(CanadaBlocks.MAPLE_PLANKS);
        this.getOrCreateTagBuilder(BlockTags.LEAVES)
            .add(CanadaBlocks.MAPLE_LEAVES);

        this.getOrCreateTagBuilder(ConventionalBlockTags.FENCES)
            .add(CanadaBlocks.MAPLE_FENCE);
        this.getOrCreateTagBuilder(BlockTags.FENCES)
            .add(CanadaBlocks.MAPLE_FENCE);

        this.getOrCreateTagBuilder(ConventionalBlockTags.WOODEN_FENCES)
            .add(CanadaBlocks.MAPLE_FENCE);
        this.getOrCreateTagBuilder(BlockTags.WOODEN_FENCES)
            .add(CanadaBlocks.MAPLE_FENCE);

        this.getOrCreateTagBuilder(ConventionalBlockTags.FENCE_GATES)
            .add(CanadaBlocks.MAPLE_FENCE_GATE);
        this.getOrCreateTagBuilder(BlockTags.FENCE_GATES)
            .add(CanadaBlocks.MAPLE_FENCE_GATE);

        this.getOrCreateTagBuilder(ConventionalBlockTags.WOODEN_FENCE_GATES)
            .add(CanadaBlocks.MAPLE_FENCE);
            
        this.getOrCreateTagBuilder(ConventionalBlockTags.STRIPPED_LOGS)
            .add(CanadaBlocks.STRIPPED_MAPLE_LOG);
        this.getOrCreateTagBuilder(ConventionalBlockTags.STRIPPED_WOODS)
            .add(CanadaBlocks.STRIPPED_MAPLE_WOOD);

        this.getOrCreateTagBuilder(BlockTags.SAPLINGS).add(CanadaBlocks.MAPLE_SAPLING);
        this.getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(CanadaBlocks.POTTED_MAPLE_SAPLING);

        this.getOrCreateTagBuilder(ConventionalBlockTags.SKULLS).add(CanadaBlocks.MOOSE_HEAD);

        this.getOrCreateTagBuilder(ConventionalBlockTags.VILLAGER_JOB_SITES).add(CanadaBlocks.TREE_TAP);
    }
}