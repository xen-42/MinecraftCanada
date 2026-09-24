package xen42.canadamod.entities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.DrownedEntity;

public class DrownedFleeEntityGoal<T extends LivingEntity> extends FleeEntityGoal<T> {

    public DrownedFleeEntityGoal(DrownedEntity mob, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
        super(mob, fleeFromType, distance, slowSpeed, fastSpeed);
    }

	@Override
	public void tick() {
		// Refresh due to Drowned switching between land and water navigation
		this.fleeingEntityNavigation = this.mob.getNavigation();
		super.tick();
	}
}
