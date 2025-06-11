package xen42.canadamod.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import xen42.canadamod.entities.DrownedFleeEntityGoal;

// Have to patch the FleeEntityGoal class to allow setting the final fleeingEntityNavigation field in our custom DrownedFleeEntityGoal class
@Mixin(FleeEntityGoal.class)
public class FleeEntityGoalMixin {

    @Shadow @Final @Mutable
    protected EntityNavigation fleeingEntityNavigation;

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo info) {
        var goal = (FleeEntityGoal)(Object)this;
        if (goal instanceof DrownedFleeEntityGoal drownedGoal) {
            this.fleeingEntityNavigation = drownedGoal.drowned.getNavigation();
        }
    }
}
