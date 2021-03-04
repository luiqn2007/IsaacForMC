package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableList;
import lq2007.mcmod.isaacformc.common.capability.data.PropRecord;
import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IsaacPropData implements IIsaacPropData {

    private static final String KEY_ACT = "_act";
    private static final String KEY_ITEMS = "_items";
    private static final String KEY_FRIENDS = "_friends";
    private static final String KEY_TYPE = "_type";
    private static final String KEY_DATA = "_data";

    private boolean isDirty = false;
    private PropRecord activeRecord = null;
    private final Map<AbstractPropType, PropRecord> itemMap = new HashMap<>();

    @Override
    public void copyFrom(LivingEntity entity) {
        IIsaacPropData data = IsaacCapabilities.getPropData(entity);
        if (data instanceof IIsaacPropData.DummyData) {
            // todo clear all
        } else if (data instanceof IsaacPropData) {
            // todo copy this
        } else {
            // todo copy other
        }
    }

    @Override
    public void markDirty() {
        isDirty = true;
    }

    @Override
    public boolean isDirty() {
        return isDirty;
    }

    @Override
    public Optional<Prop> pickup(Prop prop) {

    }

    @Override
    public Optional<Prop> remove(Prop prop, boolean removeRecord) {
        return Optional.empty();
    }

    @Override
    public void clear(boolean removeActive, boolean removeRecord) {

    }

    @Override
    public Optional<Prop> getActive() {
        return Optional.empty();
    }

    @Override
    public boolean contains(AbstractPropType type) {
        return false;
    }

    @Override
    public boolean contains(Class<?> type) {
        return false;
    }

    @Override
    public boolean containsRecord(AbstractPropType type) {
        return false;
    }

    @Override
    public ImmutableList<PropRecord> getAllRecords(boolean containsActive) {
        return null;
    }

    @Override
    public void read(PacketBuffer buffer) {

    }

    @Override
    public void write(PacketBuffer buffer) {

    }

    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}
