package xen42.canadamod.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.ChickenEntityRenderState;
import xen42.canadamod.entities.DuckEntity;

public class DuckEntityModel<T extends DuckEntity> extends ChickenEntityModel {

    private ModelPart head;

    public DuckEntityModel(ModelPart modelPart) {
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
}
