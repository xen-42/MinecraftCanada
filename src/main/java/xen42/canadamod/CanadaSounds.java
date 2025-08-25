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
    public static final SoundEvent SOUND_MOOSE_GROW_ANTLER = registerSound("moose_grow_antler");
    public static final SoundEvent SOUND_MOOSE_STRIP_ANTLER = registerSound("moose_strip_antler");

    public static final SoundEvent ENTITY_GRIZZLY_BEAR_AMBIENT = registerSound("grizzly_bear_ambient");
	public static final SoundEvent ENTITY_GRIZZLY_BEAR_AMBIENT_BABY = registerSound("grizzly_bear_ambient_baby");
	public static final SoundEvent ENTITY_GRIZZLY_BEAR_DEATH = registerSound("grizzly_bear_death");
	public static final SoundEvent ENTITY_GRIZZLY_BEAR_HURT = registerSound("grizzly_bear_hurt");
	public static final SoundEvent ENTITY_GRIZZLY_BEAR_WARNING = registerSound("grizzly_bear_warning");

	public static final SoundEvent SOUND_DUCK_AMBIENT = registerSound("duck_ambient");
	public static final SoundEvent SOUND_DUCK_HURT = registerSound("duck_hurt");
	public static final SoundEvent SOUND_DUCK_DEATH = registerSound("duck_death");
	public static final SoundEvent SOUND_DUCK_FOOTSTEP = registerSound("duck_footstep");
	public static final SoundEvent SOUND_DUCK_PLOP = registerSound("duck_plop");

	public static final SoundEvent SOUND_GOOSE_AMBIENT = registerSound("goose_ambient");
	public static final SoundEvent SOUND_GOOSE_HURT = registerSound("goose_hurt");
	public static final SoundEvent SOUND_GOOSE_DEATH = registerSound("goose_death");
	public static final SoundEvent SOUND_GOOSE_WARNING = registerSound("goose_warning");
	public static final SoundEvent SOUND_GOOSE_PLOP = registerSound("goose_plop");

    public static SoundEvent registerSound(String name) {
        var id = Identifier.of(CanadaMod.MOD_ID, name);
        var event = SoundEvent.of(id);
        Registry.register(Registries.SOUND_EVENT, id, event);
        return event;
    }

    public static void onInit() {}
}
