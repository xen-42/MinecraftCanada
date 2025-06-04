// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class moose extends EntityModel<Entity> {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart antler;
	private final ModelPart antler2;
	public moose(ModelPart root) {
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
		this.antler = this.head.getChild("antler");
		this.antler2 = this.head.getChild("antler2");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(82, 102).cuboid(-6.0F, -31.0F, -1.0F, 12.0F, 15.0F, 11.0F, new Dilation(0.0F))
		.uv(93, 57).cuboid(-6.0F, -29.0F, 10.0F, 12.0F, 11.0F, 3.0F, new Dilation(0.0F))
		.uv(103, 33).cuboid(-2.0F, -28.0F, 12.0F, 4.0F, 5.0F, 6.0F, new Dilation(0.0F))
		.uv(82, 72).cuboid(-6.0F, -29.0F, -12.0F, 12.0F, 13.0F, 11.0F, new Dilation(0.0F))
		.uv(0, 108).mirrored().cuboid(-6.0F, -16.0F, 5.0F, 4.0F, 16.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 108).cuboid(2.0F, -16.0F, 5.0F, 4.0F, 16.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 108).mirrored().cuboid(-6.0F, -16.0F, -11.0F, 4.0F, 16.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 108).cuboid(2.0F, -16.0F, -11.0F, 4.0F, 16.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData tail_r1 = root.addChild("tail_r1", ModelPartBuilder.create().uv(32, 48).cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -26.0F, -12.0F, -0.4363F, 0.0F, 0.0F));

		ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(38, 116).cuboid(-3.0F, -3.0F, -1.0F, 6.0F, 6.0F, 5.0F, new Dilation(0.0F))
		.uv(38, 96).cuboid(-2.0F, -1.0F, 4.0F, 4.0F, 4.0F, 6.0F, new Dilation(0.0F))
		.uv(2, 2).cuboid(-3.0F, -5.0F, -1.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(2, 2).cuboid(1.0F, -5.0F, -1.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(0, 21).cuboid(-5.0F, -2.0F, 0.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 21).cuboid(3.0F, -2.0F, 0.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -26.0F, 16.0F, -0.1745F, 0.0F, 0.0F));

		ModelPartData antler = head.addChild("antler", ModelPartBuilder.create().uv(-6, 21).cuboid(-8.0F, -2.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(-4, 21).cuboid(-8.0F, -4.0F, -2.0F, 6.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 21).cuboid(-9.0F, -5.0F, -2.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 21).cuboid(-3.0F, -7.0F, -2.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 21).cuboid(-5.0F, -7.0F, -2.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 21).cuboid(-7.0F, -6.0F, -2.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 21).cuboid(-4.0F, -6.0F, -2.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 21).cuboid(-6.0F, -5.0F, -2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 0.0F, 1.0F));

		ModelPartData antler2 = head.addChild("antler2", ModelPartBuilder.create().uv(-5, 21).cuboid(-7.0F, -2.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(-4, 21).cuboid(-5.0F, -4.0F, -2.0F, 6.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 21).cuboid(1.0F, -5.0F, -2.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 21).cuboid(-3.0F, -7.0F, -2.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 21).cuboid(-5.0F, -7.0F, -2.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 21).cuboid(-1.0F, -6.0F, -2.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 21).cuboid(-4.0F, -6.0F, -2.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 21).cuboid(-2.0F, -5.0F, -2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(9.0F, 0.0F, 1.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}