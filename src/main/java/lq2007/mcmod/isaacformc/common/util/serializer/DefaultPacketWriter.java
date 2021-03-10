package lq2007.mcmod.isaacformc.common.util.serializer;

import lq2007.mcmod.isaacformc.common.util.serializer.network.IPacketWriteable;
import lq2007.mcmod.isaacformc.common.util.serializer.network.IPacketWriter;
import net.minecraft.network.PacketBuffer;

public class DefaultPacketWriter<T extends IPacketWriteable> implements IPacketWriter<T> {

    public static final DefaultPacketWriter INSTANCE = new DefaultPacketWriter<>();

    @Override
    public PacketBuffer write(T item, PacketBuffer buffer) {
        item.write(buffer);
        return buffer;
    }
}
