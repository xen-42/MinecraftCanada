package xen42.canadamod;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;

public class CanadaModBlockTagGenerator extends BlockTagProvider {
    public CanadaModBlockTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "CanadaModBlockTagGenerator";
    }
    
    @Override
    protected void configure(WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(BlockTags.LOGS)
            .add(CanadaBlocks.MAPLE_LOG, CanadaBlocks.STRIPPED_MAPLE_LOG, CanadaBlocks.MAPLE_WOOD, CanadaBlocks.STRIPPED_MAPLE_WOOD);
        
        this.getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
            .add(CanadaBlocks.MAPLE_LOG, CanadaBlocks.STRIPPED_MAPLE_LOG, CanadaBlocks.MAPLE_WOOD, CanadaBlocks.STRIPPED_MAPLE_WOOD);

        this.getOrCreateTagBuilder(CanadaTags.BlockTags.MAPLE_LOGS)
            .add(CanadaBlocks.MAPLE_LOG, CanadaBlocks.STRIPPED_MAPLE_LOG, CanadaBlocks.MAPLE_WOOD, CanadaBlocks.STRIPPED_MAPLE_WOOD);
        
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
            .add(CanadaBlocks.MAPLE_STAIRS);

        this.getOrCreateTagBuilder(BlockTags.FENCES)
            .add(CanadaBlocks.MAPLE_FENCE);

        this.getOrCreateTagBuilder(BlockTags.FENCE_GATES)
            .add(CanadaBlocks.MAPLE_FENCE_GATE);

        this.getOrCreateTagBuilder(BlockTags.ALL_SIGNS)
            .add(CanadaBlocks.MAPLE_SIGN)
            .add(CanadaBlocks.MAPLE_WALL_SIGN);
        
        this.getOrCreateTagBuilder(BlockTags.ALL_HANGING_SIGNS)
            .add(CanadaBlocks.MAPLE_HANGING_SIGN)
            .add(CanadaBlocks.MAPLE_WALL_HANGING_SIGN);
        
        this.getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
            .add(CanadaBlocks.MAPLE_PLANKS)
            .add(CanadaBlocks.MAPLE_HANGING_SIGN)
            .add(CanadaBlocks.MAPLE_WALL_HANGING_SIGN)
            .add(CanadaBlocks.MAPLE_SIGN)
            .add(CanadaBlocks.MAPLE_WALL_SIGN)
            .add(CanadaBlocks.MAPLE_TRAPDOOR)
            .add(CanadaBlocks.MAPLE_FENCE);
        
        this.getOrCreateTagBuilder(BlockTags.LEAVES)
            .add(CanadaBlocks.MAPLE_LEAVES);

        this.getOrCreateTagBuilder(ConventionalBlockTags.FENCES)
            .add(CanadaBlocks.MAPLE_FENCE);
        this.getOrCreateTagBuilder(ConventionalBlockTags.WOODEN_FENCES)
            .add(CanadaBlocks.MAPLE_FENCE);
        this.getOrCreateTagBuilder(ConventionalBlockTags.FENCE_GATES)
            .add(CanadaBlocks.MAPLE_FENCE_GATE);
        this.getOrCreateTagBuilder(ConventionalBlockTags.WOODEN_FENCE_GATES)
            .add(CanadaBlocks.MAPLE_FENCE);
        this.getOrCreateTagBuilder(ConventionalBlockTags.STRIPPED_LOGS)
            .add(CanadaBlocks.STRIPPED_MAPLE_LOG);
        this.getOrCreateTagBuilder(ConventionalBlockTags.STRIPPED_WOODS)
            .add(CanadaBlocks.STRIPPED_MAPLE_WOOD);

    }
}