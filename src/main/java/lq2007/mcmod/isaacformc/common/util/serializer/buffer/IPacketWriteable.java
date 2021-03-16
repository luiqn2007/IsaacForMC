package lq2007.mcmod.isaacformc.common.util.serializer.buffer;

import net.minecraft.network.PacketBuffer;

public interface IPacketWriteable {

    /**
     * Write data to a buffer.
     *
     * @param buffer buffer
     */
    default void write(PacketBuffer buffer) {
        PacketSerializeCache cache = PacketSerializeCache.getOrCreate(getClass());
        cache.initFields();
        // int nullPoint; // 32 bit, ensure the count of nullable fields less than 32
        byte nullPoint = 0b00000000; // 8 bit, ensure the count of nullable fields less than 8
        int nullableCount = cache.nullableFields.size();
        FieldPacketWrapper[] wrappers = new FieldPacketWrapper[nullableCount];
        for (int i = 0; i < nullableCount; i++) {
            FieldPacketWrapper wrapper = cache.nullableFields.get(i);
            Object o = wrapper.get(this);
            if (o == null) {
                wrappers[i] = null;
                nullPoint |= (1 << i);
            } else {
                wrappers[i] = wrapper;
            }
        }
        buffer.writeByte(nullPoint);
        for (int i = 0; i < nullableCount; i++) {
            if (wrappers[i] != null) {
                wrappers[i].write(this, buffer);
            }
        }
        for (FieldPacketWrapper wrapper : cache.nonnullFields) {
            wrapper.write(this, buffer);
        }
    }
}
