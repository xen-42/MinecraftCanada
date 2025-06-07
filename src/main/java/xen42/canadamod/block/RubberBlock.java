package xen42.canadamod.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SlimeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RubberBlock extends SlimeBlock {

    public RubberBlock(Settings settings) {
        super(settings);
    }
    
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        // Remove the slowing effect that slime blocks have by default while keeping bounciness
    }
}
