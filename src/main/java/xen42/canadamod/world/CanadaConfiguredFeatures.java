package xen42.canadamod.world;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import xen42.canadamod.CanadaMod;

public class CanadaConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAPLE_CONFIGURED_FEATURE = registerKey("maple");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAPLE_FOREST_VEGETATION = registerKey("maple_forest_vegetation");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FALLEN_MAPLE_TREE = registerKey("fallen_maple_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAPLE_LEAF_LITTER = registerKey("maple_leaf_litter");
    
    private static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(CanadaMod.MOD_ID, name));
    }

    public static void onInitialize() {

    }
}
