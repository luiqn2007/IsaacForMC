package lq2007.mcmod.isaacmod.common.util.serializer;

import lq2007.mcmod.isaacmod.common.util.serializer.packet.INBTSerializer;
import lq2007.mcmod.isaacmod.common.util.serializer.buffer.IPacketSerializer;

public interface ISerializer<T> extends IPacketSerializer<T>, INBTSerializer<T> {
}
