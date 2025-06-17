package xen42.canadamod;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class CanadaSounds {
    public static final SoundEvent SOUND_BEAVER_AMBIENT = registerSound("beaver_ambient");
    public static final SoundEvent SOUND_BEAVER_DEATH = registerSound("beaver_death");
    public static final SoundEvent SOUND_BEAVER_HURT = registerSound("beaver_hurt");
    public static final SoundEvent SOUND_BEAVER_EAT = registerSound("beaver_eat");
    public static final SoundEvent SOUND_MOOSE_AMBIENT = registerSound("moose_ambient");
    public static final SoundEvent SOUND_MOOSE_ANGRY = registerSound("moose_angry");
    public static final SoundEvent SOUND_MOOSE_DEATH = registerSound("moose_death");
    public static final SoundEvent SOUND_MOOSE_HURT = registerSound("moose_hurt");

    public static SoundEvent registerSound(String name) {
        var id = Identifier.of(CanadaMod.MOD_ID, name);
        var event = SoundEvent.of(id);
        Registry.register(Registries.SOUND_EVENT, id, event);
        return event;
    }

    public static void onInit() {}
}
