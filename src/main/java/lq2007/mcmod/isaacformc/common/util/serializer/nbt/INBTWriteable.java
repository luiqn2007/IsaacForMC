package lq2007.mcmod.isaacformc.common.util.serializer.nbt;

import net.minecraft.nbt.CompoundNBT;

public interface INBTWriteable {

    /**
     * Write data to a buffer.
     *
     * @param nbt nbt
     */
    default void write(CompoundNBT nbt) {
        NBTSerializeCache cache = NBTSerializeCache.getOrCreate(getClass());
        cache.initFields();
        for (FieldNBTWrapper wrapper : cache.fields) {
            Object o = wrapper.get(this);
            if (o != null) {
                wrapper.write(this, nbt);
            }
        }
    }
}
