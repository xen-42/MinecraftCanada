package xen42.canadamod.block.skull;

import java.util.Map;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class MooseWallSkullBlock extends WallSkullBlock {
    public static final MapCodec<MooseWallSkullBlock> CODEC = createCodec(MooseWallSkullBlock::new);
    private static final Map<Direction, VoxelShape> SHAPES = VoxelShapes.createHorizontalFacingShapeMap(Block.createCuboidZShape(8.0, 0.0, 16.0));

    @Override
    public MapCodec<MooseWallSkullBlock> getCodec() {
        return CODEC;
    }

    public MooseWallSkullBlock(Settings settings) {
        super(CanadaSkullType.MOOSE, settings);
    }
    
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MooseSkullBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (VoxelShape)SHAPES.get(state.get(FACING));
    }
}
