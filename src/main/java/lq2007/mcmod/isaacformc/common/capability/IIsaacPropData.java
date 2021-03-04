package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lq2007.mcmod.isaacformc.common.capability.data.IsaacFriends;
import lq2007.mcmod.isaacformc.common.network.IPacketReader;
import lq2007.mcmod.isaacformc.common.network.IPacketWriter;
import lq2007.mcmod.isaacformc.common.prop.PropItem;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;
import java.util.function.Predicate;

public interface IIsaacPropData extends INBTSerializable<CompoundNBT>,
        ICopyFromEntity<Collection<PropItem>>, IDirtyData, IPacketReader, IPacketWriter {

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
    PropItem pickupProp(PropItem prop);

    /**
     * Pickup props once.
     * <li>If an entity can't pickup any prop, collect them and return.
     * <li>If this is an active prop, replaced active prop while be added to return.
     *
     * @param props prop
     * @return The props entity picked up then replaced or removed.
     */
    Collection<PropItem> pickupProps(Collection<PropItem> props);

    /**
     * Remove a prop
     *
     * @param prop prop
     * @return The item removed. If no one is removed, return {@link PropItem#EMPTY}
     */
    PropItem removeProp(PropItem prop);

    /**
     * Remove all props
     *
     * @param removeActiveProp True if remove active props
     * @param clearHeldPropRecord True if clear the records of hold prop items
     * @return The items removed.
     */
    Collection<PropItem> removeAllProps(boolean removeActiveProp, boolean clearHeldPropRecord);

    /**
     * <p>Returns the first active prop.
     * <p>If not exist, return {@link PropItem#EMPTY}
     *
     * @return An active prop
     */
    PropItem getActiveProp();

    /**
     * <p>Switch the first active prop.
     * <p>If entity has second active prop slot, it will swap slot0 and slot1.
     */
    void switchActiveProp();

    /**
     * Return if allowed held the second active prop.
     *
     * @return True if the second active prop slot is enabled.
     */
    boolean hasSecondActive();

    /**
     * Set if allowed held the second active prop.
     *
     * @param second True if the second active prop slot is enabled.
     * @return If second is false and has second item, return it.
     */
    PropItem setHasSecondAction(boolean second);

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
     * Check if entity has item implemented the condition.
     * @param condition condition
     * @return True if entity has the prop.
     */
    boolean containsIf(Predicate<PropItem> condition);

    /**
     * Check if entity has specified type of item implemented the condition.
     * @param condition condition
     * @return True if entity has the prop.
     */
    boolean containsTypeIf(Predicate<AbstractPropType> condition);

    /**
     * Check if entity ever received the specified type of item,
     * @param type prop type
     * @return True if entity ever hold or is holding the type of item.
     */
    boolean isHold(AbstractPropType type);

    /**
     * <p>Get all prop types the entity picked up.
     * <p>It contains props entity picked up but removed.
     *
     * @return All prop type
     */
    ImmutableSet<AbstractPropType> getAllHeldProps();

    /**
     * Get all props contains active props.
     *
     * @return All props
     */
    ImmutableList<PropItem> getAllProps();

    /**
     * Get all prop types contains active props.
     *
     * @return All prop types
     */
    ImmutableList<AbstractPropType> getAllPropTypes();

    /**
     * Get all friends
     * @return friends
     */
    IsaacFriends getOrCreateFriends(IsaacFriends.Type type);

    /**
     * Get all friend types.
     * @return types
     */
    ImmutableList<IsaacFriends.Type> getFriendTypes();

    class DummyData implements IIsaacPropData {

        private static final DummyData INSTANCE = new DummyData();

        @Override
        public PropItem pickupProp(PropItem prop) {
            return prop;
        }

        @Override
        public Collection<PropItem> pickupProps(Collection<PropItem> props) {
            return Collections.emptySet();
        }

        @Override
        public PropItem removeProp(PropItem prop) {
            return PropItem.EMPTY;
        }

        @Override
        public Collection<PropItem> removeAllProps(boolean removeActiveProp, boolean clearHeldPropRecord) {
            return Collections.emptySet();
        }

        @Override
        public PropItem getActiveProp() {
            return PropItem.EMPTY;
        }

        @Override
        public void switchActiveProp() { }

        @Override
        public boolean hasSecondActive() {
            return false;
        }

        @Override
        public PropItem setHasSecondAction(boolean second) {
            return PropItem.EMPTY;
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
        public boolean containsIf(Predicate<PropItem> condition) {
            return false;
        }

        @Override
        public boolean containsTypeIf(Predicate<AbstractPropType> condition) {
            return false;
        }

        @Override
        public boolean isHold(AbstractPropType type) {
            return false;
        }

        @Override
        public ImmutableSet<AbstractPropType> getAllHeldProps() {
            return ImmutableSet.of();
        }

        @Override
        public ImmutableList<PropItem> getAllProps() {
            return ImmutableList.of();
        }

        @Override
        public ImmutableList<AbstractPropType> getAllPropTypes() {
            return ImmutableList.of();
        }

        @Override
        public IsaacFriends getOrCreateFriends(IsaacFriends.Type type) {
            return IsaacFriends.NONE.newInstance();
        }

        @Override
        public ImmutableList<IsaacFriends.Type> getFriendTypes() {
            return ImmutableList.of(IsaacFriends.NONE);
        }

        @Override
        public void copyFrom(LivingEntity entity) {
        }

        @Override
        public void markDirty() { }

        @Override
        public boolean isDirty() {
            return false;
        }

        @Override
        public CompoundNBT serializeNBT() {
            return new CompoundNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) { }

        @Override
        public void read(PacketBuffer buffer) {
            buffer.readByte();
        }

        @Override
        public void write(PacketBuffer buffer) {
            buffer.writeByte(0);
        }
    }
}
