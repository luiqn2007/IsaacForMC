package lq2007.mcmod.isaacmod.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class LongSerializer implements ISerializer<Long> {

    public static final LongSerializer INSTANCE = new LongSerializer();

    @Override
    public Long read(PacketBuffer buffer) {
        return read0(buffer);
    }

    @Override
    public PacketBuffer write(Long item, PacketBuffer buffer) {
        write0(item == null ? 0L : item, buffer);
        return buffer;
    }

    @Override
    public Long read(CompoundNBT nbt, String key) {
        return read0(nbt, key);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, Long item) {
        write0(nbt, key, item == null ? 0L : item);
        return null;
    }

    public long read0(PacketBuffer buffer) {
        return buffer.readLong();
    }

    public void write0(long value, PacketBuffer buffer) {
        buffer.writeLong(value);
    }

    public long read0(CompoundNBT nbt, String key) {
        return nbt.getLong(key);
    }

    public void write0(CompoundNBT nbt, String key, long item) {
        nbt.putLong(key, item);
    }
}
