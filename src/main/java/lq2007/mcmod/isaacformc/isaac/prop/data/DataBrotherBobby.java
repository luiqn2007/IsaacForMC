package lq2007.mcmod.isaacformc.isaac.prop.data;

import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

import java.util.UUID;

public class DataBrotherBobby extends SimpleData {

    public UUID bobby;

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
        CompoundNBT nbt = new CompoundNBT();
        nbt.putUniqueId("uuid", bobby);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        bobby = nbt.getUniqueId("uuid");
    }
}
