package xen42.canadamod.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;

public class BeaverEntityModel extends QuadrupedEntityModel<BeaverEntityRenderState> {
	public BeaverEntityModel(ModelPart root) {
        super(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData ModelData = new ModelData();
		ModelPartData ModelPartData = ModelData.getRoot();

		ModelPartData head = ModelPartData.addChild("head", ModelPartBuilder.create().uv(0, 26).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 5.0F, 4.0F, new Dilation(0.0F))
		.uv(28, 21).cuboid(-2.0F, -1.0F, -4.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(20, 31).cuboid(-1.0F, 1.0F, -4.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(2, 1).cuboid(2.0F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(2, 1).cuboid(-3.0F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 18.0F, -5.0F));

		ModelPartData tail = ModelPartData.addChild("tail", ModelPartBuilder.create().uv(0, 17).cuboid(-3.0F, -1.0F, -1.0F, 6.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 19.0F, 6.0F));

		ModelPartData body = ModelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData leg_front_left = ModelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(28, 30).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(3.0F, 21.0F, -3.0F));

		ModelPartData leg_front_right = ModelPartData.addChild("right_front_leg", ModelPartBuilder.create().uv(28, 25).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-3.0F, 21.0F, -3.0F));

		ModelPartData leg_hind_left = ModelPartData.addChild("left_hind_leg", ModelPartBuilder.create().uv(28, 30).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(3.0F, 21.0F, 5.0F));

		ModelPartData leg_hind_right = ModelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(28, 25).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-3.0F, 21.0F, 5.0F));

		return TexturedModelData.of(ModelData, 64, 64);
	}

	@Override
	public void setAngles(BeaverEntityRenderState livingEntityRenderState) {
		super.setAngles(livingEntityRenderState);

		head.xScale = livingEntityRenderState.baby ? 1.5f : 1f;
		head.yScale = livingEntityRenderState.baby ? 1.5f : 1f;
		head.zScale = livingEntityRenderState.baby ? 1.5f : 1f;
	}
}
