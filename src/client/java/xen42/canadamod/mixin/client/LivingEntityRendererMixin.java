package xen42.canadamod.mixin.client;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xen42.canadamod.CanadaItems;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
	@Inject(
		method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V",
		at = @At("TAIL")
	)
	private void canadamod$disableMooseSkullRenderer(
		LivingEntity entity,
		LivingEntityRenderState state,
		float tickDelta,
		CallbackInfo ci
	) {
		if (entity.getEquippedStack(EquipmentSlot.HEAD).isOf(CanadaItems.MOOSE_HEAD)) {
			state.wearingSkullType = null;
			state.wearingSkullProfile = null;
		}
	}
}