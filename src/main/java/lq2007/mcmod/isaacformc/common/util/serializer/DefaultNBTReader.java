package lq2007.mcmod.isaacformc.common.util.serializer;

import lq2007.mcmod.isaacformc.common.util.serializer.nbt.INBTReadable;
import lq2007.mcmod.isaacformc.common.util.serializer.nbt.INBTReader;
import net.minecraft.nbt.CompoundNBT;

public class DefaultNBTReader<T extends INBTReadable> implements INBTReader<T> {

    private final Class<T> aClass;

    public DefaultNBTReader(Class<T> aClass) {
        this.aClass = aClass;
    }

    @Override
    public T read(CompoundNBT nbt, String key) {
        try {
            T t = aClass.newInstance();
            t.read(nbt);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
