package lq2007.mcmod.isaacformc.common.capability;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IsaacProperty extends VersionCapability implements IIsaacProperty, ICapabilityProvider {

    private final String KEY_BODY_SIZE = "_size";
    private final String KEY_BODY_LOCK = "_lock";
    private final String KEY_TEAR_COUNT = "_count";

    private int size = 0;
    private boolean sizeLocked = false;
    private int tearCount = 1;

    private final LazyOptional<IIsaacProperty> get = LazyOptional.of(() -> this);

    @Override
    public int bodySize() {
        return size;
    }

    @Override
    public void bodySize(int size) {
        if (!sizeLocked && this.size != size) {
            this.size = size;
            markDirty();
        }
    }

    @Override
    public void lockBodySize() {
        if (!sizeLocked) {
            sizeLocked = true;
            markDirty();
        }
    }

    @Override
    public boolean isBodySizeLocked() {
        return sizeLocked;
    }

    @Override
    public int tearCount() {
        return tearCount;
    }

    @Override
    public void tearCount(int count) {
        count = Math.max(count, 0);
        if (tearCount != size) {
            tearCount = count;
            markDirty();
        }
    }

    @Override
    public void tearCount(int count, int maxCount) {
        tearCount = MathHelper.clamp(count, 0, maxCount);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return IsaacCapabilities.CAPABILITY_PROPERTY.orEmpty(cap, get);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt(KEY_BODY_SIZE, size);
        nbt.putBoolean(KEY_BODY_LOCK, sizeLocked);
        nbt.putInt(KEY_TEAR_COUNT, tearCount);
        markDirty();
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        size = nbt.getInt(KEY_BODY_SIZE);
        sizeLocked = nbt.getBoolean(KEY_BODY_LOCK);
        tearCount = nbt.getInt(KEY_TEAR_COUNT);
    }

    @Override
    public Void copyFrom(LivingEntity entity) {
        IIsaacProperty data = IsaacCapabilities.getProperty(entity);
        size = data.bodySize();
        tearCount = data.tearCount();
        sizeLocked = data.isBodySizeLocked();
        markDirty();
        return null;
    }

    @Override
    protected void read(PacketBuffer buffer, int version) {
        size = buffer.readVarInt();
        sizeLocked = buffer.readBoolean();
        tearCount = buffer.readVarInt();
    }

    @Override
    protected void write(PacketBuffer buffer, int version) {
        buffer.writeVarInt(size);
        buffer.writeBoolean(sizeLocked);
        buffer.writeVarInt(tearCount);
    }
}