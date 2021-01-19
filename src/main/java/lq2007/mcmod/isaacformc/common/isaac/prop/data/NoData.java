package lq2007.mcmod.isaacformc.common.isaac.prop.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class NoData implements INBTSerializable<CompoundNBT> {

    public static final NoData INSTANCE = new NoData();

    @Override
    public CompoundNBT serializeNBT() {
        return new CompoundNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}
