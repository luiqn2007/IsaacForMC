package lq2007.mcmod.isaacmod.common;

import lq2007.mcmod.isaacmod.common.capability.CapabilityRegister;
import lq2007.mcmod.isaacmod.common.network.NetworkRegister;
import lq2007.mcmod.isaacmod.common.prop.type.PropRegister;
import lq2007.mcmod.isaacmod.register.Register;
import lq2007.mcmod.isaacmod.register.registers.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static lq2007.mcmod.isaacmod.common.Isaac.ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ID)
public class Isaac {

    public static final String ID = "isaacmod";

    public static Register REGISTER;
    public static BlockRegister BLOCKS;
    public static ItemRegister ITEMS;
    public static BlockItemRegister BLOCK_ITEMS;
    public static TileEntityRegister TILES;
    public static EntityRegister ENTITIES;

    public static CapabilityRegister CAPABILITIES;
    public static NetworkRegister NETWORKS;
    public static PropRegister PROPS;

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
            proxy = new lq2007.mcmod.isaacmod.client.ClientProxy(eventBus);
        } else {
            proxy = new CommonProxy(eventBus);
        }

        REGISTER = new Register();

        BLOCKS = REGISTER.add(new BlockRegister(REGISTER, "lq2007.mcmod.isaacmod"));
        ITEMS = REGISTER.add(new ItemRegister(REGISTER, "lq2007.mcmod.isaacmod"));
        TILES = REGISTER.add(new TileEntityRegister(REGISTER, "lq2007.mcmod.isaacmod", this::getBlocks));
        ENTITIES = REGISTER.add(new EntityRegister(REGISTER, "lq2007.mcmod.isaacmod"));
        BLOCK_ITEMS = REGISTER.add(new BlockItemRegister(ITEMS.register, BLOCKS, block -> new Item.Properties()));
        CAPABILITIES = REGISTER.add(new CapabilityRegister());
        NETWORKS = REGISTER.add(new NetworkRegister(network));
        PROPS = REGISTER.add(new PropRegister());

        REGISTER.execute();
    }

    private Block[] getBlocks(Class<? extends TileEntity> aClass) {
        String tileName = aClass.getSimpleName();
        String blockName = "Block" + tileName.substring(4 /* Tile */);
        try {
            String blockClassName = "lq2007.mcmod.isaacmod.common.block." + blockName;
            Class<?> blockType = aClass.getClassLoader().loadClass(blockClassName);
            return new Block[] { BLOCKS.get((Class<? extends Block>) blockType) };
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
