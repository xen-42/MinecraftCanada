package xen42.canadamod.sign;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import xen42.canadamod.CanadaBlocks;

public class MapleWallSignBlock extends WallSignBlock {
    public MapleWallSignBlock(Settings settings) {
        super(CanadaBlocks.MAPLE_WOOD_TYPE, settings);
    }
    
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MapleHangingSignBlockEntity(pos, state);
    }
}
