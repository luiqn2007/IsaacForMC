package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableSet;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class IsaacProperty extends VersionCapability implements IIsaacProperty, ICapabilityProvider {

    private final String KEY_BODY_SIZE = "_size";
    private final String KEY_BODY_LOCK = "_lock";
    private final String KEY_TEAR_COUNT = "_tear_count";
    private final String KEY_TEAR_SPEED = "_tear_speed";
    private final String KEY_SHOOT_DELAY = "_shoot_delay";
    private final String KEY_SHOOT_DELAY_MULTIPLE = "_shoot_delay";
    private final String KEY_TEAR_EFFECT = "_tear_effect";
    private final String KEY_DAMAGE = "_damage";
    private final String KEY_DAMAGE_MULTIPLE = "_damage_multiple";
    private final String KEY_KNOCK_BACK = "_knock_back";

    private int size = 0;
    private boolean sizeLocked = false;
    private int tearCount = 1;
    private float tearSpeed = 1;
    private float shootDelay = 0;
    private float shootDelayMultiple = 1;
    private final Set<EnumTearEffects> tearEffects = new HashSet<>();
    private float damage = 0;
    private float damageMultiple = 1;
    private int knockBack = 0;

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
    public float damage() {
        return damage;
    }

    @Override
    public void damage(float damage) {
        if (damage != this.damage) {
            this.damage = damage;
            markDirty();
        }
    }

    @Override
    public float damageMultiple() {
        return damageMultiple;
    }

    @Override
    public void damageMultiple(float damageMultiple) {
        if (damageMultiple != this.damageMultiple) {
            this.damageMultiple = damageMultiple;
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return IsaacCapabilities.CAPABILITY_PROPERTY.orEmpty(cap, get);
    }

    @Override
    public Void copyFrom(LivingEntity entity) {
        IIsaacProperty data = IsaacCapabilities.getProperty(entity);
        size = data.bodySize();
        tearCount = data.tearCount();
        sizeLocked = data.isBodySizeLocked();
        tearSpeed = data.tearSpeed();
        shootDelay = data.shootDelay();
        shootDelayMultiple = data.shootDelayMultiple();
        damage = data.damage();
        damageMultiple = data.damageMultiple();
        knockBack = data.knockBack();
        markDirty();
        return null;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt(KEY_BODY_SIZE, size);
        nbt.putBoolean(KEY_BODY_LOCK, sizeLocked);
        nbt.putInt(KEY_TEAR_COUNT, tearCount);
        nbt.putFloat(KEY_TEAR_SPEED, tearSpeed);
        nbt.putFloat(KEY_SHOOT_DELAY, shootDelay);
        nbt.putFloat(KEY_SHOOT_DELAY_MULTIPLE, shootDelayMultiple);
        ListNBT effects = new ListNBT();
        for (EnumTearEffects effect : tearEffects) {
            effects.add(StringNBT.valueOf(effect.name()));
        }
        nbt.put(KEY_TEAR_EFFECT, effects);
        nbt.putFloat(KEY_DAMAGE, damage);
        nbt.putFloat(KEY_DAMAGE_MULTIPLE, damageMultiple);
        nbt.putInt(KEY_KNOCK_BACK, knockBack);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        size = nbt.getInt(KEY_BODY_SIZE);
        sizeLocked = nbt.getBoolean(KEY_BODY_LOCK);
        tearCount = nbt.getInt(KEY_TEAR_COUNT);
        tearSpeed = nbt.getFloat(KEY_TEAR_SPEED);
        shootDelay = nbt.getFloat(KEY_SHOOT_DELAY);
        shootDelayMultiple = nbt.getFloat(KEY_SHOOT_DELAY_MULTIPLE);
        ListNBT effects = nbt.getList(KEY_TEAR_EFFECT, Constants.NBT.TAG_STRING);
        tearEffects.clear();
        for (INBT effect : effects) {
            tearEffects.add(EnumTearEffects.valueOf(effect.getString()));
        }
        damage = nbt.getFloat(KEY_DAMAGE);
        damageMultiple = nbt.getFloat(KEY_DAMAGE_MULTIPLE);
        knockBack = nbt.getInt(KEY_KNOCK_BACK);
        markDirty();
    }

    @Override
    protected void read(PacketBuffer buffer, int version) {
        size = buffer.readVarInt();
        sizeLocked = buffer.readBoolean();
        tearCount = buffer.readVarInt();
        tearSpeed = buffer.readFloat();
        shootDelay = buffer.readFloat();
        shootDelayMultiple = buffer.readFloat();
        readEffects(buffer);
        damage = buffer.readFloat();
        damageMultiple = buffer.readFloat();
        knockBack = buffer.readVarInt();
    }

    @Override
    protected void write(PacketBuffer buffer, int version) {
        buffer.writeVarInt(size);
        buffer.writeBoolean(sizeLocked);
        buffer.writeVarInt(tearCount);
        buffer.writeFloat(tearSpeed);
        buffer.writeFloat(shootDelay);
        buffer.writeFloat(shootDelayMultiple);
        writeEffects(buffer);
        buffer.writeFloat(damage);
        buffer.writeFloat(damageMultiple);
        buffer.writeVarInt(knockBack);
    }

    private void readEffects(PacketBuffer buffer) {
        tearEffects.clear();
        for (byte b : buffer.readByteArray()) {
            tearEffects.add(EnumTearEffects.get(b));
        }
    }

    private void writeEffects(PacketBuffer buffer) {
        byte[] effects = new byte[tearEffects.size()];
        Iterator<EnumTearEffects> iterator = tearEffects.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            effects[i++] = iterator.next().index;
        }
        buffer.writeBytes(effects);
    }
}