package xen42.canadamod;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class CanadaPotions {
    public static final RegistryEntry<Potion> DUCK_MASTER = registerPotion(
        "duck_master",
        new Potion(
            "duck_master",
            new StatusEffectInstance(CanadaEffects.DUCK_MASTER, 400), 
            new StatusEffectInstance(StatusEffects.SLOW_FALLING, 400),
            new StatusEffectInstance(StatusEffects.SPEED, 400, 1),
            new StatusEffectInstance(StatusEffects.WEAKNESS, 400, 1)
        )
    );

    public static final RegistryEntry<Potion> LONG_DUCK_MASTER = registerPotion(
        "long_duck_master",
        new Potion(
            "duck_master",
            new StatusEffectInstance(CanadaEffects.DUCK_MASTER, 800), 
            new StatusEffectInstance(StatusEffects.SLOW_FALLING, 800),
            new StatusEffectInstance(StatusEffects.SPEED, 800, 1),
            new StatusEffectInstance(StatusEffects.WEAKNESS, 800, 1)
        )
    );

    public static final RegistryEntry<Potion> STRONG_DUCK_MASTER = registerPotion(
        "strong_duck_master",
        new Potion(
            "duck_master",
            new StatusEffectInstance(CanadaEffects.DUCK_MASTER, 400), 
            new StatusEffectInstance(StatusEffects.SLOW_FALLING, 400),
            new StatusEffectInstance(StatusEffects.SPEED, 400, 2),
            new StatusEffectInstance(StatusEffects.WEAKNESS, 400, 2)
        )
    );

    @SuppressWarnings("unused")
	private static RegistryEntry<Potion> registerPotion(String name, Potion potion) {
        return Registry.registerReference(Registries.POTION, Identifier.of(CanadaMod.MOD_ID, name), potion);
    }

    public static void initialize() {
		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(Potions.AWKWARD, CanadaItems.WATERFOWL, CanadaPotions.DUCK_MASTER);
			builder.registerPotionRecipe(CanadaPotions.DUCK_MASTER, Items.REDSTONE, CanadaPotions.LONG_DUCK_MASTER);
			builder.registerPotionRecipe(CanadaPotions.DUCK_MASTER, Items.GLOWSTONE_DUST, CanadaPotions.STRONG_DUCK_MASTER);
		});
    }
}
