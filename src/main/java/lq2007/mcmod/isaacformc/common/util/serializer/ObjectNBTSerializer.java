package lq2007.mcmod.isaacformc.common.util.serializer;

import lq2007.mcmod.isaacformc.common.util.serializer.packet.INBTSerializable;
import lq2007.mcmod.isaacformc.common.util.serializer.packet.INBTSerializer;
import net.minecraft.nbt.CompoundNBT;

public class ObjectNBTSerializer<T extends INBTSerializable> implements INBTSerializer<T> {

    public static final ObjectNBTSerializer INSTANCE = new ObjectNBTSerializer();

    @Override
    public T read(CompoundNBT nbt, String key) {
        try {
            CompoundNBT compound = nbt.getCompound(key);
            Class<T> aClass = (Class<T>) ClassSerializer.INSTANCE.read(compound, "class");
            T t = aClass.newInstance();
            t.read(compound.getCompound("data"));
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, T item) {
        CompoundNBT compound = new CompoundNBT();
        ClassSerializer.INSTANCE.write(compound, "class", item.getClass());
        CompoundNBT data = new CompoundNBT();
        item.write(data);
        compound.put("data", data);
        nbt.put(key, compound);
        return nbt;
    }
}
