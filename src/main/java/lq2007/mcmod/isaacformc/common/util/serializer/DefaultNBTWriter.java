package lq2007.mcmod.isaacformc.common.util.serializer;

import lq2007.mcmod.isaacformc.common.util.serializer.nbt.INBTWriteable;
import lq2007.mcmod.isaacformc.common.util.serializer.nbt.INBTWriter;
import net.minecraft.nbt.CompoundNBT;

public class DefaultNBTWriter<T extends INBTWriteable> implements INBTWriter<T> {

    public static final DefaultNBTWriter INSTANCE = new DefaultNBTWriter<>();

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, T item) {
        CompoundNBT data = new CompoundNBT();
        item.write(data);
        nbt.put(key, data);
        return nbt;
    }
}
