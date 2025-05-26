package xen42.canadamod;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.BoatDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Items;

public class MapleBoatEntity {
    public static final EntityType<BoatEntity> MAPLE_BOAT = 
        EntityType.register("maple_boat", EntityType.Builder.<BoatEntity>create(EntityType.getBoatFactory(() -> Items.CHERRY_BOAT), SpawnGroup.MISC)
            .dropsNothing().dimensions(1.375F, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10));

    public static final EntityType<ChestBoatEntity> MAPLE_CHEST_BOAT = 
        EntityType.register("maple_chest_boat", EntityType.Builder.<ChestBoatEntity>create(EntityType.getChestBoatFactory(() -> Items.CHERRY_CHEST_BOAT), SpawnGroup.MISC)
            .dropsNothing().dimensions(1.375F, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10));
    
    public static void initialize() {
        DispenserBlock.registerBehavior(CanadaItems.MAPLE_CHEST_BOAT, new BoatDispenserBehavior(MAPLE_CHEST_BOAT));
        DispenserBlock.registerBehavior(CanadaItems.MAPLE_BOAT, new BoatDispenserBehavior(EntityType.CHERRY_BOAT));
    }
}
