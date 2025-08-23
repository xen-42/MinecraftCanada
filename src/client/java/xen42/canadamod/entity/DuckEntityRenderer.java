package xen42.canadamod.entity;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.ChickenEntityRenderState;
import net.minecraft.util.Identifier;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.CanadaModClient;
import xen42.canadamod.entities.DuckEntity;

public class DuckEntityRenderer extends MobEntityRenderer<DuckEntity, ChickenEntityRenderState, DuckEntityModel<DuckEntity>> {

    public DuckEntityRenderer(Context context) {
        super(context, new DuckEntityModel(context.getPart(CanadaModClient.MODEL_DUCK_LAYER)), 0.4f);
    }

    @Override
    public Identifier getTexture(ChickenEntityRenderState state) {
        return Identifier.of(CanadaMod.MOD_ID, "textures/entity/duck/duck.png");
    }

    @Override
    public ChickenEntityRenderState createRenderState() {
        return new ChickenEntityRenderState();
    }
    
}
