package xen42.canadamod;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;

public class CanadaPlacedFeatures {

    public static final RegistryKey<PlacedFeature> MAPLE_CHECKED = registerKey("maple_checked");
    public static final RegistryKey<PlacedFeature> MAPLE_FOREST_VEGETATION = registerKey("maple_forest_vegetation");
    public static final RegistryKey<PlacedFeature> MAPLE_LEAF_LITTER = registerKey("maple_leaf_litter");
    public static final RegistryKey<PlacedFeature> FALLEN_MAPLE_TREE = registerKey("fallen_maple_tree");

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(CanadaMod.MOD_ID, name));
    }

    public static void onInitialize() {
        
    }

}
