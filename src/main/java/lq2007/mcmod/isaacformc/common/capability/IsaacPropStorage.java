package lq2007.mcmod.isaacformc.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class IsaacPropStorage implements Capability.IStorage<IIsaacPropData> {

    public static final IsaacPropStorage INSTANCE = new IsaacPropStorage();

    @Nullable
    @Override
    public INBT writeNBT(Capability<IIsaacPropData> capability, IIsaacPropData instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IIsaacPropData> capability, IIsaacPropData instance, Direction side, INBT nbt) {
        instance.deserializeNBT((CompoundNBT) nbt);
    }
}
