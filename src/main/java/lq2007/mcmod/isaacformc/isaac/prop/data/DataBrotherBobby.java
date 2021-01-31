package lq2007.mcmod.isaacformc.isaac.prop.data;

import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class DataBrotherBobby extends SimpleData {

    @Override
    public void onBindTo(PropItem item) {
        super.onBindTo(item);
    }

    @Override
    public void read(PacketBuffer buffer) {

    }

    @Override
    public void write(PacketBuffer buffer) {

    }

    @Override
    public CompoundNBT serializeNBT() {
        return new CompoundNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}
