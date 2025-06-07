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
	private ModelPart leftAntler, rightAntler, saddle, harness;
    protected MooseEntityModel(ModelPart root) {
        super(root);
		this.leftAntler = head.getChild("oops_the_head_is_backwards").getChild("left_antler");
		this.rightAntler = head.getChild("oops_the_head_is_backwards").getChild("right_antler");
		this.harness = head.getChild("oops_the_head_is_backwards").getChild("harness");
		this.saddle = body.getChild("saddle");
    }
    
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -10.0F, -15.0F));

		ModelPartData oops_the_head_is_backwards = head.addChild("oops_the_head_is_backwards", ModelPartBuilder.create().uv(84, 24).cuboid(-1.0F, 0.0F, -7.0F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F))
		.uv(30, 109).cuboid(-5.0F, -7.924F, -9.8682F, 10.0F, 8.0F, 11.0F, new Dilation(0.0F))
		.uv(36, 95).cuboid(-3.0F, -5.924F, 1.1318F, 6.0F, 5.0F, 7.0F, new Dilation(0.0F))
		.uv(57, 46).cuboid(-4.0F, -9.924F, -4.8682F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(57, 46).cuboid(2.0F, -9.924F, -4.8682F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 5.0F, -10.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData left_antler = oops_the_head_is_backwards.addChild("left_antler", ModelPartBuilder.create().uv(0, 13).cuboid(-8.0F, -7.0F, -4.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(50, 19).cuboid(-8.0F, -10.0F, -5.0F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(25, 30).cuboid(-9.0F, -11.0F, -5.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 28).cuboid(-3.0F, -14.0F, -5.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(14, 30).cuboid(-5.0F, -14.0F, -5.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 9).cuboid(-7.0F, -13.0F, -5.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 32).cuboid(-4.0F, -13.0F, -5.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(21, 14).cuboid(-6.0F, -12.0F, -5.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-5.0F, 0.0F, 1.0F));

		ModelPartData right_antler = oops_the_head_is_backwards.addChild("right_antler", ModelPartBuilder.create().uv(0, 13).cuboid(-7.0F, -12.0F, -4.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(50, 19).cuboid(-5.0F, -15.0F, -5.0F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 11).cuboid(1.0F, -16.0F, -5.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(35, 15).cuboid(-3.0F, -19.0F, -5.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(49, 29).cuboid(-5.0F, -19.0F, -5.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(20, 22).cuboid(-1.0F, -18.0F, -5.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(40, 31).cuboid(-4.0F, -18.0F, -5.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(28, 23).cuboid(-2.0F, -17.0F, -5.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(12.0F, 5.0F, 1.0F));

		ModelPartData harness = oops_the_head_is_backwards.addChild("harness", ModelPartBuilder.create().uv(156, 35).cuboid(-5.0F, -6.0F, -8.0F, 10.0F, 8.0F, 1.0F, new Dilation(0.1F))
		.uv(148, 16).cuboid(-5.0F, -2.0F, -8.0F, 10.0F, 1.0F, 8.0F, new Dilation(0.14F))
		.uv(164, 2).cuboid(-3.0F, -2.0F, -1.0F, 6.0F, 1.0F, 5.0F, new Dilation(0.14F))
		.uv(134, 5).cuboid(-3.0F, -4.0F, 3.0F, 6.0F, 5.0F, 1.0F, new Dilation(0.1F)), ModelTransform.origin(0.0F, -2.0F, 1.0F));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 56).cuboid(-9.0F, -20.0F, -1.0F, 18.0F, 20.0F, 11.0F, new Dilation(0.0F))
		.uv(86, 49).cuboid(-7.0F, -18.0F, 10.0F, 14.0F, 16.0F, 3.0F, new Dilation(0.0F))
		.uv(62, 69).cuboid(-8.0F, -17.0F, -18.0F, 16.0F, 17.0F, 17.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, -4.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData saddle = body.addChild("saddle", ModelPartBuilder.create().uv(139, 66).cuboid(-8.0F, -38.0F, -12.0F, 16.0F, 2.0F, 11.0F, new Dilation(0.1F))
		.uv(146, 83).cuboid(-8.0F, -36.0F, -8.0F, 16.0F, 8.0F, 3.0F, new Dilation(0.09F)), ModelTransform.origin(0.0F, 21.0F, 0.0F));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(32, 48).cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -11.0F, -18.0F, -0.3491F, 0.0F, 0.0F));

		ModelPartData left_front_leg = modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(0, 102).mirrored().cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(5.5F, 3.0F, -10.5F));

		ModelPartData right_front_leg = modelPartData.addChild("right_front_leg", ModelPartBuilder.create().uv(0, 102).cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(-5.5F, 3.0F, -10.5F));

		ModelPartData left_hind_leg = modelPartData.addChild("left_hind_leg", ModelPartBuilder.create().uv(0, 102).mirrored().cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(5.5F, 3.0F, 10.5F));

		ModelPartData right_hind_leg = modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(0, 102).cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(-5.5F, 3.0F, 10.5F));
		return TexturedModelData.of(modelData, 192, 128);
	}

	@Override
	public void setAngles(MooseEntityRenderState livingEntityRenderState) {
		super.setAngles(livingEntityRenderState);

		this.animate(livingEntityRenderState.attackAnimationState, MooseEntityAnimation.ATTACK, livingEntityRenderState.age, 1f);

		leftAntler.hidden = livingEntityRenderState.baby || livingEntityRenderState.leftAntlerMissing;
		rightAntler.hidden = livingEntityRenderState.baby || livingEntityRenderState.rightAntlerMissing;

		saddle.hidden = livingEntityRenderState.saddleStack.isEmpty();
		harness.hidden = livingEntityRenderState.saddleStack.isEmpty();

		head.xScale = livingEntityRenderState.baby ? 1.5f : 1f;
		head.yScale = livingEntityRenderState.baby ? 1.5f : 1f;
		head.zScale = livingEntityRenderState.baby ? 1.5f : 1f;
	}
}
