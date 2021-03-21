package lq2007.mcmod.isaacmod;

import lq2007.mcmod.isaacmod.common.CommonProxy;
import lq2007.mcmod.isaacmod.common.capability.CapabilityRegister;
import lq2007.mcmod.isaacmod.common.command.CommandRegister;
import lq2007.mcmod.isaacmod.common.network.NetworkRegister;
import lq2007.mcmod.isaacmod.common.prop.type.PropRegister;
import lq2007.mcmod.isaacmod.item.ItemIcon;
import lq2007.mcmod.isaacmod.register.Register;
import lq2007.mcmod.isaacmod.register.registers.*;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static lq2007.mcmod.isaacmod.Isaac.ID;

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
    public static CommandRegister COMMANDS = new CommandRegister();

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static Isaac MOD;

    public final CommonProxy proxy;
    public final IEventBus eventBus;

    public Isaac() {
        MOD = this;

        REGISTER = new Register();
        SimpleChannel network = NetworkRegistry.newSimpleChannel(new ResourceLocation(ID, "network"), () -> "0", v -> true, v -> true);

        BLOCKS = REGISTER.add(new BlockRegister(REGISTER));
        ITEMS = REGISTER.add(new ItemRegister(REGISTER));
        BLOCK_ITEMS = BLOCKS.withItem(ITEMS.register, this::asItem);
        TILES = REGISTER.add(new TileEntityRegister(REGISTER, this::getBlocks, this::asTerClass));
        ENTITIES = REGISTER.add(new EntityRegister(REGISTER));
        CAPABILITIES = REGISTER.add(new CapabilityRegister());
        NETWORKS = REGISTER.add(new NetworkRegister(network));
        PROPS = REGISTER.add(new PropRegister());
        COMMANDS = REGISTER.add(new CommandRegister());

        eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        if (FMLEnvironment.dist.isClient()) {
            proxy = new lq2007.mcmod.isaacmod.client.ClientProxy(eventBus);
        } else {
            proxy = new CommonProxy(eventBus);
        }

        REGISTER.execute();
    }

    private String asTerClass(Class<? extends TileEntity> aClass, TileEntityType<?> tileEntityType) {
        return "lq2007.mcmod.isaacmod.client.ter.Ter" + aClass.getSimpleName().substring(4);
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

    private BlockItem asItem(Block block, String name) {
        return new BlockItem(block, new Item.Properties().group(ItemIcon.group()));
    }
}
