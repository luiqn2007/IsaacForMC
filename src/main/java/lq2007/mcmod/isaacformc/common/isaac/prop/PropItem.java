package lq2007.mcmod.isaacformc.common.isaac.prop;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class PropItem implements INBTSerializable<CompoundNBT> {

    public static final PropItem EMPTY = new PropItem();

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public PropItem() {

    }

    public PropItem(CompoundNBT nbt) {

    }

    @Override
    public CompoundNBT serializeNBT() {
        return new CompoundNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}
