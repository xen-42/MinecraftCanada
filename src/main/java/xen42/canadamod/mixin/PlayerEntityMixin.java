package xen42.canadamod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.ItemTags;
import xen42.canadamod.CanadaItems;
import xen42.canadamod.CanadaMod;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow
    protected abstract boolean isEquipped(Item item);

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo info) {
        var player = (PlayerEntity)(Object)this;

        var doBeaver = isEquipped(CanadaItems.BEAVER_HELMET) && player.getMainHandStack().isIn(ItemTags.AXES);
        var doMoose = isEquipped(CanadaItems.MOOSE_HELMET);

        if (doMoose) {
            player.addStatusEffect(new StatusEffectInstance(CanadaMod.MOOSE_EFFECT, 110, 0, false, false, true));
        }
        if (doBeaver) {
            player.addStatusEffect(new StatusEffectInstance(CanadaMod.BEAVER_EFFECT, 110, 0, false, false, true));
        }
    }
}
