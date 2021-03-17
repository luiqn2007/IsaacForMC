package lq2007.mcmod.isaacmod.common;

import lq2007.mcmod.isaacmod.common.util.serializer.ObjectPacketSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class CommonProxy {

    protected IEventBus eventBus;
    protected SimpleChannel channel;

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
        Isaac.CAPABILITIES.apply();
        Isaac.NETWORKS.apply();
    }

    private void constructMod(FMLConstructModEvent event) {
        ObjectPacketSerializer.collectClass();
    }

    private void setupServer(FMLDedicatedServerSetupEvent event) {}

    private void loadComplete(FMLLoadCompleteEvent event) { }

    private void gatherData(GatherDataEvent event) {}

    private void imcEnqueue(InterModEnqueueEvent event) {}

    private void imcProcess(InterModProcessEvent event) {}
}
