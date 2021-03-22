package lq2007.mcmod.isaacmod.common.util.serializer;

import lq2007.mcmod.isaacmod.common.util.ReflectionUtil;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class ClassSerializer implements ISerializer<Class<?>> {

    public static final ClassSerializer INSTANCE = new ClassSerializer();

    @Override
    public Class<?> read(PacketBuffer buffer) {
        return read0(buffer.readString());
    }

    @Override
    public PacketBuffer write(Class<?> item, PacketBuffer buffer) {
        buffer.writeString(item.getName());
        return buffer;
    }

    @Override
    public Class<?> read(CompoundNBT nbt, String key) {
        return read0(nbt.getString(key));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, Class<?> item) {
        nbt.putString(key, item.getName());
        return nbt;
    }

    private Class<?> read0(String name) {
        return ReflectionUtil.loadClass(name);
    }
}
