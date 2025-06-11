package xen42.canadamod.entities;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import xen42.canadamod.CanadaMod;

public class BeaverChopTreeGoal extends Goal {
    private final BeaverEntity beaver;

    @Nullable
    private BlockPos locatedTrunk;

    @Nullable
    private Path pathToTrunk;

    public BeaverChopTreeGoal(BeaverEntity beaver) {
        this.beaver = beaver;
    }

    public boolean canStart() {
        CanadaMod.LOGGER.info("Checking if we can start chopping");
        if (beaver.isBaby()) {
            return false;
        }

        if (!(beaver.getWorld() instanceof ServerWorld serverWorld) || !serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                                            CanadaMod.LOGGER.info("No server world or mob griefing");

            return false;
        }

        if (this.beaver.getRandom().nextInt(toGoalTicks(20)) != 0) {
                                            CanadaMod.LOGGER.info("No 20");

            return false;
        }

        locatedTrunk = findChoppableTree(5);
        if (locatedTrunk == null) {
                    CanadaMod.LOGGER.info("No tree");

            return false;
        }

        pathToTrunk = beaver.getNavigation().findPathTo(locatedTrunk.getX(), locatedTrunk.getY(), locatedTrunk.getZ(), 0);
        if (pathToTrunk == null) {
                                CanadaMod.LOGGER.info("No path");

            return false;
        }

        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (locatedTrunk == null) return false;

        return !this.beaver.getNavigation().isIdle() || this.beaver.getWorld().getBlockState(locatedTrunk).isAir();
    }

    @Override
    public void start() {
        this.beaver.getNavigation().startMovingAlong(this.pathToTrunk, 1f);
        CanadaMod.LOGGER.info("BEAVER GOING TO CHOP TREE");
    }

    @Override
    public void stop() {
        this.locatedTrunk = null;
        this.pathToTrunk = null;
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

        // If its a log with dirt under it, it may be the start of a tree!
        // From this we move up to make sure it has leaves on the top, else we might be breaking somebody's house
        var dirt = world.getBlockState(pos.down());
        if (trunk.isIn(BlockTags.LOGS) && (dirt.isIn(BlockTags.DIRT) || dirt.isOf(Blocks.GRASS_BLOCK) || dirt.isOf(Blocks.PODZOL))) {
            CanadaMod.LOGGER.info("Found trunk");
            // Move up to find leaves
            for (int i = 3; i < 8; i++) {
                var topBlockPos = pos.add(0, i, 0);
                var topBlock = world.getBlockState(topBlockPos);
                if (isNaturalLeaf(topBlock) && noLogsNextToBlock(topBlockPos)) {
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

    private boolean noLogsNextToBlock(BlockPos pos) {
        var world = this.beaver.getWorld();
        for (int ddx = -1; ddx <= 1; ddx++) {
            for (int ddz = -1; ddz <= 1; ddz++) {
                var block = world.getBlockState(pos.add(ddx, 0, ddz));
                if (block.isIn(BlockTags.LOGS)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void tick() {
        if (this.locatedTrunk.toCenterPos().distanceTo(this.beaver.getPos()) < 1.5f) {
            // Reached the tree, chop it
            // Todo: Add animation and time delay
            var block = locatedTrunk;
            while (this.beaver.getWorld().getBlockState(block).isIn(BlockTags.LOGS)) {
                this.beaver.getWorld().breakBlock(block, true);
                block = block.up();
            }
            locatedTrunk = null;
            pathToTrunk = null;
        }
    }
}
 
