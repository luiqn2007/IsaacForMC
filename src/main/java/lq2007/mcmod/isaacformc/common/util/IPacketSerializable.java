package lq2007.mcmod.isaacformc.common.util;

import net.minecraft.network.PacketBuffer;

public interface IPacketSerializable {

    void read(PacketBuffer buffer);

    void write(PacketBuffer buffer);
}
