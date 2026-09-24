// Made with Blockbench 5.1.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.example.mod;
   
public class moose_head extends EntityModel<Entity> {
	private final ModelPart head;
	private final ModelPart left_antler;
	private final ModelPart right_antler;
	public moose_head(ModelPart root) {
		this.head = root.getChild("head");
		this.left_antler = root.getChild("left_antler");
		this.right_antler = root.getChild("right_antler");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(80, 54).mirrored().cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 45).mirrored().cuboid(-5.0F, -8.0F, -3.0F, 10.0F, 8.0F, 11.0F, new Dilation(0.0F)).mirrored(false)
		.uv(48, 52).mirrored().cuboid(-3.0F, -6.0F, -10.0F, 6.0F, 5.0F, 7.0F, new Dilation(0.0F)).mirrored(false)
		.uv(90, 0).cuboid(2.0F, -10.0F, 2.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(90, 0).cuboid(-4.0F, -10.0F, 2.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData left_antler = head.addChild("left_antler", ModelPartBuilder.create().uv(0, 13).cuboid(-8.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(50, 19).cuboid(-8.0F, -4.0F, 0.0F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(25, 30).cuboid(-9.0F, -5.0F, 0.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 28).cuboid(-3.0F, -8.0F, 0.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(14, 30).cuboid(-5.0F, -8.0F, 0.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 9).cuboid(-7.0F, -7.0F, 0.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 32).cuboid(-4.0F, -7.0F, 0.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(21, 14).cuboid(-6.0F, -6.0F, 0.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -6.0F, 0.0F));

		ModelPartData right_antler = head.addChild("right_antler", ModelPartBuilder.create().uv(0, 13).cuboid(0.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(50, 19).cuboid(2.0F, -4.0F, 0.0F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 11).cuboid(8.0F, -5.0F, 0.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(35, 15).cuboid(4.0F, -8.0F, 0.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(49, 29).cuboid(2.0F, -8.0F, 0.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(20, 22).cuboid(6.0F, -7.0F, 0.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(40, 31).cuboid(3.0F, -7.0F, 0.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(28, 23).cuboid(5.0F, -6.0F, 0.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, -6.0F, 0.0F));
		return TexturedModelData.of(modelData, 96, 64);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}