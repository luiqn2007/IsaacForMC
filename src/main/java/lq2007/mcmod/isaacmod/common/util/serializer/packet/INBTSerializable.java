package lq2007.mcmod.isaacmod.common.util.serializer.packet;

import net.minecraft.nbt.CompoundNBT;

public interface INBTSerializable extends INBTReadable, INBTWriteable, net.minecraftforge.common.util.INBTSerializable<CompoundNBT> {

    @Override
    default CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        write(nbt);
        return nbt;
    }

    @Override
    default void deserializeNBT(CompoundNBT nbt) {
        read(nbt);
    }
}
