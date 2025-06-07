package xen42.canadamod.entity;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.CanadaModClient;
import xen42.canadamod.entities.MooseEntity;

public class MooseEntityRenderer extends MobEntityRenderer<MooseEntity, MooseEntityRenderState, MooseEntityModel> {

    public MooseEntityRenderer(Context context) {
        super(context, new MooseEntityModel(context.getPart(CanadaModClient.MODEL_MOOSE_LAYER)), 1.0f);
    }

    @Override
    public MooseEntityRenderState createRenderState() {
        return new MooseEntityRenderState();
    }

    @Override
    public Identifier getTexture(MooseEntityRenderState state) {
        return Identifier.of(CanadaMod.MOD_ID, "textures/entity/moose/moose.png");
    }

    @Override
    protected void scale(MooseEntityRenderState state, MatrixStack matrices) {
        if (state.baby) {
            matrices.scale(0.5F, 0.5F, 0.5F); 
        }
        super.scale(state, matrices);
    }

    @Override
    public void updateRenderState(MooseEntity mooseEntity, MooseEntityRenderState mooseEntityRenderState, float f) {
        super.updateRenderState(mooseEntity, mooseEntityRenderState, f);
        mooseEntityRenderState.saddleStack = mooseEntity.getEquippedStack(EquipmentSlot.SADDLE).copy();
        mooseEntityRenderState.leftAntlerMissing = mooseEntity.isLeftAntlerMissing();
        mooseEntityRenderState.rightAntlerMissing = mooseEntity.isRightAntlerMissing();
        
        mooseEntityRenderState.attackAnimationState.copyFrom(mooseEntity.attackAnimationState);
    }
}

