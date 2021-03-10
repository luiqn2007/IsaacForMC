package lq2007.mcmod.isaacformc.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class BooleanSerializer implements ISerializer<Boolean> {

    public static final BooleanSerializer INSTANCE = new BooleanSerializer();

    @Override
    public Boolean read(PacketBuffer buffer) {
        return read0(buffer);
    }

    @Override
    public PacketBuffer write(Boolean item, PacketBuffer buffer) {
        write0(item != null && item, buffer);
        return buffer;
    }

    @Override
    public Boolean read(CompoundNBT nbt, String key) {
        return read0(nbt, key);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, Boolean item) {
        write0(nbt, key, item != null && item);
        return nbt;
    }

    public boolean read0(PacketBuffer buffer) {
        return buffer.readBoolean();
    }

    public void write0(boolean value, PacketBuffer buffer) {
        buffer.writeBoolean(value);
    }

    public boolean read0(CompoundNBT nbt, String key) {
        return nbt.getBoolean(key);
    }

    public void write0(CompoundNBT nbt, String key, boolean item) {
        nbt.putBoolean(key, item);
    }
}
