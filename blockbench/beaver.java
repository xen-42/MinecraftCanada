// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class beaver<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "beaver"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart body;
	private final ModelPart leg_front_left;
	private final ModelPart leg_front_right;
	private final ModelPart leg_back_left;
	private final ModelPart leg_back_right;
	private final ModelPart bb_main;

	public beaver(ModelPart root) {
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
		this.tail = this.root.getChild("tail");
		this.body = this.root.getChild("body");
		this.leg_front_left = this.root.getChild("leg_front_left");
		this.leg_front_right = this.root.getChild("leg_front_right");
		this.leg_back_left = this.root.getChild("leg_back_left");
		this.leg_back_right = this.root.getChild("leg_back_right");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 26).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 21).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 31).addBox(-1.0F, 1.0F, -4.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -5.0F));

		PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, -1.0F, -1.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 6.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition leg_front_left = root.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(28, 30).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 3.0F, -3.0F));

		PartDefinition leg_front_right = root.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(28, 25).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 3.0F, -3.0F));

		PartDefinition leg_back_left = root.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(28, 30).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 3.0F, 5.0F));

		PartDefinition leg_back_right = root.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(28, 25).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 3.0F, 5.0F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(2, 1).addBox(2.0F, -10.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(2, 1).addBox(-3.0F, -10.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}