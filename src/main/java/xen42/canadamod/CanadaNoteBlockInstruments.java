package xen42.canadamod;

import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.StringIdentifiable;

public enum CanadaNoteBlockInstruments implements StringIdentifiable {
	MOOSE("moose", CanadaSounds.getReference(CanadaSounds.SOUND_MOOSE_NOTE_BLOCK_IMITATE), NoteBlockInstrument.Type.MOB_HEAD);

	private final String name;
	private final RegistryEntry<SoundEvent> sound;
	private final NoteBlockInstrument.Type type;

	CanadaNoteBlockInstruments(String name, RegistryEntry<SoundEvent> sound, NoteBlockInstrument.Type type) {
		this.name = name;
		this.sound = sound;
		this.type = type;
	}

	@Override
	public String asString() {
		return this.name;
	}

	public RegistryEntry<SoundEvent> getSound() {
		return sound;
	}

	public NoteBlockInstrument.Type getType() {
		return type;
	}

	public NoteBlockInstrument get() {
		return NoteBlockInstrument.valueOf(this.name());
	}
}