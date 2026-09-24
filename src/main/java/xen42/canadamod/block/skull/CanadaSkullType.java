package xen42.canadamod.block.skull;

import net.minecraft.block.SkullBlock;

public enum CanadaSkullType implements SkullBlock.SkullType {
	MOOSE("moose");

	private final String id;

	private CanadaSkullType(final String id) {
		this.id = id;
		TYPES.put(id, this);
	}

	@Override
	public String asString() {
		return this.id;
	}
}
