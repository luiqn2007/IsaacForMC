package lq2007.mcmod.isaacmod.common.util.serializer.packet;

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
