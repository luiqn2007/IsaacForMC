package lq2007.mcmod.isaacformc.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class ShortSerializer implements ISerializer<Short> {

    public static final ShortSerializer INSTANCE = new ShortSerializer();

    @Override
    public Short read(PacketBuffer buffer) {
        return read0(buffer);
    }

    @Override
    public PacketBuffer write(Short item, PacketBuffer buffer) {
        write0(item, buffer);
        return buffer;
    }

    @Override
    public Short read(CompoundNBT nbt, String key) {
        return null;
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, Short item) {
        return null;
    }

    public short read0(PacketBuffer buffer) {
        return buffer.readShort();
    }

    public void write0(short value, PacketBuffer buffer) {
        buffer.writeShort(value);
    }

    public short read0(CompoundNBT nbt, String key) {
        return nbt.getShort(key);
    }

    public void write0(CompoundNBT nbt, String key, short item) {
        nbt.putShort(key, item);
    }
}
