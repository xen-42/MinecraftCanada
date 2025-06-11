package xen42.canadamod.entities;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import xen42.canadamod.CanadaMod;

public class BeaverChopTreeEffectPayload implements CustomPayload {
    public static final Identifier ID = Identifier.of(CanadaMod.MOD_ID, "beaver_chop_tree_effect");
    public static final CustomPayload.Id<BeaverChopTreeEffectPayload> PAYLOAD_ID = new CustomPayload.Id<>(ID);

    public int id;
    public int stage;
    public BlockPos pos;

    public static final PacketCodec<RegistryByteBuf, BeaverChopTreeEffectPayload> CODEC = PacketCodec.of(
        BeaverChopTreeEffectPayload::write, BeaverChopTreeEffectPayload::read
    );

    public BeaverChopTreeEffectPayload(int id, int stage, BlockPos pos) {
        this.id = id;
        this.stage = stage;
        this.pos = pos;
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }

    private void write(RegistryByteBuf buf) {
        buf.writeVarInt(id);
        buf.writeVarInt(stage);
        buf.writeBlockPos(pos);
    }

    private static BeaverChopTreeEffectPayload read(RegistryByteBuf buf) {
        var id = buf.readVarInt();
        var stage = buf.readVarInt();
        var pos = buf.readBlockPos();
        return new BeaverChopTreeEffectPayload(id, stage, pos);
    }
    
}
