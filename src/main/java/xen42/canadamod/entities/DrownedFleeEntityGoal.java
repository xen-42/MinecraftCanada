package xen42.canadamod.entities;

import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.DrownedEntity;

// This class exists solely to allow patching the tick method to update the nagiation field which is final (can modify only in a mixin)
public class DrownedFleeEntityGoal extends FleeEntityGoal {

    public DrownedEntity drowned;

    public DrownedFleeEntityGoal(DrownedEntity mob, Class fleeFromType, float distance, double slowSpeed, double fastSpeed) {
        super(mob, fleeFromType, distance, slowSpeed, fastSpeed);
        drowned = mob;
    }     
}
