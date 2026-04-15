package xen42.canadamod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.mob.DrownedEntity;
import xen42.canadamod.entities.BeaverEntity;
import xen42.canadamod.entities.DrownedFleeEntityGoal;

@Mixin(DrownedEntity.class)
public class DrownedEntityMixin {
    @Inject(at = @At("HEAD"), method = "initCustomGoals")
    protected void initDrownedFleeGoal(CallbackInfo info) {
        var drowned = (DrownedEntity)(Object)this;
        drowned.goalSelector.add(1, new DrownedFleeEntityGoal<>(drowned, BeaverEntity.class, 12.0F, 1.0D, 1.2D));
    }
}
