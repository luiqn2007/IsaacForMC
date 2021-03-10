package lq2007.mcmod.isaacformc.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class ByteSerializer implements ISerializer<Byte> {

    public static final ByteSerializer INSTANCE = new ByteSerializer();

    @Override
    public Byte read(PacketBuffer buffer) {
        return read0(buffer);
    }

    @Override
    public PacketBuffer write(Byte item, PacketBuffer buffer) {
        write0(item, buffer);
        return buffer;
    }

    @Override
    public Byte read(CompoundNBT nbt, String key) {
        return read0(nbt, key);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, Byte item) {
        write0(nbt, key, item);
        return nbt;
    }

    public byte read0(PacketBuffer buffer) {
        return buffer.readByte();
    }

    public void write0(byte value, PacketBuffer buffer) {
        buffer.writeByte(value);
    }

    public byte read0(CompoundNBT nbt, String key) {
        return nbt.getByte(key);
    }

    public void write0(CompoundNBT nbt, String key, byte item) {
        nbt.putByte(key, item);
    }
}
