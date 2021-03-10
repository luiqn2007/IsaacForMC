package lq2007.mcmod.isaacformc.common.util.serializer;

import lq2007.mcmod.isaacformc.common.util.serializer.network.IPacketSerializable;
import lq2007.mcmod.isaacformc.common.util.serializer.network.IPacketSerializer;
import net.minecraft.network.PacketBuffer;

public class DefaultPacketSerializer<T extends IPacketSerializable> extends DefaultPacketReader<T> implements IPacketSerializer<T> {

    public DefaultPacketSerializer(Class<T> aClass) {
        super(aClass);
    }

    @Override
    public PacketBuffer write(T item, PacketBuffer buffer) {
        return DefaultPacketWriter.INSTANCE.write(item, buffer);
    }
}
