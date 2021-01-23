package lq2007.mcmod.isaacformc.isaac.prop.data;

import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.INBTSerializable;

public class NoData implements IPropData {

    public static final NoData INSTANCE = new NoData();

    @Override
    public void onBindTo(PropItem item) { }

    @Override
    public void update(LivingEntity entity, PropItem item) { }

    @Override
    public void active() { }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) { }

    @Override
    public CompoundNBT serializeNBT() {
        return new CompoundNBT();
    }

    @Override
    public void read(PacketBuffer buffer) { }

    @Override
    public void write(PacketBuffer buffer) { }
}