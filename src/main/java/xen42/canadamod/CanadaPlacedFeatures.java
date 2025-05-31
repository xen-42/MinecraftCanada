package xen42.canadamod;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;

public class CanadaPlacedFeatures {

    public static final RegistryKey<PlacedFeature> MAPLE_PLACED_KEY = registerKey("maple");

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(CanadaMod.MOD_ID, name));
    }

    public static void onInitialize() {
        
    }

}
