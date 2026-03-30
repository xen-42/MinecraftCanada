package xen42.canadamod.block;

import java.util.Map;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.enums.Attachment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import xen42.canadamod.CanadaMod;

public class CookingPotBlock extends BlockWithEntity {

    public CookingPotBlock(Settings settings) {
        super(settings);
        setDefaultState((this.stateManager.getDefaultState()).with(LIT, false));
    }

    public static final BooleanProperty LIT = Properties.LIT;
    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
    public static final MapCodec<CookingPotBlock> CODEC = createCodec(CookingPotBlock::new);
    
    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[] { FACING, LIT });
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(LIT))
            return;  
        
        var x = pos.getX() + 0.5f;
        var y = pos.getY();
        var z = pos.getZ() + 0.5f;
        if (random.nextDouble() < 0.1f) {
            world.playSoundClient(x, y, z, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
        }

        var direction = state.get(FACING);
        var axis = direction.getAxis();

        double g = 0.52D;
        double h = random.nextDouble() * 0.6D - 0.3D;
        double i = (axis == Direction.Axis.X) ? (direction.getOffsetX() * 0.52D) : h;
        double j = random.nextDouble() * 6.0D / 16.0D;
        double k = (axis == Direction.Axis.Z) ? (direction.getOffsetZ() * 0.52D) : h;

        world.addParticleClient(ParticleTypes.SMOKE, x + i, y + j, z + k, 0.0D, 0.0D, 0.0D);
        world.addParticleClient(ParticleTypes.FLAME, x + i, y + j, z + k, 0.0D, 0.0D, 0.0D);

        if (random.nextInt(2) == 0) {
            for (int ii = 0; ii < random.nextInt(1) + 2; ii++) {
                world.addParticleClient(ParticleTypes.SMOKE, x + random.nextFloat() / 2f, y + 1f, z + random.nextFloat() / 2f, 0, 0.005f, 0);
            }
        }

        if (random.nextInt(20) == 0) {
            world.addParticleClient(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y + 1f, z, 0, 0.025f, 0);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            openScreen(world, pos, player);
        }
        return ActionResult.SUCCESS;
    }
    
    public Text getTitle() {
        return Text.translatable(getTranslationKey());
    }

    public void openScreen(World world, BlockPos pos, PlayerEntity player) {
        var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CookingPotBlockEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory)blockEntity);
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CookingPotBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(world, type, CanadaMod.COOKING_POT_ENTITY);
    }

    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(World world, BlockEntityType<T> givenType, BlockEntityType<? extends CookingPotBlockEntity> expectedType) {
        if (world instanceof ServerWorld serverWorld) {
            return validateTicker(givenType, expectedType, (__, pos, state, blockEntity) -> CookingPotBlockEntity.tick(serverWorld, pos, state, blockEntity));
        }
        else {
            return null;
        }
    }

    public static final VoxelShape SHAPE = VoxelShapes.union(
		    // Furnace
		    Block.createCuboidShape(0, 0, 0, 16, 8, 16),

		    // Pot
		    Block.createCuboidShape(2, 9, 2, 14, 16, 14), // taper
		    Block.createCuboidShape(3, 8, 3, 13, 15, 13), // bottom and soup
		    
		    // Pot handles
		    Block.createCuboidShape(0, 14, 6, 2, 16, 10),  // west
		    Block.createCuboidShape(14, 14, 6, 16, 16, 10) // east
		);
    public static final Map<Direction, VoxelShape> FACING_SHAPES = VoxelShapes.createHorizontalFacingShapeMap(SHAPE);

    
	private VoxelShape getShape(BlockState state) {
		Direction direction = state.get(FACING);

		return (VoxelShape)FACING_SHAPES.get(direction);
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return this.getShape(state);
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return this.getShape(state);
	}
}
