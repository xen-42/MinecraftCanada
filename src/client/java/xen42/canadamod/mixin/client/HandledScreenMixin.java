package xen42.canadamod.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import xen42.canadamod.CanadaItems;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {
    @Inject(at = @At("HEAD"), 
        method = "isItemTooltipSticky",
        cancellable = true)
	private void isItemTooltipSticky(ItemStack item, CallbackInfoReturnable<Boolean> info) {
        if (item.isOf(CanadaItems.THERMOS)) {
            info.setReturnValue(true);
            info.cancel();
        }
	}
}