package xen42.canadamod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.world.World;
import xen42.canadamod.entities.DuckEntity;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity{

    protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
	public void tickMovementOverride(CallbackInfo info) {
        if ((Object)this instanceof DuckEntity) {
            // Only call super
            super.tickMovement();
            info.cancel();
        }

    }
    
}
