package lq2007.mcmod.isaacformc.common.isaac.prop;

import net.minecraft.nbt.CompoundNBT;

public class NoData implements IPropData {

    public static final NoData INSTANCE = new NoData();

    private NoData() {}

    @Override
    public void onBindTo(PropItem item) {

    }

    @Override
    public CompoundNBT serializeNBT() {
        return new CompoundNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}
