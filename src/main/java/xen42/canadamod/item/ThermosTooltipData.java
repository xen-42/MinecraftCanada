package xen42.canadamod.item;

import net.minecraft.item.tooltip.TooltipData;

public record ThermosTooltipData(ThermosContentsComponent contents) implements TooltipData {
    public ThermosTooltipData(ThermosContentsComponent contents) {
        this.contents = contents;
    }

    public ThermosContentsComponent contents() {
        return this.contents;
    }
}
