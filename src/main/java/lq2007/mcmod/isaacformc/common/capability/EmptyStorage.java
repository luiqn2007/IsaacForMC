package lq2007.mcmod.isaacformc.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class EmptyStorage implements Capability.IStorage {

    public static final EmptyStorage INSTANCE = new EmptyStorage();
    private static final CompoundNBT EMPTY = new CompoundNBT();

    public static <T> Capability.IStorage<T> get() {
        return (Capability.IStorage<T>) INSTANCE;
    }

    @Nullable
    @Override
    public INBT writeNBT(Capability capability, Object instance, Direction side) {
        return EMPTY;
    }

    @Override
    public void readNBT(Capability capability, Object instance, Direction side, INBT nbt) { }
}
