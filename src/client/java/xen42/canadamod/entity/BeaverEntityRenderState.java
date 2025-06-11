package xen42.canadamod.entity;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class BeaverEntityRenderState extends LivingEntityRenderState {
    public final AnimationState chopAnimationState = new AnimationState();

    public BeaverEntityRenderState() {
        super();
    }
}
