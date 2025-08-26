package xen42.canadamod.entity;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.PolarBearEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.CanadaModClient;
import xen42.canadamod.entities.GrizzlyEntity;

public class GrizzlyEntityRenderer extends MobEntityRenderer<GrizzlyEntity, PolarBearEntityRenderState, GrizzlyEntityModel<GrizzlyEntity>> {

    public GrizzlyEntityRenderer(Context context) {
        super(context, new GrizzlyEntityModel(context.getPart(CanadaModClient.MODEL_GRIZZLY_LAYER)), 1f);
    }

    @Override
    public Identifier getTexture(PolarBearEntityRenderState state) {
        return Identifier.of(CanadaMod.MOD_ID, "textures/entity/grizzly/grizzly.png");
    }

    @Override
    public PolarBearEntityRenderState createRenderState() {
        return new PolarBearEntityRenderState();
    }

    @Override
    protected void scale(PolarBearEntityRenderState state, MatrixStack matrices) {
        if (state.baby) {
            matrices.scale(0.5F, 0.5F, 0.5F); 
        }
        super.scale(state, matrices);
    }
    
}
