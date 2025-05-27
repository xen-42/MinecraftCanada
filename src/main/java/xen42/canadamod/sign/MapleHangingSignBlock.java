package xen42.canadamod.sign;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.block.HangingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import xen42.canadamod.CanadaBlocks;

public class MapleHangingSignBlock extends HangingSignBlock {
    public static final MapCodec<HangingSignBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(WoodType.CODEC.fieldOf("wood_type").forGetter((sign) -> sign.getWoodType()), createSettingsCodec())
        .apply(instance, (type, settings) -> new MapleHangingSignBlock(settings));
    });

    public MapCodec<HangingSignBlock> getCodec() {
        return CODEC;
    }

    public MapleHangingSignBlock(Settings settings) {
        super(CanadaBlocks.MAPLE_WOOD_TYPE, settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MapleHangingSignBlockEntity(pos, state);
    }
}
