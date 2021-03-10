package lq2007.mcmod.isaacformc.common.util.serializer;

import lq2007.mcmod.isaacformc.common.util.serializer.network.IPacketReadable;
import lq2007.mcmod.isaacformc.common.util.serializer.network.IPacketReader;
import net.minecraft.network.PacketBuffer;

public class DefaultPacketReader<T extends IPacketReadable> implements IPacketReader<T> {

    private final Class<T> aClass;

    public DefaultPacketReader(Class<T> aClass) {
        this.aClass = aClass;
    }

    @Override
    public T read(PacketBuffer buffer) {
        try {
            T t = aClass.newInstance();
            t.read(buffer);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
