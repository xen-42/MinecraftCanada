package xen42.canadamod.sign;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.util.math.BlockPos;
import xen42.canadamod.CanadaBlocks;

public class MapleSignEntity extends SignBlockEntity {
    public MapleSignEntity(BlockPos blockPos, BlockState blockState) {
        super(CanadaBlocks.MAPLE_SIGN_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    public boolean supports(BlockState state) {
        return CanadaBlocks.MAPLE_SIGN_BLOCK_ENTITY.supports(state);
    }
}
