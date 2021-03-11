package lq2007.mcmod.isaacformc.common;

import lq2007.mcmod.isaacformc.common.block.Blocks;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.entity.Entities;
import lq2007.mcmod.isaacformc.common.network.IsaacNetworks;
import lq2007.mcmod.isaacformc.common.util.serializer.ObjectPacketSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class CommonProxy {

    protected IEventBus eventBus;
    protected SimpleChannel channel;

    public CommonProxy(IEventBus eventBus, SimpleChannel channel) {
        this.eventBus = eventBus;
        this.channel = channel;

        eventBus.addListener(this::setup);
        eventBus.addListener(this::constructMod);
        eventBus.addListener(this::setupServer);
        eventBus.addListener(this::loadComplete);
        eventBus.addListener(this::gatherData);
        eventBus.addListener(this::imcEnqueue);
        eventBus.addListener(this::imcProcess);

        Blocks.register(eventBus);
        Entities.register(eventBus);
    }

    private void setup(FMLCommonSetupEvent event) {
        IsaacCapabilities.registerAll();
        IsaacNetworks.registerAll(channel);
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
