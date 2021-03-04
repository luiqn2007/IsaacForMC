package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableList;
import lq2007.mcmod.isaacformc.common.capability.data.PropRecord;
import lq2007.mcmod.isaacformc.common.network.ISynchronized;
import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

/**
 * Capability saves held props and records.
 */
public interface IIsaacPropData extends ICapabilitySerializable<CompoundNBT>, ICopyFromEntity<Collection<Prop>>, IDirtyData, ISynchronized {

    static IIsaacPropData dummy() {
        return DummyData.INSTANCE;
    }

    /**
     * Pickup a prop
     * <li>If an entity can't pickup the prop, returns itself.
     * <li>If this is an active prop, returns replaced active prop while exist.
     *
     * @param prop prop
     * @return The prop that an entity pickup a prop and remove one.
     */
    Optional<Prop> pickup(Prop prop);

    /**
     * Remove a prop
     *
     * @param prop prop
     * @param removeRecord True if should remove the record if no hold.
     * @return The item removed. If no one is removed, return {@link Prop#EMPTY}
     */
    Optional<Prop> remove(Prop prop, boolean removeRecord);

    /**
     * Remove all props
     *
     * @param removeActive True if remove active props
     * @param removeRecord True if clear the records of hold prop items
     */
    void clear(boolean removeActive, boolean removeRecord);

    /**
     * <p>Returns the first active prop.
     * <p>If not exist, return {@link Prop#EMPTY}
     *
     * @return An active prop
     */
    Optional<Prop> getActive();

    /**
     * Check if entity has specified type of item.
     * @param type the prop type
     * @return True if entity has the prop.
     */
    boolean contains(AbstractPropType type);

    /**
     * Check if entity has item type implement class.
     * @param type the prop type class
     * @return True if entity has the prop.
     */
    boolean contains(Class<?> type);

    /**
     * Check if entity ever received the specified type of item,
     * @param type prop type
     * @return True if entity ever hold or is holding the type of item.
     */
    boolean containsRecord(AbstractPropType type);

    /**
     * Get all prop records.
     *
     * @param containsActive True if the list contains active prop. If have, it will be first.
     * @return All prop records
     */
    ImmutableList<PropRecord> getAllRecords(boolean containsActive);

    @Nonnull
    @Override
    default <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return IsaacCapabilities.CAPABILITY_PROP.orEmpty(cap, LazyOptional.of(() -> this));
    }

    class DummyData implements IIsaacPropData {

        private static final DummyData INSTANCE = new DummyData();

        @Override
        public void copyFrom(LivingEntity entity) {

        }

        @Override
        public void markDirty() {

        }

        @Override
        public boolean isDirty() {
            return false;
        }

        @Override
        public Optional<Prop> pickup(Prop prop) {
            return Optional.empty();
        }

        @Override
        public Optional<Prop> remove(Prop prop, boolean removeRecord) {
            return Optional.empty();
        }

        @Override
        public void clear(boolean removeActive, boolean removeRecord) { }

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
            return ImmutableList.of();
        }

        @Override
        public void read(PacketBuffer buffer) {

        }

        @Override
        public void write(PacketBuffer buffer) {

        }

        @Override
        public CompoundNBT serializeNBT() {
            return new CompoundNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {

        }
    }
}
