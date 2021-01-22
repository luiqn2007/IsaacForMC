package lq2007.mcmod.isaacformc.common.network;

import net.minecraft.network.PacketBuffer;

public interface IPacketWriter {

    /**
     * Read data from a buffer.
     *
     * @param buffer buffer
     */
    void write(PacketBuffer buffer);
}
