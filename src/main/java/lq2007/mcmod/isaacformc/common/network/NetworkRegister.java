package lq2007.mcmod.isaacformc.common.network;

import lq2007.mcmod.isaacformc.register.BiObjectConstructor;
import lq2007.mcmod.isaacformc.register.StaticInvoker;
import lq2007.mcmod.isaacformc.register.registers.IRegister;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class NetworkRegister implements IRegister {

    public final SimpleChannel CHANNEL;
    private final List<Class<?>> packetList = new ArrayList<>();
    private int nextId = 0;

    public NetworkRegister(SimpleChannel channel) {
        CHANNEL = channel;
    }

    @Override
    public void cache(ClassLoader classLoader, Type clazz, String className, String packageName, Class<?> aClass) {
        if (inPackage(aClass, "lq2007.mcmod.isaacformc.common.network")
                && BasePacket.class.isAssignableFrom(aClass)
                && isInstantiable(aClass)) {
            packetList.add(aClass);
        }
    }

    @Override
    public void apply() {
        for (Class<?> packet : packetList) {
            BiConsumer encoder = new StaticInvoker(packet, "encode");
            Function decoder = new BiObjectConstructor(packet, PacketBuffer.class);
            BiConsumer consumer = new StaticInvoker(packet, "apply");
            NetSide annotation = packet.getAnnotation(NetSide.class);
            Optional<NetworkDirection> direction = annotation == null ? Optional.empty() : Optional.of(annotation.value());
            CHANNEL.registerMessage(nextId++, packet, encoder, decoder, consumer, direction);
        }
    }
}
