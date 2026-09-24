package xen42.canadamod.jade;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.entities.BeaverEntity;

public enum BeaverChopProvider implements IEntityComponentProvider {
	INSTANCE;

	public static final String FATIGUE_KEY = "jade.beaver.chop.fatigue.time";
	public static final String FRENZY_KEY = "jade.beaver.chop.frenzy.time";

	@Override
	public Identifier getUid() {
		return CanadaMod.BEAVER_ENTITY_ID;
	}

	@Override
	public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
		var entity = accessor.getEntity();
		if (entity instanceof BeaverEntity beaver) {
			int frenzyTime = beaver.getFrenzyAge();
			if (frenzyTime > 0) {
				tooltip.add(Text.translatable(
						FRENZY_KEY,
						IThemeHelper.get().seconds(frenzyTime, accessor.tickRate())));
			}
			else {
				// Fatigue is only relevant when no frenzy
				int fatigueTime = beaver.getFatigueAge();
				if (fatigueTime > 0) {
					tooltip.add(Text.translatable(
							FATIGUE_KEY,
							IThemeHelper.get().seconds(fatigueTime, accessor.tickRate())));
				}
			}
		}
	}
}
