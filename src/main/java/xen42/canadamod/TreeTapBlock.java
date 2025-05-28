package xen42.canadamod;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class TreeTapBlock extends Block {
    public static IntProperty SAP_LEVEL = IntProperty.of("sap_level", 0, 8);
    public static final MapCodec<TreeTapBlock> CODEC = createCodec(TreeTapBlock::new);

    public TreeTapBlock(Settings settings) {
        super(settings);
        setDefaultState((this.stateManager.getDefaultState()).with(SAP_LEVEL, 0));
    }

    public MapCodec<? extends TreeTapBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!isFull(state)) {
            world.setBlockState(pos, state.with(SAP_LEVEL, state.get(SAP_LEVEL) + 1));
        }
    }

    public boolean isFull(BlockState state) {
        return state.get(SAP_LEVEL) == 8;
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return !isFull(state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(player.getMainHandStack().isOf(Items.GLASS_BOTTLE) && state.get(SAP_LEVEL) > 0) {
            player.getMainHandStack().decrement(1);
            player.giveOrDropStack(new ItemStack(Items.HONEY_BOTTLE));
            world.setBlockState(pos, state.with(SAP_LEVEL, state.get(SAP_LEVEL) - 1));
            return ActionResult.CONSUME;
        }
        else {
            return ActionResult.PASS;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[] { SAP_LEVEL });
    }
}
