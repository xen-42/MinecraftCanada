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
	private final ModelPart head;
	private final ModelPart bb_main;

	public BeaverEntityModel(ModelPart root) {
        super(root);
		this.head = root.getChild("head");
		this.bb_main = root.getChild("bb_main");
	}

	public static TexturedModelData getTexturedModelData() {
        ModelData meshdefinition = new ModelData();
		ModelPartData partdefinition = meshdefinition.getRoot();

		ModelPartData head = partdefinition.addChild("head", ModelPartBuilder.create().uv(0, 26).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 5.0F, 4.0F, new Dilation(0.0F))
		.uv(28, 21).cuboid(-2.0F, -1.0F, -4.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(20, 31).cuboid(-1.0F, 1.0F, -4.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 18.0F, -5.0F));

		ModelPartData bb_main = partdefinition.addChild("bb_main", ModelPartBuilder.create().uv(1, 1).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 7.0F, 9.0F, new Dilation(0.0F))
		.uv(28, 30).cuboid(2.0F, -3.0F, -4.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(28, 30).cuboid(2.0F, -3.0F, 3.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(28, 25).cuboid(-4.0F, -3.0F, 3.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(28, 25).cuboid(-4.0F, -3.0F, -4.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData tail_r1 = bb_main.addChild("tail_r1", ModelPartBuilder.create().uv(0, 17).cuboid(-3.0F, -1.0F, -1.0F, 6.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.0F, 4.0F, -0.2182F, 0.0F, 0.0F));

		return TexturedModelData.of(meshdefinition, 64, 64);
	}
}
