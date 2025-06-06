// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class moose extends EntityModel<Entity> {
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart oops_the_head_is_backwards;
	private final ModelPart left_antler;
	private final ModelPart right_antler;
	private final ModelPart harness;
	private final ModelPart body;
	private final ModelPart saddle;
	private final ModelPart left_front_leg;
	private final ModelPart right_front_leg;
	private final ModelPart left_hind_leg;
	private final ModelPart right_hind_leg;
	public moose(ModelPart root) {
		this.tail = root.getChild("tail");
		this.head = root.getChild("head");
		this.oops_the_head_is_backwards = this.head.getChild("oops_the_head_is_backwards");
		this.left_antler = this.oops_the_head_is_backwards.getChild("left_antler");
		this.right_antler = this.oops_the_head_is_backwards.getChild("right_antler");
		this.harness = this.oops_the_head_is_backwards.getChild("harness");
		this.body = root.getChild("body");
		this.saddle = this.body.getChild("saddle");
		this.left_front_leg = root.getChild("left_front_leg");
		this.right_front_leg = root.getChild("right_front_leg");
		this.left_hind_leg = root.getChild("left_hind_leg");
		this.right_hind_leg = root.getChild("right_hind_leg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData tail = modelPartData.addChild("tail", ModelPartBuilder.create().uv(32, 48).cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, 14.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -10.0F, -15.0F));

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
		.uv(21, 14).cuboid(-6.0F, -12.0F, -5.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 0.0F, 1.0F));

		ModelPartData right_antler = oops_the_head_is_backwards.addChild("right_antler", ModelPartBuilder.create().uv(0, 13).cuboid(-7.0F, -12.0F, -4.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(50, 19).cuboid(-5.0F, -15.0F, -5.0F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 11).cuboid(1.0F, -16.0F, -5.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(35, 15).cuboid(-3.0F, -19.0F, -5.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(49, 29).cuboid(-5.0F, -19.0F, -5.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(20, 22).cuboid(-1.0F, -18.0F, -5.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(40, 31).cuboid(-4.0F, -18.0F, -5.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(28, 23).cuboid(-2.0F, -17.0F, -5.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(12.0F, 5.0F, 1.0F));

		ModelPartData harness = oops_the_head_is_backwards.addChild("harness", ModelPartBuilder.create().uv(156, 35).cuboid(-5.0F, -6.0F, -8.0F, 10.0F, 8.0F, 1.0F, new Dilation(0.1F))
		.uv(148, 16).cuboid(-5.0F, -2.0F, -8.0F, 10.0F, 1.0F, 8.0F, new Dilation(0.14F))
		.uv(164, 2).cuboid(-3.0F, -2.0F, -1.0F, 6.0F, 1.0F, 5.0F, new Dilation(0.14F))
		.uv(134, 5).cuboid(-3.0F, -4.0F, 3.0F, 6.0F, 5.0F, 1.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, -2.0F, 1.0F));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 56).cuboid(-9.0F, -41.0F, -1.0F, 18.0F, 20.0F, 11.0F, new Dilation(0.0F))
		.uv(86, 49).cuboid(-7.0F, -39.0F, 10.0F, 14.0F, 16.0F, 3.0F, new Dilation(0.0F))
		.uv(62, 69).cuboid(-8.0F, -38.0F, -18.0F, 16.0F, 17.0F, 17.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 24.0F, -4.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData saddle = body.addChild("saddle", ModelPartBuilder.create().uv(139, 66).cuboid(-8.0F, -38.0F, -12.0F, 16.0F, 2.0F, 11.0F, new Dilation(0.1F))
		.uv(146, 83).cuboid(-8.0F, -36.0F, -8.0F, 16.0F, 8.0F, 3.0F, new Dilation(0.09F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData left_front_leg = modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(0, 102).mirrored().cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-5.5F, 3.0F, 10.5F));

		ModelPartData right_front_leg = modelPartData.addChild("right_front_leg", ModelPartBuilder.create().uv(0, 102).cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, 3.0F, 10.5F));

		ModelPartData left_hind_leg = modelPartData.addChild("left_hind_leg", ModelPartBuilder.create().uv(0, 102).mirrored().cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-5.5F, 3.0F, -10.5F));

		ModelPartData right_hind_leg = modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(0, 102).cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, 3.0F, -10.5F));
		return TexturedModelData.of(modelData, 192, 128);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		tail.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		left_front_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		right_front_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		left_hind_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		right_hind_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}