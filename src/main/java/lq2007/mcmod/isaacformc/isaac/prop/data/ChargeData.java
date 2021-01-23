package lq2007.mcmod.isaacformc.isaac.prop.data;

import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class ChargeData implements IPropData {

    public int totalCharge, charge;
    public int totalProcess, process;

    public ChargeData(int totalCharge, int totalProcess) {
        this.charge = 0;
        this.totalCharge = totalCharge;
        this.process = -1;
        this.totalProcess = totalProcess;
    }

    @Override
    public void onBindTo(PropItem item) { }

    @Override
    public void update(LivingEntity entity, PropItem item) {
        if (!entity.world.isRemote && process >= 0) {
            process++;
            if (process >= totalProcess) {
                process = -1;
            }
        }
    }

    @Override
    public void active() {
        process = 1;
    }

    @Override
    public boolean isActive() {
        return process >= 0;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("totalCharge", totalCharge);
        nbt.putInt("charge", charge);
        nbt.putInt("totalProcess", totalProcess);
        nbt.putInt("process", process);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        totalCharge = nbt.getInt("totalCharge");
        charge = nbt.getInt("charge");
        totalProcess = nbt.getInt("totalProcess");
        process = nbt.getInt("process");
    }

    @Override
    public void read(PacketBuffer buffer) {
        totalCharge = buffer.readVarInt();
        charge = buffer.readVarInt();
        totalProcess = buffer.readVarInt();
        process = buffer.readVarInt();
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeVarInt(totalCharge);
        buffer.writeVarInt(charge);
        buffer.writeVarInt(totalProcess);
        buffer.writeVarInt(process);
    }
}
