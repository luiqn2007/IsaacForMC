package lq2007.mcmod.isaacmod.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class NBTSerializer implements ISerializer<CompoundNBT> {

    public static final NBTSerializer INSTANCE = new NBTSerializer();

    @Override
    public CompoundNBT read(PacketBuffer buffer) {
        return buffer.readCompoundTag();
    }

    @Override
    public PacketBuffer write(CompoundNBT item, PacketBuffer buffer) {
        return buffer.writeCompoundTag(item);
    }

    @Override
    public CompoundNBT read(CompoundNBT nbt, String key) {
        return nbt.getCompound(key);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, CompoundNBT item) {
        nbt.put(key, item);
        return nbt;
    }
}
