package lq2007.mcmod.isaacmod.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class EnumSerializer<T extends Enum<T>> implements ISerializer<T> {

    private final Class<T> aClass;

    public EnumSerializer(Class<T> aClass) {
        this.aClass = aClass;
    }

    @Override
    public T read(PacketBuffer buffer) {
        return buffer.readEnumValue(aClass);
    }

    @Override
    public PacketBuffer write(T item, PacketBuffer buffer) {
        return buffer.writeEnumValue(item);
    }

    @Override
    public T read(CompoundNBT nbt, String key) {
        return Enum.valueOf(aClass, nbt.getString(key));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, T item) {
        nbt.putString(key, item.name());
        return nbt;
    }
}
