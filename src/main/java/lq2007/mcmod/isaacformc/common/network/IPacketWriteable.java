package lq2007.mcmod.isaacformc.common.network;

import net.minecraft.network.PacketBuffer;

public interface IPacketWriteable {

    /**
     * Read data from a buffer.
     *
     * @param buffer buffer
     */
    void write(PacketBuffer buffer);
}
