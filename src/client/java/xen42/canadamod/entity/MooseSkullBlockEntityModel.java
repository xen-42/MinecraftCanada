package xen42.canadamod.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

@Environment(EnvType.CLIENT)
public class MooseSkullBlockEntityModel extends SkullBlockEntityModel {
	private final ModelPart head;

	public MooseSkullBlockEntityModel(ModelPart modelPart) {
		super(modelPart);
		this.head = modelPart.getChild(EntityModelPartNames.HEAD);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		
		ModelPartData head = modelPartData.addChild(
			EntityModelPartNames.HEAD, 
			ModelPartBuilder.create()
				.uv(80, 54).mirrored().cuboid("dewlap", -1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
				.uv(0, 45).mirrored().cuboid(EntityModelPartNames.HEAD, -5.0F, -8.0F, -3.0F, 10.0F, 8.0F, 11.0F, new Dilation(0.0F)).mirrored(false)
				.uv(48, 52).mirrored().cuboid(EntityModelPartNames.MOUTH, -3.0F, -6.0F, -10.0F, 6.0F, 5.0F, 7.0F, new Dilation(0.0F)).mirrored(false)
				.uv(90, 0).cuboid(EntityModelPartNames.LEFT_EAR, 2.0F, -10.0F, 2.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
				.uv(90, 0).cuboid(EntityModelPartNames.RIGHT_EAR, -4.0F, -10.0F, 2.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), 
			ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData left_antler = head.addChild(
			"left_antler",
			ModelPartBuilder.create()
				.uv(0, 13).cuboid(-8.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(50, 19).cuboid(-8.0F, -4.0F, 0.0F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(25, 30).cuboid(-9.0F, -5.0F, 0.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 28).cuboid(-3.0F, -8.0F, 0.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(14, 30).cuboid(-5.0F, -8.0F, 0.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 9).cuboid(-7.0F, -7.0F, 0.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 32).cuboid(-4.0F, -7.0F, 0.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
				.uv(21, 14).cuboid(-6.0F, -6.0F, 0.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), 
			ModelTransform.origin(-5.0F, -6.0F, 0.0F));

		ModelPartData right_antler = head.addChild(
			"right_antler", 
			ModelPartBuilder.create()
				.uv(0, 13).cuboid(0.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(50, 19).cuboid(2.0F, -4.0F, 0.0F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 11).cuboid(8.0F, -5.0F, 0.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
				.uv(35, 15).cuboid(4.0F, -8.0F, 0.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(49, 29).cuboid(2.0F, -8.0F, 0.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(20, 22).cuboid(6.0F, -7.0F, 0.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
				.uv(40, 31).cuboid(3.0F, -7.0F, 0.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
				.uv(28, 23).cuboid(5.0F, -6.0F, 0.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), 
			ModelTransform.origin(5.0F, -6.0F, 0.0F));
		
		return TexturedModelData.of(modelData, 96, 64);
	}

	@Override
	public void setHeadRotation(float animationProgress, float yaw, float pitch) {
		this.head.yaw = yaw * ((float)Math.PI / 180.0F);
		this.head.pitch = pitch * ((float)Math.PI / 180.0F);
	}
}