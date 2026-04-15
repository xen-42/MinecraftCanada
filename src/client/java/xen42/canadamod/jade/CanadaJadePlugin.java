package xen42.canadamod.jade;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

import xen42.canadamod.CanadaMod;
import xen42.canadamod.entities.BeaverEntity;

@WailaPlugin(CanadaMod.MOD_ID)
public class CanadaJadePlugin implements IWailaPlugin {
	public CanadaJadePlugin() {
		CanadaMod.LOGGER.info("Creating Jade plugin");
	}
	
	@Override
	public void registerClient(IWailaClientRegistration registration) {
		CanadaMod.LOGGER.info("Registering client components");
		registration.registerEntityComponent(BeaverChopProvider.INSTANCE, BeaverEntity.class);
	}
}