package xen42.canadamod.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import xen42.canadamod.entities.DuckEntity;

public class DuckEntityModel<T extends DuckEntity> extends ChickenEntityModel {

    public DuckEntityModel(ModelPart modelPart) {
        super(modelPart);
    }
    
}
