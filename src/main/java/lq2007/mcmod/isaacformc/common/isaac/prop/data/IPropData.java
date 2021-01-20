package lq2007.mcmod.isaacformc.common.isaac.prop.data;

import lq2007.mcmod.isaacformc.common.isaac.prop.PropItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPropData extends INBTSerializable<CompoundNBT> {

    void onBindTo(PropItem item);

    NoData NO_DATA = new NoData();
    static ChargeData createCharge(int charge) {
        return new ChargeData(charge);
    }

    class NoData implements IPropData {

        @Override
        public void onBindTo(PropItem item) { }

        @Override
        public void deserializeNBT(CompoundNBT nbt) { }

        @Override
        public CompoundNBT serializeNBT() {
            return new CompoundNBT();
        }
    }

    class ChargeData implements IPropData {

        public int totalCharge, charge;

        public ChargeData(int total) {
            charge = 0;
            totalCharge = total;
        }

        @Override
        public void onBindTo(PropItem item) { }

        @Override
        public CompoundNBT serializeNBT() {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt("total", totalCharge);
            nbt.putInt("charge", charge);
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            totalCharge = nbt.getInt("total");
            charge = nbt.getInt("charge");
        }
    }
}
