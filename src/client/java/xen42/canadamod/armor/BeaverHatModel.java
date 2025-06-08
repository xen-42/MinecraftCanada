package xen42.canadamod.armor;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;

public class BeaverHatModel {
    
    public static TexturedModelData getModel() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData hat = modelPartData.addChild("hat", ModelPartBuilder.create().uv(0, 15).cuboid(-6.0F, -6.0F, -6.0F, 12.0F, 1.0F, 12.0F, new Dilation(0.1F))
		.uv(0, 0).cuboid(-4.0F, -12.0F, -4.0F, 8.0F, 6.0F, 8.0F, new Dilation(0.1F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
    }
}
