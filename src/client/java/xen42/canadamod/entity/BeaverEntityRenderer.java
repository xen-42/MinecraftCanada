package xen42.canadamod.entity;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.CanadaModClient;
import xen42.canadamod.entities.BeaverEntity;

public class BeaverEntityRenderer extends MobEntityRenderer<BeaverEntity, BeaverEntityRenderState, BeaverEntityModel> {

    public BeaverEntityRenderer(Context context) {
        super(context, new BeaverEntityModel(context.getPart(CanadaModClient.MODEL_BEAVER_LAYER)), 0.4f);
    }

    @Override
    public Identifier getTexture(BeaverEntityRenderState state) {
        return Identifier.of(CanadaMod.MOD_ID, "textures/entity/beaver/beaver.png");
    }

    @Override
    public BeaverEntityRenderState createRenderState() {
        return new BeaverEntityRenderState();
    }

    @Override
    protected void scale(BeaverEntityRenderState state, MatrixStack matrices) {
        if (state.baby) {
            matrices.scale(0.5F, 0.5F, 0.5F); 
        }
        super.scale(state, matrices);
    }
}
