package xen42.canadamod.entity;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.item.ItemStack;

public class MooseEntityRenderState extends LivingEntityRenderState {
    public ItemStack saddleStack;
    public boolean leftAntlerMissing, rightAntlerMissing;

    public MooseEntityRenderState() {
        super();
        saddleStack = ItemStack.EMPTY;
        leftAntlerMissing = false;
        rightAntlerMissing = false;
    }
}
