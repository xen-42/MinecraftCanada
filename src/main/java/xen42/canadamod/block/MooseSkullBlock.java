package xen42.canadamod.block;


import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class MooseSkullBlock extends BlockWithEntity {
    public static final MapCodec<MooseSkullBlock> CODEC = createCodec(MooseSkullBlock::new);

    private static final int MAX_ROTATIONS = RotationPropertyHelper.getMax() + 1;
    public static final IntProperty ROTATION = Properties.ROTATION;
    // So the weird hack is on by default and makes the block not render
    // This is because the block renders only with a BlockEntityRenderer or with a ArmorEntityRenderer for when its on your head
    // Since these render with the rendering of the block itself it has to be different in the two cases
    // Idk man
    public static final BooleanProperty WEIRD_HACK = BooleanProperty.of("weird_hack");

    public MooseSkullBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ROTATION, 0).with(WEIRD_HACK, true));
    }

    protected VoxelShape getCullingShape(BlockState state) {
        return VoxelShapes.empty();
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(ROTATION, RotationPropertyHelper.fromYaw(ctx.getPlayerYaw()));
    }

    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(ROTATION, rotation.rotate((Integer)state.get(ROTATION), MAX_ROTATIONS));
    }

    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(ROTATION, mirror.mirror((Integer)state.get(ROTATION), MAX_ROTATIONS));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MooseSkullBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createColumnShape(12.0, 0.0, 8.0);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.ROTATION, WEIRD_HACK);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return state.get(WEIRD_HACK) ? BlockRenderType.INVISIBLE : BlockRenderType.MODEL;
    }
}
