package lq2007.mcmod.isaacmod.capability.storage;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public final class NoStorage implements Capability.IStorage {

    public static final NoStorage INSTANCE = new NoStorage();

    public static <T> Capability.IStorage<T> get() {
        return (Capability.IStorage<T>) INSTANCE;
    }

    @Nullable
    @Override
    public INBT writeNBT(Capability capability, Object instance, Direction side) {
        return null;
    }

    @Override
    public void readNBT(Capability capability, Object instance, Direction side, INBT nbt) { }
}
