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
			new StatusEffectInstance(StatusEffects.SLOW_FALLING, 600),
			new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 600),
			new StatusEffectInstance(StatusEffects.WEAKNESS, 600, 1)
        )
    );

    public static final RegistryEntry<Potion> LONG_DUCK_MASTER = registerPotion(
        "long_duck_master",
        new Potion(
            "duck_master",
			new StatusEffectInstance(StatusEffects.SLOW_FALLING, 1200),
			new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 1200),
			new StatusEffectInstance(StatusEffects.WEAKNESS, 1200, 1)
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
		});
    }
}
