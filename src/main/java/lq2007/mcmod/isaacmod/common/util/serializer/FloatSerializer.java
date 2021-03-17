package lq2007.mcmod.isaacmod.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class FloatSerializer implements ISerializer<Float> {

    public static final FloatSerializer INSTANCE = new FloatSerializer();

    @Override
    public Float read(PacketBuffer buffer) {
        return read0(buffer);
    }

    @Override
    public PacketBuffer write(Float item, PacketBuffer buffer) {
        write0(item == null ? 0 : item, buffer);
        return buffer;
    }

    @Override
    public Float read(CompoundNBT nbt, String key) {
        return read0(nbt, key);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, Float item) {
        write0(nbt, key, item == null ? 0 : item);
        return nbt;
    }

    public float read0(PacketBuffer buffer) {
        return buffer.readFloat();
    }

    public void write0(float value, PacketBuffer buffer) {
        buffer.writeFloat(value);
    }

    public float read0(CompoundNBT nbt, String key) {
        return nbt.getFloat(key);
    }

    public void write0(CompoundNBT nbt, String key, float item) {
        nbt.putFloat(key, item);
    }
}
