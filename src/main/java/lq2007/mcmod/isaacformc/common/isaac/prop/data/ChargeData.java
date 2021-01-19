package lq2007.mcmod.isaacformc.common.isaac.prop.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class ChargeData implements INBTSerializable<CompoundNBT> {

    private int charge, maxCharge;

    public ChargeData(int maxCharge) {
        this.maxCharge = maxCharge;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}
