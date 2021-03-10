package lq2007.mcmod.isaacformc.common.util.serializer.nbt;

import net.minecraft.nbt.CompoundNBT;

public interface INBTReadable {

    /**
     * Write data to a buffer.
     *
     * @param nbt buffer
     */
    default void read(CompoundNBT nbt) {
        NBTSerializeCache cache = NBTSerializeCache.getOrCreate(getClass());
        cache.initFields();
        for (FieldNBTWrapper wrapper : cache.fields) {
            if (nbt.contains(wrapper.name)) {
                wrapper.read(this, nbt);
            }
        }
    }
}
