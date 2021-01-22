package lq2007.mcmod.isaacformc.common.network;

import net.minecraft.network.PacketBuffer;

public interface IPacketReader {

    /**
     * <p>Write data to a buffer.
     * <p>
     * <p>This is a convention: the first value in the packet is a boolean. It means the capability type. True is
     * default packet, and False is used for custom packet.
     * <p>This convention is used for classes implement {@link IPacketWriter}
     *
     * @param buffer buffer
     */
    void read(PacketBuffer buffer);
}
