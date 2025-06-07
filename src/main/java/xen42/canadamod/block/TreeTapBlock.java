package xen42.canadamod.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;
import xen42.canadamod.CanadaBlocks;
import xen42.canadamod.CanadaItems;

public class TreeTapBlock extends Block {
    public static int MAX_SAP = 4;
    public static IntProperty SAP_LEVEL = IntProperty.of("sap_level", 0, MAX_SAP);
    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING; 
    public static final MapCodec<TreeTapBlock> CODEC = createCodec(TreeTapBlock::new);

    public final VoxelShape northShape;
    public final VoxelShape southShape;
    public final VoxelShape eastShape;
    public final VoxelShape westShape;

    public TreeTapBlock(Settings settings) {
        super(settings);
        setDefaultState((this.stateManager.getDefaultState()).with(SAP_LEVEL, 0));

        var width = 12;
        this.southShape = Block.createCuboidShape(0, 0, 0, 16f, 16f, width);
        this.northShape = Block.createCuboidShape(0, 0, 16 - width, 16f, 16f, 16f);
        this.eastShape = Block.createCuboidShape(0, 0, 0, width, 16f, 16f);
        this.westShape = Block.createCuboidShape(16 - width, 0, 0, 16f, 16f, 16f);
    }

    public MapCodec<? extends TreeTapBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case NORTH:
                return northShape;
            case SOUTH:
                return southShape;
            case EAST:
                return eastShape;
            case WEST:
                return westShape;
            default:
                break; 
        }
        return westShape;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        var direction = state.get(FACING);
        if (direction == Direction.UP || direction == Direction.DOWN) {
            return false;
        }
        else {
            var blockPos = pos.offset(direction.getOpposite());
            return world.getBlockState(blockPos).isSideSolidFullSquare((BlockView)world, blockPos, direction);
        }
    }

    public Item getReturnItem(BlockState state, World world, BlockPos pos) {
        var tappedBlock = world.getBlockState(pos.add(state.get(FACING).getOpposite().getVector()));

        if (tappedBlock.isOf(Blocks.PALE_OAK_LOG)) {
            return Items.RESIN_CLUMP;
        }
        else if (tappedBlock.isOf(CanadaBlocks.MAPLE_LOG)) {
            return CanadaItems.MAPLE_SAP;
        }
        else if (tappedBlock.isIn(BlockTags.LOGS)) {
            return CanadaItems.SAP;
        }
        else {
            return null;
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        var pair = getReturnItem(state, world, pos);

        if (pair != null && !isFull(state)) {
            world.setBlockState(pos, state.with(SAP_LEVEL, state.get(SAP_LEVEL) + 1));
            world.playSoundClient(SoundEvents.BLOCK_POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON, SoundCategory.BLOCKS, 1f, 1f);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
        }
    }

    public boolean isFull(BlockState state) {
        return state.get(SAP_LEVEL) == MAX_SAP;
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return !isFull(state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        var returnItem = getReturnItem(state, world, pos);

        if (returnItem == null) {
            return ActionResult.FAIL;
        }

        if(state.get(SAP_LEVEL) > 0) {
            world.setBlockState(pos, state.with(SAP_LEVEL, state.get(SAP_LEVEL) - 1));
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

            player.giveOrDropStack(new ItemStack(returnItem));

            return ActionResult.CONSUME;
        }
        else {
            return ActionResult.PASS;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[] { SAP_LEVEL, FACING });
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(SAP_LEVEL);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (direction == (state.get(FACING)).getOpposite() && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState(); 
        }
        else {
            return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        var dir = ctx.getSide();
        // Should never happen but want to avoid a crash
        if (dir == Direction.UP || dir == Direction.DOWN) {
            dir = Direction.NORTH;
        }
        return getDefaultState().with(SAP_LEVEL, 0).with(FACING, dir);
    }
}
