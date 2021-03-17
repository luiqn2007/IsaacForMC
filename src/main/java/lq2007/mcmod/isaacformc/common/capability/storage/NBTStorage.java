package lq2007.mcmod.isaacformc.common.capability.storage;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public class NBTStorage implements Capability.IStorage {

    public static final NBTStorage INSTANCE = new NBTStorage();

    public static <T extends INBTSerializable<CompoundNBT>> Capability.IStorage<T> get() {
        return (Capability.IStorage<T>) INSTANCE;
    }

    @Nullable
    @Override
    public INBT writeNBT(Capability capability, Object instance, Direction side) {
        return ((INBTSerializable<CompoundNBT>) instance).serializeNBT();
    }

    @Override
    public void readNBT(Capability capability, Object instance, Direction side, INBT nbt) {
        ((INBTSerializable<CompoundNBT>) instance).deserializeNBT((CompoundNBT) nbt);
    }
}
