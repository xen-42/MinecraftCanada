package xen42.canadamod;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class CanadaEffects {
    public static final RegistryEntry<StatusEffect> BEAVER_EFFECT = registerStatusEffect("beaver_effect",
            (new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x9B7441))
            .addAttributeModifier(EntityAttributes.BLOCK_BREAK_SPEED, Identifier.of(CanadaMod.MOD_ID, "effect.beaver_effect"), 3f, Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(EntityAttributes.SUBMERGED_MINING_SPEED, Identifier.of(CanadaMod.MOD_ID, "effect.beaver_effect"), 2f, Operation.ADD_MULTIPLIED_TOTAL)
        );
    
    public static final RegistryEntry<StatusEffect> MOOSE_EFFECT = registerStatusEffect("moose_effect",
            (new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x6C0822))
            .addAttributeModifier(EntityAttributes.MAX_HEALTH, Identifier.of(CanadaMod.MOD_ID, "effect.moose_effect"), 6.0, Operation.ADD_VALUE)
            .addAttributeModifier(EntityAttributes.KNOCKBACK_RESISTANCE, Identifier.of(CanadaMod.MOD_ID, "effect.moose_effect"), 2f, Operation.ADD_MULTIPLIED_TOTAL)
        );

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(CanadaMod.MOD_ID, name), statusEffect);
    }

    public static void initialize() {
        
    }
}
