package xen42.canadamod;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class CanadaConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAPLE_CONFIGURED_FEATURE = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(CanadaMod.MOD_ID, "maple"));
    
    public static void onInitialize() {

    }
}
