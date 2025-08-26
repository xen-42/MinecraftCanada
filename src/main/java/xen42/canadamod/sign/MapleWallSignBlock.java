package xen42.canadamod.sign;

import java.util.Collections;
import java.util.List;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.util.math.BlockPos;
import xen42.canadamod.CanadaBlocks;
import xen42.canadamod.CanadaItems;

public class MapleWallSignBlock extends WallSignBlock {
    public static final MapCodec<WallSignBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(WoodType.CODEC.fieldOf("wood_type").forGetter((sign) -> CanadaBlocks.MAPLE_WOOD_TYPE), createSettingsCodec())
        .apply(instance, (type, settings) -> new MapleWallSignBlock(settings));
    });

    public MapCodec<WallSignBlock> getCodec() {
        return CODEC;
    }

    public MapleWallSignBlock(Settings settings) {
        super(CanadaBlocks.MAPLE_WOOD_TYPE, settings);
    }
    
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MapleSignEntity(pos, state);
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        // Loot table stuff is weird and doesn't work, have to do this
        return Collections.singletonList(new ItemStack(CanadaItems.MAPLE_SIGN_ITEM));
    }
}
