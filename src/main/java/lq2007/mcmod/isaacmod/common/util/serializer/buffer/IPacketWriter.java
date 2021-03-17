package lq2007.mcmod.isaacmod.common.util.serializer.buffer;

import net.minecraft.network.PacketBuffer;

public interface IPacketWriter<T> {

    /**
     * Write data to a buffer.
     *
     * @param buffer buffer
     */
    PacketBuffer write(T item, PacketBuffer buffer);
}
