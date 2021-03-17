package lq2007.mcmod.isaacmod.common.util.serializer.buffer;

import net.minecraft.network.PacketBuffer;

public interface IPacketReader<T> {


    /**
     * Write data to a buffer.
     *
     * @param buffer buffer
     */
    T read(PacketBuffer buffer);
}
