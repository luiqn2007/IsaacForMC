package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lq2007.mcmod.isaacformc.common.util.serializer.nbt.INBTSerializable;
import lq2007.mcmod.isaacformc.common.util.serializer.nbt.NBTData;
import lq2007.mcmod.isaacformc.common.util.serializer.network.BufferData;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearAppearances;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearEffects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IsaacProperty implements IIsaacProperty, ICapabilityProvider, INBTSerializable {

    @NBTData
    @BufferData
    private int size = 0;
    @NBTData
    @BufferData
    private boolean sizeLocked = false;
    @NBTData
    @BufferData
    private int tearCount = 1;
    @NBTData
    @BufferData
    private float tearSpeed = 1;
    @NBTData
    @BufferData
    private float tearSpeedMultiple = 1;
    @NBTData
    @BufferData
    private float tossUpSpeed = 1;
    @NBTData
    @BufferData
    private float shootDelay = 0;
    @NBTData
    @BufferData
    private float shootDelayMultiple = 1;
    @NBTData
    @BufferData
    private int knockBack = 0;
    @NBTData
    @BufferData
    private float range = 1;
    @NBTData(collection = HashSet.class, V = EnumTearEffects.class)
    @BufferData(collection = HashSet.class, V = EnumTearEffects.class)
    private Set<EnumTearEffects> tearEffects = new HashSet<>();
    @NBTData(V = EnumTearAppearances.class)
    @BufferData(V = EnumTearAppearances.class)
    private List<EnumTearAppearances> tearAppearances = new ArrayList<>();

    @Override
    public int bodySize() {
        return size;
    }

    @Override
    public void bodySize(int size) {
        if (!sizeLocked && this.size != size) {
            this.size = size;
        }
    }

    @Override
    public void lockBodySize() {
        if (!sizeLocked) {
            sizeLocked = true;
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
        }
    }

    @Override
    public void tearCount(int count, int maxCount) {
        int c = MathHelper.clamp(count, 0, maxCount);
        if (c != count) {
            tearCount = c;
        }
    }

    @Override
    public float tearSpeed() {
        return tearSpeed;
    }

    @Override
    public void tearSpeed(float count) {
        if (count != tearSpeed) {
            this.tearSpeed = count;
        }
    }

    @Override
    public float tearSpeedMultiple() {
        return tearSpeedMultiple;
    }

    @Override
    public void tearSpeedMultiple(float count) {
        if (count != 0 && count != tearSpeedMultiple) {
            tearSpeedMultiple = count;
        }
    }

    @Override
    public float tossUpSpeed() {
        return tossUpSpeed;
    }

    @Override
    public void tossUpSpeed(float speed) {
        if (this.tossUpSpeed != speed) {
            this.tossUpSpeed = speed;
        }
    }

    @Override
    public float shootDelay() {
        return shootDelay;
    }

    @Override
    public void shootDelay(float delay) {
        if (delay != shootDelay) {
            this.shootDelay = delay;
        }
    }

    @Override
    public float shootDelayMultiple() {
        return shootDelayMultiple;
    }

    @Override
    public void shootDelayMultiple(float delay) {
        if (delay != 0 && shootDelayMultiple != delay) {
            shootDelayMultiple = delay;
        }
    }

    @Override
    public int knockBack() {
        return knockBack;
    }

    @Override
    public void knockBack(int knockBack) {
        if (knockBack != this.knockBack) {
            this.knockBack = knockBack;
        }
    }

    @Override
    public void addTearEffect(EnumTearEffects type) {
        tearEffects.add(type);
    }

    @Override
    public ImmutableSet<EnumTearEffects> getTearEffects() {
        return ImmutableSet.copyOf(tearEffects);
    }

    @Override
    public void removeTearEffect(EnumTearEffects type) {
        tearEffects.remove(type);
    }

    @Override
    public void addTearAppearance(EnumTearAppearances appearance) {
        tearAppearances.add(appearance);
    }

    @Override
    public ImmutableList<EnumTearAppearances> getTearAppearances() {
        return ImmutableList.copyOf(tearAppearances);
    }

    @Override
    public void removeTearAppearance(EnumTearAppearances appearance) {
        tearAppearances.remove(appearance);
    }

    @Override
    public float range() {
        return range;
    }

    @Override
    public void range(float range) {
        if (this.range != range) {
            this.range = range;
        }
    }

    @Override
    public void copy(IIsaacProperty source) {
        size = source.bodySize();
        sizeLocked = source.isBodySizeLocked();
        tearCount = source.tearCount();
        tearSpeed = source.tearSpeed();
        tearSpeedMultiple = source.tearSpeedMultiple();
        tossUpSpeed = source.tossUpSpeed();
        shootDelay = source.shootDelay();
        shootDelayMultiple = source.shootDelayMultiple();
        tearEffects = new HashSet<>(source.getTearEffects());
        tearAppearances = new ArrayList<>(source.getTearAppearances());
        knockBack = source.knockBack();
        range = source.range();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return IsaacCapabilities.CAPABILITY_PROPERTY.orEmpty(cap, LazyOptional.of(() -> this));
    }
}