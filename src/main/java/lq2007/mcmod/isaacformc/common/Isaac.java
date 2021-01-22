package lq2007.mcmod.isaacformc.common;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.forgespi.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static lq2007.mcmod.isaacformc.common.Isaac.ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ID)
public class Isaac {

    public static final String ID = "isaacformc";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public CommonProxy proxy;
    public SimpleChannel network = NetworkRegistry.newSimpleChannel(new ResourceLocation(ID, "network"), () -> "0", v -> true, v -> true);

    public Isaac() {
        if (Environment.get().getDist().isClient()) {
            proxy = new lq2007.mcmod.isaacformc.client.ClientProxy(FMLJavaModLoadingContext.get().getModEventBus(), network);
        } else {
            proxy = new CommonProxy(FMLJavaModLoadingContext.get().getModEventBus(), network);
        }
    }
}