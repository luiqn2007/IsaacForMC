package lq2007.mcmod.isaacformc.common;

import lq2007.mcmod.isaacformc.common.capability.CapabilityRegister;
import lq2007.mcmod.isaacformc.register.Register;
import lq2007.mcmod.isaacformc.register.registers.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static lq2007.mcmod.isaacformc.common.Isaac.ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ID)
public class Isaac {

    public static final String ID = "isaacformc";

    public static Register REGISTER;
    public static BlockRegister BLOCKS;
    public static ItemRegister ITEMS;
    public static TileEntityRegister TILES;
    public static EntityRegister ENTITIES;

    public static CapabilityRegister CAPABILITIES;

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static Isaac MOD;

    public final CommonProxy proxy;
    public final IEventBus eventBus;

    public Isaac() {
        MOD = this;
        eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        SimpleChannel network = NetworkRegistry.newSimpleChannel(new ResourceLocation(ID, "network"), () -> "0", v -> true, v -> true);
        if (FMLEnvironment.dist.isClient()) {
            proxy = new lq2007.mcmod.isaacformc.client.ClientProxy(eventBus, network);
        } else {
            proxy = new CommonProxy(eventBus, network);
        }

        REGISTER = new Register();
        BLOCKS = REGISTER.add(new BlockRegister(REGISTER, "lq2007.mcmod.isaacformc"));
        ITEMS = REGISTER.add(new ItemRegister(REGISTER, "lq2007.mcmod.isaacformc"));
        TILES = REGISTER.add(new TileEntityRegister(REGISTER, "lq2007.mcmod.isaacformc", aClass -> {
            String tileName = aClass.getSimpleName();
            String blockName = "Block" + tileName.substring(4 /* Tile */);
            try {
                Class<? extends Block> blockType =
                        (Class<? extends Block>) aClass.getClassLoader().loadClass("lq2007.mcmod.isaacformc.common.block." + blockName);
                return new Block[] { BLOCKS.get(blockType) };
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }));
        ENTITIES = REGISTER.add(new EntityRegister(REGISTER, "lq2007.mcmod.isaacformc"));
        REGISTER.add(new BlockItemRegister(ITEMS.register, BLOCKS, block -> new Item.Properties()));
        CAPABILITIES = REGISTER.add(new CapabilityRegister());

        REGISTER.execute();
    }
}
