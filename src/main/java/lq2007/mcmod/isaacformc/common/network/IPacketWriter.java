package lq2007.mcmod.isaacformc.common.network;

import net.minecraft.network.PacketBuffer;

public interface IPacketWriter {

    /**
     * <p>Read data from a buffer.
     * <p>The method is called at client.
     *
     * @param buffer buffer
     */
    void write(PacketBuffer buffer);
}
