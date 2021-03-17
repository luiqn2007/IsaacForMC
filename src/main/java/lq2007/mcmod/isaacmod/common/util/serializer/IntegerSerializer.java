package lq2007.mcmod.isaacmod.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class IntegerSerializer implements ISerializer<Integer> {

    public static final IntegerSerializer INSTANCE = new IntegerSerializer();

    @Override
    public Integer read(PacketBuffer buffer) {
        return read0(buffer);
    }

    @Override
    public PacketBuffer write(Integer item, PacketBuffer buffer) {
        write0(item == null ? 0 : item, buffer);
        return buffer;
    }

    @Override
    public Integer read(CompoundNBT nbt, String key) {
        return read0(nbt, key);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, Integer item) {
        write0(nbt, key, item == null ? 0 : item);
        return nbt;
    }

    public int read0(PacketBuffer buffer) {
        return buffer.readInt();
    }

    public void write0(int value, PacketBuffer buffer) {
        buffer.writeInt(value);
    }

    public int read0(CompoundNBT nbt, String key) {
        return nbt.getInt(key);
    }

    public void write0(CompoundNBT nbt, String key, int item) {
        nbt.putInt(key, item);
    }
}
