package lq2007.mcmod.isaacformc.common;

import lq2007.mcmod.isaacformc.Isaac;
import lq2007.mcmod.isaacformc.common.capability.IIsaacData;
import lq2007.mcmod.isaacformc.common.capability.IsaacData;
import lq2007.mcmod.isaacformc.common.capability.IsaacDataStorage;
import lq2007.mcmod.isaacformc.common.network.PacketFoundation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.network.NetworkDirection;

import java.util.Optional;

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
        Isaac.MOD.network.registerMessage(0, PacketFoundation.class, PacketFoundation::encode, PacketFoundation::new, PacketFoundation::apply, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    private void constructMod(FMLConstructModEvent event) {}

    private void setupServer(FMLDedicatedServerSetupEvent event) {}

    private void loadComplete(FMLLoadCompleteEvent event) {}

    private void gatherData(GatherDataEvent event) {}

    private void imcEnqueue(InterModEnqueueEvent event) {}

    private void imcProcess(InterModProcessEvent event) {}
}
