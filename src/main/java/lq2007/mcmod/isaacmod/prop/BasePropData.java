package lq2007.mcmod.isaacmod.prop;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public abstract class BasePropData implements INBTSerializable<CompoundNBT> {

    private UUID appliedPlayer = null;

    public boolean isAppliedTo(PlayerEntity player) {
        return appliedPlayer != null && appliedPlayer.equals(player.getUniqueID());
    }

    public void setApplyTo(PlayerEntity player) {
        this.appliedPlayer = player.getUniqueID();
    }

    @Override
    public final CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        write(nbt);
        return nbt;
    }

    public abstract void write(CompoundNBT nbt);

    @Override
    public final void deserializeNBT(CompoundNBT nbt) {
        read(nbt);
    }

    public abstract void read(CompoundNBT nbt);
}
