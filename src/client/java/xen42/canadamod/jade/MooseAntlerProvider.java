package xen42.canadamod.jade;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.entities.MooseEntity;

public enum MooseAntlerProvider implements IEntityComponentProvider {
	INSTANCE;

	public static final String ANTLERS_KEY = "jade.moose.antlers";
	public static final String LEFT_SHED_KEY = "jade.moose.antlers.left.shed";
	public static final String RIGHT_SHED_KEY = "jade.moose.antlers.right.shed";
	public static final String LEFT_REGROW_KEY = "jade.moose.antlers.left.regrow";
	public static final String RIGHT_REGROW_KEY = "jade.moose.antlers.right.regrow";

	@Override
	public Identifier getUid() {
		return CanadaMod.MOOSE_ENTITY_ID;
	}

	@Override
	public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
		var entity = accessor.getEntity();

		if (entity instanceof MooseEntity moose) {
			if (moose.canHaveAntlers()) {
				int antlers = 0;

				if (!moose.isLeftAntlerMissing()) {
					antlers++;
				}

				if (!moose.isRightAntlerMissing()) {
					antlers++;
				}

				tooltip.add(Text.translatable(ANTLERS_KEY, antlers));

				tooltip.add(Text.translatable(
					moose.isLeftAntlerMissing() ? LEFT_REGROW_KEY : LEFT_SHED_KEY,
					IThemeHelper.get().seconds(moose.getLeftAntlerTimer(), accessor.tickRate())
				));

				tooltip.add(Text.translatable(
					moose.isRightAntlerMissing() ? RIGHT_REGROW_KEY : RIGHT_SHED_KEY,
					IThemeHelper.get().seconds(moose.getRightAntlerTimer(), accessor.tickRate())
				));
			}
		}
	}
}