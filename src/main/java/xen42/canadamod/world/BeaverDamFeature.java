package xen42.canadamod.world;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class BeaverDamFeature extends Feature<DefaultFeatureConfig> {

    public BeaverDamFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        var origin = context.getOrigin();
        var world = context.getWorld();

        for (var i = 0; i < 10; i++) {
            var pos = origin.add(0, i, 0);
            world.setBlockState(pos, Blocks.OAK_WOOD.getDefaultState(), Block.NOTIFY_ALL);
        }
        return true;
    }
    
}
