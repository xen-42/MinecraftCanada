package xen42.canadamod.entity;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.ChickenEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.CanadaModClient;
import xen42.canadamod.entities.DuckEntity;
import xen42.canadamod.entities.GooseEntity;

public class DuckEntityRenderer extends MobEntityRenderer<DuckEntity, ChickenEntityRenderState, DuckEntityModel<DuckEntity>> {

    public DuckEntityRenderer(Context context) {
        super(context, new DuckEntityModel(context.getPart(CanadaModClient.MODEL_DUCK_LAYER)), 0.3f);
    }

    @Override
    public Identifier getTexture(ChickenEntityRenderState state) {
        return Identifier.of(CanadaMod.MOD_ID, "textures/entity/duck/duck.png");
    }

    @Override
    public ChickenEntityRenderState createRenderState() {
        return new ChickenEntityRenderState();
    }

    @Override
    protected void scale(ChickenEntityRenderState state, MatrixStack matrices) {
        if (state.baby) {
            matrices.scale(0.5F, 0.5F, 0.5F); 
        }
        super.scale(state, matrices);
    }
    
    public void updateRenderState(DuckEntity chickenEntity, ChickenEntityRenderState chickenEntityRenderState, float f) {
		super.updateRenderState(chickenEntity, chickenEntityRenderState, f);
		chickenEntityRenderState.flapProgress = MathHelper.lerp(f, chickenEntity.lastFlapProgress, chickenEntity.flapProgress);
		chickenEntityRenderState.maxWingDeviation = MathHelper.lerp(f, chickenEntity.lastMaxWingDeviation, chickenEntity.maxWingDeviation);
	}
}
