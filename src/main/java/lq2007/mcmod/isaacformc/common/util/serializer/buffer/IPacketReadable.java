package lq2007.mcmod.isaacformc.common.util.serializer.buffer;

import net.minecraft.network.PacketBuffer;

public interface IPacketReadable {

    /**
     * Write data to a buffer.
     *
     * @param buffer buffer
     */
    default void read(PacketBuffer buffer) {
        PacketSerializeCache cache = PacketSerializeCache.getOrCreate(getClass());
        cache.initFields();
        int nullableCount = cache.nullableFields.size();
        byte nullPoint = buffer.readByte();
        for (int i = 0; i < nullableCount; i++) {
            FieldPacketWrapper wrapper = cache.nullableFields.get(i);
            int b = 1 << i;
            if ((nullPoint & b) == b) wrapper.setNull(this);
            else wrapper.read(this, buffer);
        }
        for (FieldPacketWrapper wrapper : cache.nonnullFields) {
            wrapper.read(this, buffer);
        }
    }
}
