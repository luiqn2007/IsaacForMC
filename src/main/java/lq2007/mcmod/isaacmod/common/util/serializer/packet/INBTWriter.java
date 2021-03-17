package lq2007.mcmod.isaacmod.common.util.serializer.packet;

import net.minecraft.nbt.CompoundNBT;

public interface INBTWriter<T> {


    /**
     * Write data to a nbt.
     *
     * @param nbt nbt
     * @param key key
     * @param item item
     */
    CompoundNBT write(CompoundNBT nbt, String key, T item);
}
