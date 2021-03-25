package lq2007.mcmod.isaacmod;

import lq2007.mcmod.isaacmod.common.CommonProxy;
import lq2007.mcmod.isaacmod.common.capability.CapabilityRegister;
import lq2007.mcmod.isaacmod.common.command.CommandRegister;
import lq2007.mcmod.isaacmod.common.network.NetworkRegister;
import lq2007.mcmod.isaacmod.common.prop.type.PropItemRegister;
import lq2007.mcmod.isaacmod.common.prop.type.PropRegister;
import lq2007.mcmod.isaacmod.common.util.CollectionUtils;
import lq2007.mcmod.isaacmod.common.util.ReflectionUtil;
import lq2007.mcmod.isaacmod.common.util.serializer.ObjectPacketSerializer;
import lq2007.mcmod.isaacmod.common.item.ItemIcon;
import lq2007.mcmod.isaacmod.register.Register;
import lq2007.mcmod.isaacmod.register.registers.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
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
    public static PropItemRegister PROP_ITEMS;
    public static CommandRegister COMMANDS;

    public static ObjectPacketSerializer.Collector C;

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static Isaac MOD;

    public final CommonProxy proxy;
    public final IEventBus eventBus;

    public Isaac() {
        MOD = this;

        ReflectionUtil.setSkipClass("lq2007.mcmod.isaacmod.coremod.mixin");

        REGISTER = new Register();
        SimpleChannel network = NetworkRegistry.newSimpleChannel(new ResourceLocation(ID, "network"), () -> "0", v -> true, v -> true);

        BLOCKS = REGISTER.add(new BlockRegister(REGISTER));
        ITEMS = REGISTER.add(new ItemRegister(REGISTER));
        BLOCK_ITEMS = BLOCKS.withItem(ITEMS.register, this::asItem);
        TILES = REGISTER.add(new TileEntityRegister(REGISTER, this::getBlocks, this::asTerClass));
        ENTITIES = REGISTER.add(new EntityRegister(REGISTER)).withRender(this::getRenderClass);
        CAPABILITIES = REGISTER.add(new CapabilityRegister());
        NETWORKS = REGISTER.add(new NetworkRegister(network));
        PROPS = REGISTER.add(new PropRegister());
        PROP_ITEMS = PROPS.withItem(ITEMS.register);
        COMMANDS = REGISTER.add(new CommandRegister());

        C = REGISTER.add(new ObjectPacketSerializer.Collector());

        eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        if (FMLEnvironment.dist.isClient()) {
            proxy = new lq2007.mcmod.isaacmod.client.ClientProxy(eventBus);
        } else {
            proxy = new CommonProxy(eventBus);
        }

        REGISTER.execute();
    }

    private BlockItem asItem(Block block, String name) {
        return new BlockItem(block, new Item.Properties().group(ItemIcon.group()));
    }

    private Block[] getBlocks(Class<? extends TileEntity> aClass) {
        String tileName = aClass.getSimpleName();
        String blockName = "Block" + tileName.substring(4 /* Tile */);
        String blockClassName = "lq2007.mcmod.isaacmod.common.block." + blockName;
        Class<? extends Block> blockType = ReflectionUtil.loadClass(blockClassName);
        return CollectionUtils.asArray(Block.class, BLOCKS.getOrNull(blockType));
    }

    private String asTerClass(Class<? extends TileEntity> aClass, TileEntityType<?> tileEntityType) {
        return "lq2007.mcmod.isaacmod.client.ter.Ter" + aClass.getSimpleName().substring(4);
    }

    private String getRenderClass(Class<? extends Entity> entityClass) {
        return "lq2007.mcmod.isaacmod.client.render.Render" + entityClass.getSimpleName().substring(6);
    }
}
