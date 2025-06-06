package xen42.canadamod.entity;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;

public class MooseEntityModel extends QuadrupedEntityModel<MooseEntityRenderState> {
	private ModelPart leftAntler, rightAntler;
    protected MooseEntityModel(ModelPart root) {
        super(root);
		this.leftAntler = head.getChild("oops_the_head_is_backwards").getChild("left_antler");
		this.rightAntler = head.getChild("oops_the_head_is_backwards").getChild("right_antler");
    }
    
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData tail = modelPartData.addChild("tail", ModelPartBuilder.create().uv(32, 48).cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, 14.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -10.0F, -15.0F));

		ModelPartData oops_the_head_is_backwards = head.addChild("oops_the_head_is_backwards", ModelPartBuilder.create().uv(84, 24).cuboid(-1.0F, 0.0F, -7.0F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F))
		.uv(30, 109).cuboid(-5.0F, -7.924F, -9.8682F, 10.0F, 8.0F, 11.0F, new Dilation(0.0F))
		.uv(36, 95).cuboid(-3.0F, -5.924F, 1.1318F, 6.0F, 5.0F, 7.0F, new Dilation(0.0F))
		.uv(57, 46).cuboid(-4.0F, -9.924F, -4.8682F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(57, 46).cuboid(2.0F, -9.924F, -4.8682F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 5.0F, -10.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData antler = oops_the_head_is_backwards.addChild("left_antler", ModelPartBuilder.create().uv(0, 13).cuboid(-8.0F, -6.924F, -3.8682F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(50, 19).cuboid(-8.0F, -9.924F, -4.8682F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(25, 30).cuboid(-9.0F, -10.924F, -4.8682F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 28).cuboid(-3.0F, -13.924F, -4.8682F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(14, 30).cuboid(-5.0F, -13.924F, -4.8682F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 9).cuboid(-7.0F, -12.924F, -4.8682F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 32).cuboid(-4.0F, -11.924F, -4.8682F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(21, 14).cuboid(-6.0F, -10.924F, -4.8682F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-5.0F, 0.0F, 1.0F));

		ModelPartData antler2 = oops_the_head_is_backwards.addChild("right_antler", ModelPartBuilder.create().uv(0, 13).cuboid(-7.0F, -6.924F, -3.8682F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(50, 19).cuboid(-5.0F, -9.924F, -4.8682F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 11).cuboid(1.0F, -10.924F, -4.8682F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(35, 15).cuboid(-3.0F, -13.924F, -4.8682F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(49, 29).cuboid(-5.0F, -13.924F, -4.8682F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(20, 22).cuboid(-1.0F, -12.924F, -4.8682F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(40, 31).cuboid(-4.0F, -11.924F, -4.8682F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(28, 23).cuboid(-2.0F, -10.924F, -4.8682F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(12.0F, 0.0F, 1.0F));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 56).cuboid(-9.0F, -41.0F, -1.0F, 18.0F, 20.0F, 11.0F, new Dilation(0.0F))
		.uv(86, 49).cuboid(-7.0F, -39.0F, 10.0F, 14.0F, 16.0F, 3.0F, new Dilation(0.0F))
		.uv(62, 69).cuboid(-8.0F, -38.0F, -18.0F, 16.0F, 17.0F, 17.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 24.0F, -4.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData left_front_leg = modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(0, 102).mirrored().cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-5.5F, 3.0F, 10.5F));

		ModelPartData right_front_leg = modelPartData.addChild("right_front_leg", ModelPartBuilder.create().uv(0, 102).cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(5.5F, 3.0F, 10.5F));

		ModelPartData left_hind_leg = modelPartData.addChild("left_hind_leg", ModelPartBuilder.create().uv(0, 102).mirrored().cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-5.5F, 3.0F, -10.5F));

		ModelPartData right_hind_leg = modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(0, 102).cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(5.5F, 3.0F, -10.5F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(MooseEntityRenderState livingEntityRenderState) {
		super.setAngles(livingEntityRenderState);

		leftAntler.hidden = livingEntityRenderState.baby;
		rightAntler.hidden = livingEntityRenderState.baby;

		leftAntler.xScale = livingEntityRenderState.baby ? 0 : 1;
		leftAntler.yScale = livingEntityRenderState.baby ? 0 : 1;
		leftAntler.zScale = livingEntityRenderState.baby ? 0 : 1;

		rightAntler.xScale = livingEntityRenderState.baby ? 0 : 1;
		rightAntler.yScale = livingEntityRenderState.baby ? 0 : 1;
		rightAntler.zScale = livingEntityRenderState.baby ? 0 : 1;

		head.xScale = livingEntityRenderState.baby ? 1.5f : 1f;
		head.yScale = livingEntityRenderState.baby ? 1.5f : 1f;
		head.zScale = livingEntityRenderState.baby ? 1.5f : 1f;
	}
}
