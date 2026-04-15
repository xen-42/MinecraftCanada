package xen42.canadamod;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class CanadaSounds {
	private static final Map<SoundEvent, RegistryEntry.Reference<SoundEvent>> REFERENCES = new HashMap<>();

	public static final SoundEvent SOUND_BEAVER_AMBIENT = registerSound("entity.beaver.ambient");
	public static final SoundEvent SOUND_BEAVER_DEATH = registerSound("entity.beaver.death");
	public static final SoundEvent SOUND_BEAVER_HURT = registerSound("entity.beaver.hurt");
	public static final SoundEvent SOUND_BEAVER_EAT = registerSound("entity.beaver.eat");

	public static final SoundEvent SOUND_MOOSE_AMBIENT = registerSound("entity.moose.ambient");
	public static final SoundEvent SOUND_MOOSE_ANGRY = registerSound("entity.moose.angry");
	public static final SoundEvent SOUND_MOOSE_DEATH = registerSound("entity.moose.death");
	public static final SoundEvent SOUND_MOOSE_HURT = registerSound("entity.moose.hurt");
	public static final SoundEvent SOUND_MOOSE_GROW_ANTLER = registerSound("entity.moose.grow_antler");
	public static final SoundEvent SOUND_MOOSE_STRIP_ANTLER = registerSound("entity.moose.strip_antler");
	public static final SoundEvent SOUND_MOOSE_NOTE_BLOCK_IMITATE = registerSound("entity.moose.note_block_imitate");

	public static final SoundEvent ENTITY_GRIZZLY_BEAR_AMBIENT = registerSound("entity.grizzly.ambient");
	public static final SoundEvent ENTITY_GRIZZLY_BEAR_AMBIENT_BABY = registerSound("entity.grizzly.ambient_baby");
	public static final SoundEvent ENTITY_GRIZZLY_BEAR_DEATH = registerSound("entity.grizzly.death");
	public static final SoundEvent ENTITY_GRIZZLY_BEAR_HURT = registerSound("entity.grizzly.hurt");
	public static final SoundEvent ENTITY_GRIZZLY_BEAR_WARNING = registerSound("entity.grizzly.warning");

	public static final SoundEvent SOUND_DUCK_AMBIENT = registerSound("entity.duck.ambient");
	public static final SoundEvent SOUND_DUCK_HURT = registerSound("entity.duck.hurt");
	public static final SoundEvent SOUND_DUCK_DEATH = registerSound("entity.duck.death");
	public static final SoundEvent SOUND_DUCK_FOOTSTEP = registerSound("entity.duck.footstep");
	public static final SoundEvent SOUND_DUCK_PLOP = registerSound("entity.duck.plop");

	public static final SoundEvent SOUND_GOOSE_AMBIENT = registerSound("entity.goose.ambient");
	public static final SoundEvent SOUND_GOOSE_HURT = registerSound("entity.goose.hurt");
	public static final SoundEvent SOUND_GOOSE_DEATH = registerSound("entity.goose.death");
	public static final SoundEvent SOUND_GOOSE_WARNING = registerSound("entity.goose.warning");
	public static final SoundEvent SOUND_GOOSE_PLOP = registerSound("entity.goose.plop");

	public static SoundEvent registerSound(String name) {
		var id = Identifier.of(CanadaMod.MOD_ID, name);
		var event = SoundEvent.of(id);
		var reference = Registry.registerReference(Registries.SOUND_EVENT, id, event);
		REFERENCES.put(event, reference);
		return event;
	}

	public static RegistryEntry.Reference<SoundEvent> getReference(SoundEvent event) {
		return REFERENCES.get(event);
	}

	public static void onInit() {}
}
