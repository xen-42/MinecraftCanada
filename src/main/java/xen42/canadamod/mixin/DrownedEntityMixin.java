package xen42.canadamod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.entities.BeaverEntity;
import xen42.canadamod.entities.DrownedFleeEntityGoal;

@Mixin(DrownedEntity.class)
public class DrownedEntityMixin {
    @Inject(at = @At("HEAD"), method = "initCustomGoals")
    protected void initCustomGoals(CallbackInfo info) {
        var drowned = (DrownedEntity)(Object)this;
        drowned.goalSelector.add(1, new DrownedFleeEntityGoal(drowned, BeaverEntity.class, 12.0F, 1.0D, 1.2D));
    }
}
