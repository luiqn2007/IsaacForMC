package lq2007.mcmod.isaacmod.common.capability;

import com.google.common.collect.ImmutableList;
import lq2007.mcmod.isaacmod.common.capability.data.PropRecord;
import lq2007.mcmod.isaacmod.common.capability.data.IPropRecords;
import lq2007.mcmod.isaacmod.common.entity.friend.manager.FriendType;
import lq2007.mcmod.isaacmod.common.entity.friend.manager.IFriendManager;
import lq2007.mcmod.isaacmod.common.util.serializer.buffer.IPacketSerializable;
import lq2007.mcmod.isaacmod.common.prop.Prop;
import lq2007.mcmod.isaacmod.common.prop.type.AbstractPropType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Capability saves held props and records.
 */
public interface IIsaacProps extends ICapabilitySerializable<CompoundNBT>, IPacketSerializable {

    static IIsaacProps dummy() {
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
     * True if we can use secondary active prop.
     * @return secondary active prop
     */
    boolean hasSecondaryActive();

    /**
     * Swap the secondary active prop and the main active prop
     */
    void switchActive();

    /**
     * <p>Returns the first active prop.
     * <p>If not exist, return {@link Prop#EMPTY}
     *
     * @return An active prop
     */
    Optional<Prop> getActive();

    /**
     * Return a prop by type.
     * @param type type
     * @return prop
     */
    Prop getProp(AbstractPropType type);

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
     * @return All prop records
     */
    IPropRecords getAllRecords();

    /**
     * Copy values from another data.
     * @param source another data
     */
    void copy(IIsaacProps source);

    /**
     * Get all friends
     * @return friends
     */
    Map<FriendType, IFriendManager> getFriends();

    /**
     * Get all friends with type
     * @return friends
     */
    IFriendManager getFriends(FriendType type);

    @Nonnull
    @Override
    default <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return CapabilityRegister.CAPABILITY_PROPS.orEmpty(cap, LazyOptional.of(() -> this));
    }

    class DummyData implements IIsaacProps {

        private static final DummyData INSTANCE = new DummyData();
        private IPropRecords records;

        public DummyData() {
            this.records = new IPropRecords() {
                @Override
                public boolean hasSecondaryProp() {
                    return false;
                }

                @Nullable
                @Override
                public PropRecord getMainActiveProp() {
                    return null;
                }

                @Nullable
                @Override
                public PropRecord getSecondaryActiveProp() {
                    return null;
                }

                @Override
                public ImmutableList<PropRecord> getPassiveRecords() {
                    return ImmutableList.of();
                }

                @Override
                public ImmutableList<PropRecord> getActiveRecords() {
                    return ImmutableList.of();
                }

                @Override
                public void iterPassiveProps(Consumer<PropRecord> records) { }

                @Override
                public void iterActiveProps(Consumer<PropRecord> records) { }
            };
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
        public Prop getProp(AbstractPropType type) {
            return new Prop();
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
        public IPropRecords getAllRecords() {
            return this.records = records;
        }

        @Override
        public void copy(IIsaacProps source) { }

        @Override
        public boolean hasSecondaryActive() {
            return false;
        }

        @Override
        public void switchActive() { }

        @Override
        public Map<FriendType, IFriendManager> getFriends() {
            return new HashMap<>();
        }

        @Override
        public IFriendManager getFriends(FriendType type) {
            return IFriendManager.EMPTY;
        }

        @Override
        public void read(PacketBuffer buffer) { }

        @Override
        public void write(PacketBuffer buffer) { }

        @Override
        public CompoundNBT serializeNBT() {
            return new CompoundNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) { }
    }
}
