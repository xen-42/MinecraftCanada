package xen42.canadamod.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;

public class BeaverEntityModel extends QuadrupedEntityModel<BeaverEntityRenderState> {
	private ModelPart tail;
	
	public BeaverEntityModel(ModelPart root) {
        super(root);
      	this.tail = root.getChild("tail");	
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 26).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 5.0F, 4.0F, new Dilation(0.0F))
		.uv(28, 21).cuboid(-2.0F, -1.0F, -4.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(20, 31).cuboid(-1.0F, 1.0F, -4.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(2, 1).cuboid(2.0F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(2, 1).cuboid(-3.0F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 18.0F, -5.0F));

		ModelPartData tail = modelPartData.addChild("tail", ModelPartBuilder.create().uv(0, 17).cuboid(-3.0F, -1.0F, -1.0F, 6.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 19.0F, 6.0F));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData leg_front_left = modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(28, 30).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(3.0F, 21.0F, -3.0F));

		ModelPartData leg_front_right = modelPartData.addChild("right_front_leg", ModelPartBuilder.create().uv(28, 25).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-3.0F, 21.0F, -3.0F));

		ModelPartData leg_hind_left = modelPartData.addChild("left_hind_leg", ModelPartBuilder.create().uv(28, 30).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(3.0F, 21.0F, 5.0F));

		ModelPartData leg_hind_right = modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(28, 25).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-3.0F, 21.0F, 5.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(BeaverEntityRenderState livingEntityRenderState) {
		this.resetTransforms();
		super.setAngles(livingEntityRenderState);

		this.animate(livingEntityRenderState.chopAnimationState, BeaverEntityAnimation.CHOP, livingEntityRenderState.age, 1f);

		if (livingEntityRenderState.isFrenzied) {
			this.tail.pitch = 30 / MathHelper.DEGREES_PER_RADIAN;
		}
		else if (livingEntityRenderState.isFatigued) {
			this.tail.pitch = -20 / MathHelper.DEGREES_PER_RADIAN;
		}

		head.xScale = livingEntityRenderState.baby ? 1.5f : 1f;
		head.yScale = livingEntityRenderState.baby ? 1.5f : 1f;
		head.zScale = livingEntityRenderState.baby ? 1.5f : 1f;
	}
}
