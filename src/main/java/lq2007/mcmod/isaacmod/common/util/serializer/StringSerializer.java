package lq2007.mcmod.isaacmod.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class StringSerializer implements ISerializer<String> {

    public static final StringSerializer INSTANCE = new StringSerializer();

    @Override
    public String read(PacketBuffer buffer) {
        return buffer.readString();
    }

    @Override
    public PacketBuffer write(String item, PacketBuffer buffer) {
        return buffer.writeString(item);
    }

    @Override
    public String read(CompoundNBT nbt, String key) {
        return nbt.getString(key);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, String item) {
        nbt.putString(key, item);
        return nbt;
    }
}
