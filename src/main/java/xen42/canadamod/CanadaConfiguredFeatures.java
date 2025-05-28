package xen42.canadamod;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

public class CanadaConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAPLE_CONFIGURED_FEATURE = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(CanadaMod.MOD_ID, "maple"));
    public static final RegistryKey<PlacedFeature> MAPLE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(CanadaMod.MOD_ID, "maple"));
    
    public static void onInitialize() {

    }
}
