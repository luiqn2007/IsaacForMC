package lq2007.mcmod.isaacmod.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ICapability<C extends ICapability<C>> extends ICapabilitySerializable<CompoundNBT> {

    Capability<C> getCapability();

    default C unpack(ICapability<C> o) {
        return (C) o;
    }

    @Nonnull
    @Override
    default <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return getCapability().orEmpty(cap, LazyOptional.of(() -> unpack(this)));
    }
}
