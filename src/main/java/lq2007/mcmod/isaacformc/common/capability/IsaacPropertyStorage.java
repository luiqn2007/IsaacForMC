package lq2007.mcmod.isaacformc.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class IsaacPropertyStorage implements Capability.IStorage<IIsaacProperty> {

    public static final IsaacPropertyStorage INSTANCE = new IsaacPropertyStorage();

    @Nullable
    @Override
    public INBT writeNBT(Capability<IIsaacProperty> capability, IIsaacProperty instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IIsaacProperty> capability, IIsaacProperty instance, Direction side, INBT nbt) {
        instance.deserializeNBT((CompoundNBT) nbt);
    }
}
