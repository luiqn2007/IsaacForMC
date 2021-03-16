package lq2007.mcmod.isaacformc.common.util.serializer;

import lq2007.mcmod.isaacformc.common.util.serializer.packet.INBTSerializer;
import lq2007.mcmod.isaacformc.common.util.serializer.buffer.IPacketSerializer;

public interface ISerializer<T> extends IPacketSerializer<T>, INBTSerializer<T> {
}
