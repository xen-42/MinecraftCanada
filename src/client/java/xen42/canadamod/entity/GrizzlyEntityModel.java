package xen42.canadamod.entity;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.ModelTransformer;
import net.minecraft.client.render.entity.model.PolarBearEntityModel;
import net.minecraft.client.render.entity.state.PolarBearEntityRenderState;
import xen42.canadamod.entities.GrizzlyEntity;

public class GrizzlyEntityModel<T extends GrizzlyEntity> extends PolarBearEntityModel {

    public GrizzlyEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
	public void setAngles(PolarBearEntityRenderState livingEntityRenderState) {
        super.setAngles(livingEntityRenderState);
		head.xScale = livingEntityRenderState.baby ? 1.5f : 1f;
		head.yScale = livingEntityRenderState.baby ? 1.5f : 1f;
		head.zScale = livingEntityRenderState.baby ? 1.5f : 1f;
	}

    public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(
			EntityModelPartNames.HEAD,
			ModelPartBuilder.create()
				.uv(0, 0)
				.cuboid(-3.5F, -3.0F, -3.0F, 7.0F, 7.0F, 7.0F)
				.uv(0, 44)
				.cuboid(EntityModelPartNames.MOUTH, -2.5F, 0.0F, -5.0F, 5.0F, 4.0F, 2.0F)
				.uv(26, 0)
				.cuboid(EntityModelPartNames.RIGHT_EAR, -4.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F)
				.uv(26, 0)
				.mirrored()
				.cuboid(EntityModelPartNames.LEFT_EAR, 2.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F),
			ModelTransform.origin(0.0F, 10.0F, -16.0F)
		);
		modelPartData.addChild(
			EntityModelPartNames.BODY,
			ModelPartBuilder.create().uv(0, 19).cuboid(-5.0F, -13.0F, -7.0F, 14.0F, 14.0F, 11.0F).uv(39, 0).cuboid(-4.0F, -25.0F, -7.0F, 12.0F, 12.0F, 10.0F),
			ModelTransform.of(-2.0F, 9.0F, 12.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
		);
		int i = 10;
		ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(50, 22).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 8.0F);
		modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, modelPartBuilder, ModelTransform.origin(-4.5F, 14.0F, 6.0F));
		modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, modelPartBuilder, ModelTransform.origin(4.5F, 14.0F, 6.0F));
		ModelPartBuilder modelPartBuilder2 = ModelPartBuilder.create().uv(50, 40).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 6.0F);
		modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, modelPartBuilder2, ModelTransform.origin(-3.5F, 14.0F, -8.0F));
		modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, modelPartBuilder2, ModelTransform.origin(3.5F, 14.0F, -8.0F));
		return TexturedModelData.of(modelData, 128, 64).transform(ModelTransformer.NO_OP).transform(ModelTransformer.scaling(1.2F));
	}
    
}
