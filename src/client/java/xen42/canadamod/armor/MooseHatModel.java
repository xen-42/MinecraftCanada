package xen42.canadamod.armor;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;

public class MooseHatModel {
    
    public static TexturedModelData getModel() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData hat = modelPartData.addChild("hat", ModelPartBuilder.create().uv(0, 37).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.1F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData left_antler = hat.addChild("left_antler", ModelPartBuilder.create().uv(0, 1).cuboid(-8.0F, -7.0F, -6.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(43, 10).cuboid(-8.0F, -10.0F, -5.0F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(40, 12).cuboid(-9.0F, -11.0F, -5.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(41, 1).cuboid(-3.0F, -14.0F, -5.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(19, 16).cuboid(-5.0F, -14.0F, -5.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 9).cuboid(-7.0F, -13.0F, -5.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 19).cuboid(-4.0F, -13.0F, -5.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(21, 14).cuboid(-6.0F, -12.0F, -5.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-3.0F, 0.0F, 5.0F));

		ModelPartData right_antler = hat.addChild("right_antler", ModelPartBuilder.create().uv(0, 6).cuboid(-7.0F, -12.0F, -6.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(47, 2).cuboid(-5.0F, -15.0F, -5.0F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(22, 2).cuboid(1.0F, -16.0F, -5.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(35, 8).cuboid(-3.0F, -19.0F, -5.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(48, 11).cuboid(-5.0F, -19.0F, -5.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(20, 15).cuboid(-1.0F, -18.0F, -5.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(40, 18).cuboid(-4.0F, -18.0F, -5.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(28, 16).cuboid(-2.0F, -17.0F, -5.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(11.0F, 5.0F, 5.0F));
		return TexturedModelData.of(modelData, 64, 64);
    }
}
