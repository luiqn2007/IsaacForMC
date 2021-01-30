package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearEffects;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearAppearances;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class IsaacProperty extends VersionCapability implements IIsaacProperty, ICapabilityProvider {

    private final String KEY_BODY_SIZE = "_size";
    private final String KEY_BODY_LOCK = "_lock";
    private final String KEY_TEAR_COUNT = "_tear_count";
    private final String KEY_TEAR_SPEED = "_tear_speed";
    private final String KEY_TEAR_SPEED_MULTIPLE = "_tear_speed_multiple";
    private final String KEY_TOSS_UP_SPEED = "_toss_up_speed";
    private final String KEY_SHOOT_DELAY = "_shoot_delay";
    private final String KEY_SHOOT_DELAY_MULTIPLE = "_shoot_delay_multiple";
    private final String KEY_TEAR_EFFECT = "_tear_effect";
    private final String KEY_TEAR_APPEARANCE = "_tear_appearance";
    private final String KEY_KNOCK_BACK = "_knock_back";
    private final String KEY_RANGE = "_range";

    private int size = 0;
    private boolean sizeLocked = false;
    private int tearCount = 1;
    private float tearSpeed = 1;
    private float tearSpeedMultiple = 1;
    private float tossUpSpeed = 1;
    private float shootDelay = 0;
    private float shootDelayMultiple = 1;
    private Set<EnumTearEffects> tearEffects = new HashSet<>();
    private List<EnumTearAppearances> tearAppearances = new ArrayList<>();
    private int knockBack = 0;
    private float range = 1;

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
        int c = MathHelper.clamp(count, 0, maxCount);
        if (c != count) {
            tearCount = c;
            markDirty();
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
            markDirty();
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
            markDirty();
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
            markDirty();
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
            markDirty();
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
            markDirty();
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
            markDirty();
        }
    }

    @Override
    public void addTearEffect(EnumTearEffects type) {
        if (tearEffects.add(type)) {
            markDirty();
        }
    }

    @Override
    public ImmutableSet<EnumTearEffects> getTearEffects() {
        return ImmutableSet.copyOf(tearEffects);
    }

    @Override
    public void removeTearEffect(EnumTearEffects type) {
        if (tearEffects.remove(type)) {
            markDirty();
        }
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
            markDirty();
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return IsaacCapabilities.CAPABILITY_PROPERTY.orEmpty(cap, get);
    }

    @Override
    public void copyFrom(LivingEntity entity) {
        IIsaacProperty data = IsaacCapabilities.getProperty(entity);
        size = data.bodySize();
        sizeLocked = data.isBodySizeLocked();
        tearCount = data.tearCount();
        tearSpeed = data.tearSpeed();
        tearSpeedMultiple = data.tearSpeedMultiple();
        tossUpSpeed = data.tossUpSpeed();
        shootDelay = data.shootDelay();
        shootDelayMultiple = data.shootDelayMultiple();
        tearEffects = new HashSet<>(data.getTearEffects());
        tearAppearances = new ArrayList<>(data.getTearAppearances());
        knockBack = data.knockBack();
        range = data.range();
        markDirty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt(KEY_BODY_SIZE, size);
        nbt.putBoolean(KEY_BODY_LOCK, sizeLocked);
        nbt.putInt(KEY_TEAR_COUNT, tearCount);
        nbt.putFloat(KEY_TEAR_SPEED, tearSpeed);
        nbt.putFloat(KEY_TEAR_SPEED_MULTIPLE, tearSpeedMultiple);
        nbt.putFloat(KEY_TOSS_UP_SPEED, tossUpSpeed);
        nbt.putFloat(KEY_SHOOT_DELAY, shootDelay);
        nbt.putFloat(KEY_SHOOT_DELAY_MULTIPLE, shootDelayMultiple);
        writeEffects(nbt);
        writeAppearances(nbt);
        nbt.putInt(KEY_KNOCK_BACK, knockBack);
        nbt.putFloat(KEY_RANGE, range);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        size = nbt.getInt(KEY_BODY_SIZE);
        sizeLocked = nbt.getBoolean(KEY_BODY_LOCK);
        tearCount = nbt.getInt(KEY_TEAR_COUNT);
        tearSpeed = nbt.getFloat(KEY_TEAR_SPEED);
        tearSpeedMultiple = nbt.getFloat(KEY_TEAR_SPEED_MULTIPLE);
        tossUpSpeed = nbt.getFloat(KEY_TOSS_UP_SPEED);
        shootDelay = nbt.getFloat(KEY_SHOOT_DELAY);
        shootDelayMultiple = nbt.getFloat(KEY_SHOOT_DELAY_MULTIPLE);
        readEffects(nbt.getByteArray(KEY_TEAR_EFFECT));
        readAppearances(nbt.getByteArray(KEY_TEAR_APPEARANCE));
        knockBack = nbt.getInt(KEY_KNOCK_BACK);
        range = nbt.getFloat(KEY_RANGE);
        markDirty();
    }

    @Override
    protected void read(PacketBuffer buffer, int version) {
        size = buffer.readVarInt();
        sizeLocked = buffer.readBoolean();
        tearCount = buffer.readVarInt();
        tearSpeed = buffer.readFloat();
        tearSpeedMultiple = buffer.readFloat();
        tossUpSpeed = buffer.readFloat();
        shootDelay = buffer.readFloat();
        shootDelayMultiple = buffer.readFloat();
        readEffects(buffer);
        readAppearances(buffer);
        knockBack = buffer.readVarInt();
        range = buffer.readFloat();
    }

    @Override
    protected void write(PacketBuffer buffer, int version) {
        buffer.writeVarInt(size);
        buffer.writeBoolean(sizeLocked);
        buffer.writeVarInt(tearCount);
        buffer.writeFloat(tearSpeed);
        buffer.writeFloat(tearSpeedMultiple);
        buffer.writeFloat(tossUpSpeed);
        buffer.writeFloat(shootDelay);
        buffer.writeFloat(shootDelayMultiple);
        writeEffects(buffer);
        writeAppearances(buffer);
        buffer.writeVarInt(knockBack);
        buffer.writeFloat(range);
    }

    private void readEffects(byte[] effects) {
        tearEffects = new HashSet<>(effects.length);
        for (byte b : effects) {
            tearEffects.add(EnumTearEffects.get(b));
        }
    }

    private void readEffects(PacketBuffer buffer) {
        tearEffects = new HashSet<>();
        byte b = buffer.readByte();
        while (b != -1) {
            tearEffects.add(EnumTearEffects.get(b));
            b = buffer.readByte();
        }
    }

    private void writeEffects(PacketBuffer buffer) {
        for (EnumTearEffects tearEffect : tearEffects) {
            buffer.writeByte(tearEffect.index);
        }
        buffer.writeByte(-1);
    }

    private void writeEffects(CompoundNBT nbt) {
        byte[] effects = new byte[tearEffects.size()];
        Iterator<EnumTearEffects> iterator = tearEffects.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            effects[i++] = iterator.next().index;
        }
        nbt.putByteArray(KEY_TEAR_EFFECT, effects);
    }

    private void readAppearances(byte[] appearances) {
        tearAppearances = new ArrayList<>(appearances.length);
        for (byte b : appearances) {
            tearAppearances.add(EnumTearAppearances.get(b));
        }
    }

    private void readAppearances(PacketBuffer buffer) {
        tearAppearances = new ArrayList<>();
        byte b = buffer.readByte();
        while (b != -1) {
            tearAppearances.add(EnumTearAppearances.get(b));
            b = buffer.readByte();
        }
    }

    private void writeAppearances(PacketBuffer buffer) {
        for (EnumTearAppearances appearance : tearAppearances) {
            buffer.writeByte(appearance.index);
        }
        buffer.writeByte(-1);
    }

    private void writeAppearances(CompoundNBT nbt) {
        byte[] appearances = new byte[tearAppearances.size()];
        int i = 0;
        for (EnumTearAppearances appearance : tearAppearances) {
            appearances[i] = appearance.index;
        }
        nbt.putByteArray(KEY_TEAR_APPEARANCE, appearances);
    }
}