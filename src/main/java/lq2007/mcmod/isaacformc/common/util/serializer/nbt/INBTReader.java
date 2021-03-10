package lq2007.mcmod.isaacformc.common.util.serializer.nbt;

import net.minecraft.nbt.CompoundNBT;

public interface INBTReader<T> {

    /**
     * Write data to a nbt.
     *
     * @param nbt nbt
     * @param key key
     */
    T read(CompoundNBT nbt, String key);
}
