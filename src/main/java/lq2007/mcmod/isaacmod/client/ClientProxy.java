package lq2007.mcmod.isaacmod.client;

import lq2007.mcmod.isaacmod.common.CommonProxy;
import lq2007.mcmod.isaacmod.common.Isaac;
import net.minecraft.entity.Entity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {

    public ClientProxy(IEventBus eventBus) {
        super(eventBus);
        eventBus.addListener(this::setupClient);
    }

    public void setupClient(FMLClientSetupEvent event) {
        Isaac.ENTITIES.asRender(this::getRenderClass).apply();
    }

    private String getRenderClass(Class<? extends Entity> entityClass) {
        return "lq2007.mcmod.isaacmod.client.render.Render" + entityClass.getSimpleName().substring(6);
    }
}
