// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class moose_helmet extends EntityModel<Entity> {
	private final ModelPart hat;
	private final ModelPart left_antler;
	private final ModelPart right_antler;
	public moose_helmet(ModelPart root) {
		this.hat = root.getChild("hat");
		this.left_antler = this.hat.getChild("left_antler");
		this.right_antler = this.hat.getChild("right_antler");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData hat = modelPartData.addChild("hat", ModelPartBuilder.create().uv(0, 37).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData left_antler = hat.addChild("left_antler", ModelPartBuilder.create().uv(0, 1).cuboid(-8.0F, -7.0F, -6.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(43, 10).cuboid(-8.0F, -10.0F, -5.0F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(40, 12).cuboid(-9.0F, -11.0F, -5.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(41, 1).cuboid(-3.0F, -14.0F, -5.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(19, 16).cuboid(-5.0F, -14.0F, -5.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 9).cuboid(-7.0F, -13.0F, -5.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 19).cuboid(-4.0F, -13.0F, -5.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(21, 14).cuboid(-6.0F, -12.0F, -5.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 0.0F, 5.0F));

		ModelPartData right_antler = hat.addChild("right_antler", ModelPartBuilder.create().uv(0, 6).cuboid(-7.0F, -12.0F, -6.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(47, 2).cuboid(-5.0F, -15.0F, -5.0F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(22, 2).cuboid(1.0F, -16.0F, -5.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(35, 8).cuboid(-3.0F, -19.0F, -5.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(48, 11).cuboid(-5.0F, -19.0F, -5.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(20, 15).cuboid(-1.0F, -18.0F, -5.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(40, 18).cuboid(-4.0F, -18.0F, -5.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(28, 16).cuboid(-2.0F, -17.0F, -5.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(11.0F, 5.0F, 5.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		hat.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}