package lq2007.mcmod.isaacformc.common.util.serializer;

import lq2007.mcmod.isaacformc.common.util.serializer.nbt.INBTReadable;
import lq2007.mcmod.isaacformc.common.util.serializer.nbt.INBTSerializer;
import lq2007.mcmod.isaacformc.common.util.serializer.nbt.INBTWriteable;
import net.minecraft.nbt.CompoundNBT;

public class DefaultNBTSerializer<T extends INBTReadable & INBTWriteable> extends DefaultNBTReader<T> implements INBTSerializer<T> {

    public DefaultNBTSerializer(Class<T> aClass) {
        super(aClass);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, T item) {
        return DefaultNBTWriter.INSTANCE.write(nbt, key, item);
    }
}
