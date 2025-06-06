package xen42.canadamod.entity;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.item.ItemStack;

public class MooseEntityRenderState extends LivingEntityRenderState {
    public ItemStack saddleStack;

    public MooseEntityRenderState() {
        super();
        saddleStack = ItemStack.EMPTY;
    }
}
