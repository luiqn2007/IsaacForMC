package lq2007.mcmod.isaacmod;

import lq2007.mcmod.isaacmod.block.ModBlocks;
import lq2007.mcmod.isaacmod.block.ModTileEntities;
import lq2007.mcmod.isaacmod.entity.ModEntities;
import lq2007.mcmod.isaacmod.item.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static lq2007.mcmod.isaacmod.Isaac.ID;

@Mod(ID)
public class Isaac {

    public static final String ID = "isaacmod";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static Isaac MOD;

    public Isaac() {
        MOD = this;
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::setupClient);
        ModItems.register(eventBus);
        ModEntities.register(eventBus);
        ModBlocks.register(eventBus);
        ModTileEntities.register(eventBus);
    }

    private void setup(FMLCommonSetupEvent event) {
    }

    private void setupClient(FMLClientSetupEvent event) {
        ModTileEntities.REGISTER.registerTers();
        ModEntities.REGISTER.registerRenderers();
    }
}
