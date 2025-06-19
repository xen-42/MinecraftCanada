package xen42.canadamod.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.type.Consumable;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import xen42.canadamod.item.DurabilityFoodItem;

@Mixin(ConsumableComponent.class)
public abstract class ConsumableComponentMixin {
    @Shadow
    public abstract List<ConsumeEffect> onConsumeEffects();

    @Shadow
    public abstract UseAction useAction();

    @Inject(at = @At("HEAD"), method = "finishConsumption", cancellable = true)
    public void finishConsumption(World world, LivingEntity user, ItemStack stack, CallbackInfoReturnable<ItemStack> info) {
        if (stack.getItem() instanceof DurabilityFoodItem) {
            var thisConsumable = (ConsumableComponent)(Object)this;

            var random = user.getRandom();
            thisConsumable.spawnParticlesAndPlaySound(random, user, stack, 16);
            if (user instanceof ServerPlayerEntity serverPlayerEntity) {
                serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            }

            stack.streamAll(Consumable.class).forEach((consumable) -> {
                consumable.onConsume(world, user, stack, thisConsumable);
            });
            if (!world.isClient) {
                this.onConsumeEffects().forEach((effect) -> {
                    effect.onConsume(world, stack, user);
                });
            }

            user.emitGameEvent(this.useAction() == UseAction.DRINK ? GameEvent.DRINK : GameEvent.EAT);
            if (!user.isInCreativeMode() && user instanceof PlayerEntity player) {
                stack.damage(1, player);
            }
            if (stack.getDamage() >= stack.getMaxDamage() - 1) {
                stack.decrementUnlessCreative(1, user);
            }
            info.setReturnValue(stack);
            info.cancel();
        }
    }
}
