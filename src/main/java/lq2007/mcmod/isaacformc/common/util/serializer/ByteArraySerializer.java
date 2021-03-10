package lq2007.mcmod.isaacformc.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class ByteArraySerializer implements ISerializer<byte[]> {

    public static final ByteArraySerializer INSTANCE = new ByteArraySerializer();

    @Override
    public byte[] read(PacketBuffer buffer) {
        return buffer.readByteArray();
    }

    @Override
    public PacketBuffer write(byte[] item, PacketBuffer buffer) {
        return buffer.writeByteArray(item);
    }

    @Override
    public byte[] read(CompoundNBT nbt, String key) {
        return nbt.getByteArray(key);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, byte[] item) {
        nbt.putByteArray(key, item);
        return nbt;
    }
}
