package xen42.canadamod.entity;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.ChickenEntityRenderState;
import xen42.canadamod.entities.GooseEntity;

public class GooseEntityModel<T extends GooseEntity> extends ChickenEntityModel {

    private ModelPart head;

    public GooseEntityModel(ModelPart modelPart) {
        super(modelPart);
        this.head = modelPart.getChild(EntityModelPartNames.HEAD);
    }
    
    @Override
	public void setAngles(ChickenEntityRenderState livingEntityRenderState) {
        super.setAngles(livingEntityRenderState);
		head.xScale = livingEntityRenderState.baby ? 1.5f : 1f;
		head.yScale = livingEntityRenderState.baby ? 1.5f : 1f;
		head.zScale = livingEntityRenderState.baby ? 1.5f : 1f;
	}

    public static TexturedModelData getTexturedModelData() {
        ModelData meshdefinition = new ModelData();
		ModelPartData partdefinition = meshdefinition.getRoot();

		ModelPartData right_leg = partdefinition.addChild("right_leg", ModelPartBuilder.create().uv(14, 28).cuboid(-3.0F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 20.0F, 1.0F));

		ModelPartData left_leg = partdefinition.addChild("left_leg", ModelPartBuilder.create().uv(26, 28).cuboid(0.0F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 20.0F, 1.0F));

		ModelPartData right_wing = partdefinition.addChild("right_wing", ModelPartBuilder.create().uv(16, 16).cuboid(-1.0F, 0.0F, -3.0F, 1.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.origin(-3.0F, 14.0F, 0.0F));

		ModelPartData left_wing = partdefinition.addChild("left_wing", ModelPartBuilder.create().uv(0, 16).cuboid(0.0F, 0.0F, -3.0F, 1.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.origin(3.0F, 14.0F, 0.0F));

		ModelPartData body = partdefinition.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -4.0F, -5.0F, 6.0F, 6.0F, 10.0F, new Dilation(0.0F))
		.uv(32, 0).cuboid(-1.0F, -10.0F, -5.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 18.0F, 0.0F));

		ModelPartData head = partdefinition.addChild("head", ModelPartBuilder.create().uv(0, 28).cuboid(-2.0F, -3.0F, -3.0F, 4.0F, 4.0F, 3.0F, new Dilation(0.0F))
		.uv(32, 8).cuboid(-1.0F, -1.0F, -6.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 8.0F, -4.0F));

		return TexturedModelData.of(meshdefinition, 64, 64);
	}
}
