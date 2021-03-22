package lq2007.mcmod.isaacmod.common.util.serializer;

import lq2007.mcmod.isaacmod.common.util.serializer.buffer.IPacketReadable;
import lq2007.mcmod.isaacmod.common.util.serializer.buffer.IPacketSerializable;
import lq2007.mcmod.isaacmod.common.util.serializer.buffer.IPacketSerializer;
import lq2007.mcmod.isaacmod.common.util.serializer.buffer.IPacketWriteable;
import lq2007.mcmod.isaacmod.register.registers.IRegister;
import net.minecraft.network.PacketBuffer;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Comparator;

public class ObjectPacketSerializer<T extends IPacketSerializable> implements IPacketSerializer<T> {

    public static final ObjectPacketSerializer INSTANCE = new ObjectPacketSerializer();
    public static final ArrayList<Class<?>> CLASS_LIST = new ArrayList<>();

    @Override
    public T read(PacketBuffer buffer) {
        try {
            Class<T> aClass = (Class<T>) CLASS_LIST.get(buffer.readVarInt());
            T t = aClass.newInstance();
            t.read(buffer);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PacketBuffer write(T item, PacketBuffer buffer) {
        buffer.writeVarInt(CLASS_LIST.indexOf(item.getClass()));
        item.write(buffer);
        return buffer;
    }

    public static class Collector implements IRegister {

        @Override
        public void cache(ClassLoader classLoader, Type clazz, String className, String packageName, Class<?> aClass) {
            if (isInstantiable(aClass) && isExtends(aClass, IPacketReadable.class) && isExtends(aClass, IPacketWriteable.class)) {
                CLASS_LIST.add(aClass);
            }
        }

        @Override
        public void apply() {
            CLASS_LIST.sort(Comparator.comparing(Class::getName));
        }
    }
}
