package xen42.canadamod.entity;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModel;

public class MooseEntityModel extends EntityModel<MooseEntityRenderState> {

    //private final ModelPart root;
	//private final ModelPart head;
	//private final ModelPart antler;
	//private final ModelPart antler2;

    protected MooseEntityModel(ModelPart root) {
        super(root);
		//this.root = root.getChild("root");
		//this.head = this.root.getChild("head");
		//this.antler = this.head.getChild("antler");
		//this.antler2 = this.head.getChild("antler2");
    }
    
    public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 56).cuboid(-9.0F, -41.0F, -1.0F, 18.0F, 20.0F, 11.0F, new Dilation(0.0F))
		.uv(86, 49).cuboid(-7.0F, -39.0F, 10.0F, 14.0F, 16.0F, 3.0F, new Dilation(0.0F))
		.uv(100, 33).cuboid(-4.0F, -37.0F, 12.0F, 8.0F, 7.0F, 6.0F, new Dilation(0.0F))
		.uv(62, 69).cuboid(-8.0F, -38.0F, -18.0F, 16.0F, 17.0F, 17.0F, new Dilation(0.0F))
		.uv(0, 102).mirrored().cuboid(-8.0F, -21.0F, 4.0F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 102).cuboid(3.0F, -21.0F, 4.0F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F))
		.uv(0, 102).mirrored().cuboid(-8.0F, -21.0F, -16.0F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 102).cuboid(3.0F, -21.0F, -16.0F, 5.0F, 21.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData tail_r1 = root.addChild("tail_r1", ModelPartBuilder.create().uv(32, 48).cuboid(-2.0F, -4.5315F, -2.1131F, 4.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -26.0F, -18.0F, -0.4363F, 0.0F, 0.0F));

		ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(86, 26).cuboid(-1.0F, 0.0F, -3.0F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F))
		.uv(33, 112).cuboid(-5.0F, -7.924F, -4.8682F, 10.0F, 8.0F, 8.0F, new Dilation(0.0F))
		.uv(36, 95).cuboid(-3.0F, -5.924F, 3.1318F, 6.0F, 5.0F, 7.0F, new Dilation(0.0F))
		.uv(57, 46).cuboid(-4.0F, -9.924F, -2.8682F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(57, 46).cuboid(2.0F, -9.924F, -2.8682F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 21).cuboid(-5.0F, -6.924F, -0.8682F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 21).cuboid(3.0F, -6.924F, -0.8682F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -29.0F, 21.0F, -0.1745F, 0.0F, 0.0F));

		ModelPartData antler = head.addChild("antler", ModelPartBuilder.create().uv(0, 13).cuboid(-8.0F, -6.924F, -1.8682F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(50, 19).cuboid(-8.0F, -9.924F, -2.8682F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(25, 30).cuboid(-9.0F, -10.924F, -2.8682F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 28).cuboid(-3.0F, -13.924F, -2.8682F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(14, 30).cuboid(-5.0F, -13.924F, -2.8682F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 9).cuboid(-7.0F, -12.924F, -2.8682F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 32).cuboid(-4.0F, -11.924F, -2.8682F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(21, 14).cuboid(-6.0F, -10.924F, -2.8682F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-5.0F, 0.0F, 1.0F));

		ModelPartData antler2 = head.addChild("antler2", ModelPartBuilder.create().uv(0, 13).cuboid(-7.0F, -6.924F, -1.8682F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(50, 19).cuboid(-5.0F, -9.924F, -2.8682F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 11).cuboid(1.0F, -10.924F, -2.8682F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(35, 15).cuboid(-3.0F, -13.924F, -2.8682F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(49, 29).cuboid(-5.0F, -13.924F, -2.8682F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(20, 22).cuboid(-1.0F, -12.924F, -2.8682F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(40, 31).cuboid(-4.0F, -11.924F, -2.8682F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(28, 23).cuboid(-2.0F, -10.924F, -2.8682F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(12.0F, 0.0F, 1.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
}
