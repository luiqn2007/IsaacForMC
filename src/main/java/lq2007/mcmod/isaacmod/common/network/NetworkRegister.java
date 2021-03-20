package lq2007.mcmod.isaacmod.common.network;

import lq2007.mcmod.isaacmod.register.registers.IRegister;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetworkRegister implements IRegister {

    public static final Logger LOGGER = LogManager.getLogger();

    public final SimpleChannel CHANNEL;
    private final List<Class<?>> packetList = new ArrayList<>();
    private int nextId = 0;

    public NetworkRegister(SimpleChannel channel) {
        CHANNEL = channel;
    }

    @Override
    public void cache(ClassLoader classLoader, Type clazz, String className, String packageName, Class<?> aClass) {
        if (isPackage(packageName, "lq2007.mcmod.isaacmod.common.network")
                && isExtends(aClass, BasePacket.class) && isInstantiable(aClass)) {
            LOGGER.warn("\tCached as Network");
            packetList.add(aClass);
        }
    }

    @Override
    public void apply() {
        int count = 0;
        LOGGER.warn("Network apply begin");
        for (Class<?> packet : packetList) {
            LOGGER.warn("\tRegister {}", packet);
            BiConsumer encoder = new NetRegConsumer(packet, "encode", PacketBuffer.class);
            Function decoder = new NetRegConstructor(packet);
            BiConsumer consumer = new NetRegConsumer(packet, "apply", Supplier.class);
            NetSide annotation = packet.getAnnotation(NetSide.class);
            Optional<NetworkDirection> direction = annotation == null ? Optional.empty() : Optional.of(annotation.value());
            CHANNEL.registerMessage(nextId++, packet, encoder, decoder, consumer, direction);
            count++;
        }
        LOGGER.warn("Network apply end, total {}", count);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface NetSide {
        NetworkDirection value();
    }
}
