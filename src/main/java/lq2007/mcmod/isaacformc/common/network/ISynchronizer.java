package lq2007.mcmod.isaacformc.common.network;

import net.minecraft.network.PacketBuffer;

public interface ISynchronizer<T> {

    default T read(T item, PacketBuffer buffer) {
        return item;
    }

    default PacketBuffer write(T item, PacketBuffer buffer) {
        return buffer;
    }
}
