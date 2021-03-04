package lq2007.mcmod.isaacformc.common.network;

import net.minecraft.network.PacketBuffer;

public interface IPacketReadable {

    /**
     * Write data to a buffer.
     *
     * @param buffer buffer
     */
    void read(PacketBuffer buffer);
}
