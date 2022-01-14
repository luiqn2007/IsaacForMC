package lq2007.mcmod.isaacmod.prop;

import net.minecraft.nbt.CompoundNBT;

public class ActivatePropData extends BasePropData {

    private int charge = 0;
    private final int maxCharge;

    public ActivatePropData(int maxCharge) {
        this.maxCharge = maxCharge;
    }

    public void addCharge(int charge) {
        this.charge = Math.min(maxCharge, this.charge + charge);
    }

    public void decCharge(int charge) {
        this.charge = Math.max(0, this.charge - charge);
    }

    public void clearCharge() {
        this.charge = 0;
    }

    public boolean isFullCharge() {
        return charge == maxCharge;
    }

    public int getCharge() {
        return charge;
    }

    public int getMaxCharge() {
        return maxCharge;
    }

    @Override
    public void write(CompoundNBT nbt) {
        nbt.putInt("charge", charge);
    }

    @Override
    public void read(CompoundNBT nbt) {
        charge = nbt.getInt("charge");
    }
}
