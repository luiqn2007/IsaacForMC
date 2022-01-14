package lq2007.mcmod.isaacmod.prop;

import net.minecraft.nbt.CompoundNBT;

public class EmptyPropData extends BasePropData {

    public static final EmptyPropData INSTANCE = new EmptyPropData();

    @Override
    public void write(CompoundNBT nbt) {

    }

    @Override
    public void read(CompoundNBT nbt) {

    }
}
