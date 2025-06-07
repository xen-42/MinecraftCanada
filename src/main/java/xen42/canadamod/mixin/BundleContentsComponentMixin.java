package xen42.canadamod.mixin;

import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleContentsComponent.class)
public class BundleContentsComponentMixin {
	@Inject(at = @At("HEAD"), method = "getOccupancy", cancellable = true)
	private static void getOccupancy(ItemStack stack, CallbackInfoReturnable<Fraction> info) {
		var res = Fraction.getFraction(1, 64);
		info.setReturnValue(res);
	}
}