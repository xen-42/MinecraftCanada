package xen42.canadamod.entities;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import xen42.canadamod.CanadaBlocks;
import xen42.canadamod.CanadaMod;

public class BeaverChopTreeGoal extends Goal {
    public static Map<LeavesBlock, SaplingBlock> saplingMap = new HashMap();

    private final BeaverEntity beaver;

    @Nullable
    private Path pathToTrunk;

    @Nullable
    private SaplingBlock sapling;

    private boolean isChoppingTree;

    private final int MAX_BREAKING_TICKS = 40;
    private int choppingTicks = 0;

    public BeaverChopTreeGoal(BeaverEntity beaver) {
        this.beaver = beaver;
    }

    public boolean canStart() {
        if (!beaver.canChopTree()) {
            return false;
        }

        if (!(beaver.getWorld() instanceof ServerWorld serverWorld) || !serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            return false;
        }

        if (!this.beaver.isFrenzied() && this.beaver.getRandom().nextInt(toGoalTicks(20)) != 0) {
            return false;
        }

        this.beaver.setChoppingTree(findChoppableTree(5));
        if (this.beaver.getChoppingTreePos() == null) {
            return false;
        }

        pathToTrunk = beaver.getNavigation().findPathTo(this.beaver.getChoppingTreePos().getX(), this.beaver.getChoppingTreePos().getY(),
            this.beaver.getChoppingTreePos().getZ(), 0);
        if (pathToTrunk == null) {
            return false;
        }

        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (isChoppingTree) return true;

        if (this.beaver.getChoppingTreePos() == null) return false;

        return !this.beaver.getNavigation().isIdle() || this.beaver.getWorld().getBlockState(this.beaver.getChoppingTreePos()).isAir();
    }

    @Override
    public void start() {
        this.beaver.getNavigation().startMovingAlong(this.pathToTrunk, 1f);
    }

    @Override
    public void stop() {
        // In case somebody else broke it
        this.beaver.stopChopping();
        this.sapling = null;
    }

    private BlockPos findChoppableTree(int radius) {
        for (var dx = -radius; dx <= radius; dx++) {
            for (var dz = -radius; dz <= radius; dz++) {
                for (var dy = -1; dy <= 1; dy++) {
                    var trunkPos = beaver.getBlockPos().add(dx, dy, dz);

                    if (isTreeTrunk(trunkPos)) {
                        return trunkPos;
                    }
                }
            }
        }
        return null;
    }

    private boolean isTreeTrunk(BlockPos pos) {
        var world = beaver.getWorld();
        var trunk = world.getBlockState(pos);
        this.sapling = null;

        // If its a log with dirt under it, it may be the start of a tree!
        // From this we move up to make sure it has leaves on the top, else we might be breaking somebody's house
        var dirt = world.getBlockState(pos.down());
        if (trunk.isIn(BlockTags.LOGS) && (dirt.isIn(BlockTags.DIRT) || dirt.isOf(Blocks.GRASS_BLOCK) || dirt.isOf(Blocks.PODZOL))) {
            // Move up to find leaves
            for (int i = 0; i < 8; i++) {
                var topBlockPos = pos.add(0, i, 0);
                var topBlock = world.getBlockState(topBlockPos);
                // TODO: also check for tree taps near the base
                if (!noLogsNextToBlock(topBlockPos)) {
                    return false;
                }
                if (isNaturalLeaf(topBlock)) {
                    this.sapling = getSaplingFromLeaf(topBlockPos);
                    return true;
                }
                // Make sure it is a continuous tree
                else if (!topBlock.isIn(BlockTags.LOGS)) {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean isNaturalLeaf(BlockState state) {
        return state.isIn(BlockTags.LEAVES) && state.contains(Properties.PERSISTENT) && !state.get(Properties.PERSISTENT);
    }

    @Nullable
    private SaplingBlock getSaplingFromLeaf(BlockPos pos) {
        var blockState = beaver.getWorld().getBlockState(pos);
        var leafBlock = (LeavesBlock)blockState.getBlock();

        if (leafBlock == null) {
            return null;
        }

        if (saplingMap.containsKey(leafBlock)) {
            return saplingMap.get(leafBlock);
        }
        else {
            // This is straight goofy ok we need to simulate like 40 leaf drops to PROBABLY get a sapling but even then
            for (int i = 0; i < 60; i++) {
                for (var item : Block.getDroppedStacks(blockState, (ServerWorld)beaver.getWorld(), pos, null)) {
                    if (item.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof SaplingBlock sapling) {
                        saplingMap.put(leafBlock, sapling);
                        return sapling;
                    }
                }
            }
        }

        return null;
    }

    private boolean noLogsNextToBlock(BlockPos pos) {
        var world = this.beaver.getWorld();
        for (int ddx = -1; ddx <= 1; ddx++) {
            for (int ddz = -1; ddz <= 1; ddz++) {
                if (ddx == 0 && ddz == 0) {
                    continue;
                }
                var block = world.getBlockState(pos.add(ddx, 0, ddz));
                if (block.isIn(BlockTags.LOGS) || block.isOf(CanadaBlocks.TREE_TAP)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void tick() {
        if (isChoppingTree) {
            // Don't let them move away
            this.beaver.getNavigation().stop();
            this.beaver.getLookControl().lookAt(this.beaver.getChoppingTreePos().toCenterPos());

            if (!this.beaver.getWorld().getBlockState(this.beaver.getChoppingTreePos()).isIn(BlockTags.LOGS) || 
                this.beaver.getChoppingTreePos().toCenterPos().distanceTo(this.beaver.getPos()) > 2f ||
                this.beaver.getRecentDamageSource() != null) {
                // If something else broke it give up, or if we moved away or got hurts
                this.beaver.stopChopping();
                isChoppingTree = false;
            }
            else {
                // Particles and block breaking effects
                var stage = 10 * (MAX_BREAKING_TICKS - choppingTicks) / MAX_BREAKING_TICKS;

                this.beaver.setChoppingProgress(stage);

                choppingTicks--;

                if (choppingTicks <= 0) {
                    breakTree();
                }

                // Make sure the beaver stays here and faces the tree
                // Null check because maybe just broke it
                if (this.beaver.getChoppingTreePos() != null) {

                    this.beaver.getNavigation().stop();
                    this.beaver.getLookControl().lookAt(this.beaver.getChoppingTreePos().toCenterPos());
                }
            }
        }
        else if (this.beaver.getChoppingTreePos().toCenterPos().distanceTo(this.beaver.getPos()) < 1.6f) {
            // Reached the tree, chop it
            this.isChoppingTree = true;
            choppingTicks = MAX_BREAKING_TICKS;
        }
        if (beaver.isDead()) {
            stop();
        }
    }

    private void breakTree() {
        var blockPos = this.beaver.getChoppingTreePos();
        while (this.beaver.getWorld().getBlockState(blockPos).isIn(BlockTags.LOGS)) {
            this.beaver.getWorld().breakBlock(blockPos, true);
            blockPos = blockPos.up();
        }
        if (sapling != null) {
            this.beaver.getWorld().setBlockState(this.beaver.getChoppingTreePos(), sapling.getDefaultState());
        }
        this.beaver.stopChopping();
        this.beaver.onChopTree();
        this.isChoppingTree = false;
    }
}
 
