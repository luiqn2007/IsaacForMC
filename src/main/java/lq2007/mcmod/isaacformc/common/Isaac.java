package lq2007.mcmod.isaacformc.common;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import static lq2007.mcmod.isaacformc.common.Isaac.ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ID)
public class Isaac {

    public static final String ID = "isaacformc";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static Isaac MOD;

    public final CommonProxy proxy;
    public final IEventBus eventBus;

    public Isaac() {
        // mixin
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.isaacformc.refmap.json");
        // mod
        MOD = this;
        eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        SimpleChannel network = NetworkRegistry.newSimpleChannel(new ResourceLocation(ID, "network"), () -> "0", v -> true, v -> true);
        if (FMLEnvironment.dist.isClient()) {
            proxy = new lq2007.mcmod.isaacformc.client.ClientProxy(eventBus, network);
        } else {
            proxy = new CommonProxy(eventBus, network);
        }
    }
}
