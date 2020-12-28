package lq2007.mcmod.isaacformc.common.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class IsaacDataStorage implements Capability.IStorage<IIsaacData> {

    public static final IsaacDataStorage INSTANCE = new IsaacDataStorage();

    @Nullable
    @Override
    public INBT writeNBT(Capability<IIsaacData> capability, IIsaacData instance, Direction side) {
        return null;
    }

    @Override
    public void readNBT(Capability<IIsaacData> capability, IIsaacData instance, Direction side, INBT nbt) {

    }
}
