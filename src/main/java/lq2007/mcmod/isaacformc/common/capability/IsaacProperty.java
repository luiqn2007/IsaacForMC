package lq2007.mcmod.isaacformc.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IsaacProperty implements IIsaacProperty, ICapabilityProvider {

    private final String KEY_BODY_SIZE = "_size";
    private final String KEY_BODY_LOCK = "_lock";
    private final String KEY_TEAR_COUNT = "_count";

    private int size = 0;
    private boolean sizeLocked = false;
    private int tearCount = 1;

    @Override
    public int bodySize() {
        return size;
    }

    @Override
    public void bodySize(int size) {
        this.size = size;
    }

    @Override
    public void lockBodySize() {
        sizeLocked = true;
    }

    @Override
    public int tearCount() {
        return tearCount;
    }

    @Override
    public void tearCount(int count) {
        tearCount = Math.max(count, 0);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return IsaacCapabilities.CAPABILITY_PROPERTY.orEmpty(cap, LazyOptional.of(() -> this));
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt(KEY_BODY_SIZE, size);
        nbt.putBoolean(KEY_BODY_LOCK, sizeLocked);
        nbt.putInt(KEY_TEAR_COUNT, tearCount);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        size = nbt.getInt(KEY_BODY_SIZE);
        sizeLocked = nbt.getBoolean(KEY_BODY_LOCK);
        tearCount = nbt.getInt(KEY_TEAR_COUNT);
    }
}