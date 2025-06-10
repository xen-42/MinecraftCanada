package xen42.canadamod.block;

import javax.swing.text.html.BlockView;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;

public class MooseSkullBlock extends SkullBlock {
    public MooseSkullBlock(Settings settings) {
        super(null, settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MooseSkullBlockEntity(pos, state);
    }
}
