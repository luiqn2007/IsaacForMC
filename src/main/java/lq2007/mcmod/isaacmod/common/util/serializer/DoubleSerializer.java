package lq2007.mcmod.isaacmod.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class DoubleSerializer implements ISerializer<Double> {

    public static final DoubleSerializer INSTANCE = new DoubleSerializer();

    @Override
    public Double read(PacketBuffer buffer) {
        return read0(buffer);
    }

    @Override
    public PacketBuffer write(Double item, PacketBuffer buffer) {
        write0(item == null ? 0 : item, buffer);
        return buffer;
    }

    @Override
    public Double read(CompoundNBT nbt, String key) {
        return read0(nbt, key);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, Double item) {
        write0(nbt, key, item == null ? 0 : item);
        return nbt;
    }

    public double read0(PacketBuffer buffer) {
        return buffer.readDouble();
    }

    public void write0(double value, PacketBuffer buffer) {
        buffer.writeDouble(value);
    }

    public double read0(CompoundNBT nbt, String key) {
        return nbt.getDouble(key);
    }

    public void write0(CompoundNBT nbt, String key, double item) {
        nbt.putDouble(key, item);
    }
}
