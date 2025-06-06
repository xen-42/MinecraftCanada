package xen42.canadamod.entity;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.item.ItemStack;

public class MooseEntityRenderState extends LivingEntityRenderState {
    // Hardcoded that this is synced server to client
    public ItemStack saddleStack;

    public MooseEntityRenderState() {
        super();
        saddleStack = ItemStack.EMPTY;
    }
}
