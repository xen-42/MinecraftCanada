package xen42.canadamod;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class TreeTapBlock extends Block {
    public static int MAX_SAP = 4;
    public static IntProperty SAP_LEVEL = IntProperty.of("sap_level", 0, MAX_SAP);
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
        if(player.getMainHandStack().isOf(Items.GLASS_BOTTLE) && state.get(SAP_LEVEL) > 0) {
            var stack = player.getMainHandStack();
            var hand = Hand.MAIN_HAND;
            var item = stack.getItem();

            var pickUpItem = Items.HONEY_BOTTLE;

            world.setBlockState(pos, state.with(SAP_LEVEL, state.get(SAP_LEVEL) - 1));
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

            if (stack.isEmpty()) {
               player.setStackInHand(hand, new ItemStack(pickUpItem));
            } else if (!player.getInventory().insertStack(new ItemStack(pickUpItem))) {
               player.dropItem(new ItemStack(pickUpItem), false);
            }

            world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);

            if (!world.isClient()) {
                player.incrementStat(Stats.USED.getOrCreateStat(item));
            }

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

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(SAP_LEVEL);
    }
}
