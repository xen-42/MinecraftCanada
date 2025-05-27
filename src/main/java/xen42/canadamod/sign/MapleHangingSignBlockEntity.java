package xen42.canadamod.sign;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.HangingSignBlockEntity;
import net.minecraft.util.math.BlockPos;
import xen42.canadamod.CanadaBlocks;

public class MapleHangingSignBlockEntity extends HangingSignBlockEntity {
    public MapleHangingSignBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
        this.type = CanadaBlocks.MAPLE_HANGING_SIGN_BLOCK_ENTITY;
    }
    
    @Override
    public boolean supports(BlockState state) {
        return CanadaBlocks.MAPLE_HANGING_SIGN_BLOCK_ENTITY.supports(state);
    }
}
