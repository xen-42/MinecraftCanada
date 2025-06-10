package xen42.canadamod.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import xen42.canadamod.CanadaMod;

public class MooseSkullBlockEntity extends BlockEntity {

    public MooseSkullBlockEntity(BlockPos pos, BlockState state) {
        super(CanadaMod.MOOSE_HEAD_ENTITY, pos, state);
    }

    @Override
    public boolean supports(BlockState state) {
        return CanadaMod.MOOSE_HEAD_ENTITY.supports(state);
    }    
}
