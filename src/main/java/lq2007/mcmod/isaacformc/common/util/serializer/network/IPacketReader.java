package lq2007.mcmod.isaacformc.common.util.serializer.network;

import net.minecraft.network.PacketBuffer;

public interface IPacketReader<T> {


    /**
     * Write data to a buffer.
     *
     * @param buffer buffer
     */
    T read(PacketBuffer buffer);
}
