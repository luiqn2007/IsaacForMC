package lq2007.mcmod.isaacformc.client;

import lq2007.mcmod.isaacformc.client.render.Renders;
import lq2007.mcmod.isaacformc.common.CommonProxy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ClientProxy extends CommonProxy {

    public ClientProxy(IEventBus eventBus) {
        super(eventBus);
        eventBus.addListener(this::setupClient);
    }

    public void setupClient(FMLClientSetupEvent event) {
        Renders.register();
    }
}
