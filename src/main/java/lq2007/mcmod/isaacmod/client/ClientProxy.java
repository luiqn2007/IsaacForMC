package lq2007.mcmod.isaacmod.client;

import lq2007.mcmod.isaacmod.client.render.Renders;
import lq2007.mcmod.isaacmod.common.CommonProxy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {

    public ClientProxy(IEventBus eventBus) {
        super(eventBus);
        eventBus.addListener(this::setupClient);
    }

    public void setupClient(FMLClientSetupEvent event) {
        Renders.register();
    }
}
