package xen42.canadamod.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.util.math.Vec3d;
import xen42.canadamod.CanadaBlocks;
import xen42.canadamod.block.MooseSkullBlock;
import xen42.canadamod.block.MooseSkullBlockEntity;

public class MooseSkullBlockEntityRenderer implements BlockEntityRenderer<MooseSkullBlockEntity> {
    public MooseSkullBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(MooseSkullBlockEntity entity, float tickProgress, MatrixStack matrices,
            VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {

        if (entity.getCachedState().isOf(CanadaBlocks.MOOSE_WALL_HEAD)) {
            return;
        }

        var rot = RotationPropertyHelper.toDegrees((16 - entity.getCachedState().get(MooseSkullBlock.ROTATION)) % 16);

        matrices.translate(0.5f, 0.5f, 0.5f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float)Math.toRadians(rot)));
        matrices.translate(-0.5f, -0.5f, -0.5f);

        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(CanadaBlocks.MOOSE_HEAD.getDefaultState().with(MooseSkullBlock.WEIRD_HACK, false),
            matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);
    }
}