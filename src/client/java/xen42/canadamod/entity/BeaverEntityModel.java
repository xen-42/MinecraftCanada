package xen42.canadamod.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;

public class BeaverEntityModel extends EntityModel<BeaverEntityRenderState> {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart body;
	private final ModelPart leg_front_left;
	private final ModelPart leg_front_right;
	private final ModelPart leg_back_left;
	private final ModelPart leg_back_right;
	private final ModelPart bb_main;

	public BeaverEntityModel(ModelPart root) {
        super(root);
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

	public static TexturedModelData getTexturedModelData() {
		ModelData meshdefinition = new ModelData();
		ModelPartData ModelPartData = meshdefinition.getRoot();

		ModelPartData root = ModelPartData.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 18.0F, 0.0F));

		ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 26).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 5.0F, 4.0F, new Dilation(0.0F))
		.uv(28, 21).cuboid(-2.0F, -1.0F, -4.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(20, 31).cuboid(-1.0F, 1.0F, -4.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, -5.0F));

		ModelPartData tail = root.addChild("tail", ModelPartBuilder.create().uv(0, 17).cuboid(-3.0F, -1.0F, -1.0F, 6.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 1.0F, 6.0F));

		ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 6.0F, 0.0F));

		ModelPartData leg_front_left = root.addChild("leg_front_left", ModelPartBuilder.create().uv(28, 30).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(3.0F, 3.0F, -3.0F));

		ModelPartData leg_front_right = root.addChild("leg_front_right", ModelPartBuilder.create().uv(28, 25).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-3.0F, 3.0F, -3.0F));

		ModelPartData leg_back_left = root.addChild("leg_back_left", ModelPartBuilder.create().uv(28, 30).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(3.0F, 3.0F, 5.0F));

		ModelPartData leg_back_right = root.addChild("leg_back_right", ModelPartBuilder.create().uv(28, 25).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-3.0F, 3.0F, 5.0F));

		ModelPartData bb_main = ModelPartData.addChild("bb_main", ModelPartBuilder.create().uv(2, 1).cuboid(2.0F, -10.0F, -7.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(2, 1).cuboid(-3.0F, -10.0F, -7.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		return TexturedModelData.of(meshdefinition, 64, 64);
	}
}
