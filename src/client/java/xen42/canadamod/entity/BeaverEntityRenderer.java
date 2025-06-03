package xen42.canadamod.entity;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.CanadaModClient;
import xen42.canadamod.entities.BeaverEntity;

public class BeaverEntityRenderer extends MobEntityRenderer<BeaverEntity, BeaverEntityRenderState, BeaverEntityModel> {

    public BeaverEntityRenderer(Context context) {
        super(context, new BeaverEntityModel(context.getPart(CanadaModClient.MODEL_BEAVER_LAYER)), 0.2f);
    }

    @Override
    public Identifier getTexture(BeaverEntityRenderState state) {
        return Identifier.of(CanadaMod.MOD_ID, "textures/entity/beaver/beaver.png");
    }

    @Override
    public BeaverEntityRenderState createRenderState() {
        return new BeaverEntityRenderState();
    }
    
}
