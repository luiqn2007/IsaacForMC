package lq2007.mcmod.isaacmod.capability.empty;

import lq2007.mcmod.isaacmod.capability.PlayerData;
import net.minecraft.nbt.CompoundNBT;

public class EmptyPlayerData extends PlayerData {

    public static EmptyPlayerData INSTANCE = new EmptyPlayerData();

    @Override
    public CompoundNBT serializeNBT() {
        return new CompoundNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}
