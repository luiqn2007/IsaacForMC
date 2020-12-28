package lq2007.mcmod.isaacformc.common;

import lq2007.mcmod.isaacformc.common.capability.IIsaacData;
import lq2007.mcmod.isaacformc.common.capability.IsaacData;
import lq2007.mcmod.isaacformc.common.capability.IsaacDataStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.*;

public class CommonProxy {

    protected IEventBus eventBus;

    public CommonProxy(IEventBus eventBus) {
        this.eventBus = eventBus;

        eventBus.addListener(this::setup);
        eventBus.addListener(this::constructMod);
        eventBus.addListener(this::setupServer);
        eventBus.addListener(this::loadComplete);
        eventBus.addListener(this::gatherData);
        eventBus.addListener(this::imcEnqueue);
        eventBus.addListener(this::imcProcess);
    }

    private void setup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IIsaacData.class, IsaacDataStorage.INSTANCE, IsaacData::new);
    }

    private void constructMod(FMLConstructModEvent event) {}

    private void setupServer(FMLDedicatedServerSetupEvent event) {}

    private void loadComplete(FMLLoadCompleteEvent event) {}

    private void gatherData(GatherDataEvent event) {}

    private void imcEnqueue(InterModEnqueueEvent event) {}

    private void imcProcess(InterModProcessEvent event) {}
}
