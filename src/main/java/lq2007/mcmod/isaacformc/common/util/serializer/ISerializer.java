package lq2007.mcmod.isaacformc.common.util.serializer;

import lq2007.mcmod.isaacformc.common.util.serializer.nbt.INBTSerializer;
import lq2007.mcmod.isaacformc.common.util.serializer.network.IPacketSerializer;

public interface ISerializer<T> extends IPacketSerializer<T>, INBTSerializer<T> {
}
