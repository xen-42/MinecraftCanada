package xen42.canadamod.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock.SkullType;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class MooseWallSkullBlock extends WallSkullBlock {

    public MooseWallSkullBlock(Settings settings) {
        super(null, settings);
    }
    
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MooseSkullBlockEntity(pos, state);
    }
}
