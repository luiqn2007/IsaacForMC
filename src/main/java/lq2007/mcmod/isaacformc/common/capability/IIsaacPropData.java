package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lq2007.mcmod.isaacformc.common.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.common.isaac.prop.type.PropType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IIsaacPropData extends INBTSerializable<CompoundNBT> {

    static IIsaacPropData dummy(LivingEntity entity) {
        DummyData.INSTANCE.bindEntity(entity);
        return DummyData.INSTANCE;
    }

    /**
     * Bind the data to an entity.
     *
     * @param entity entity
     */
    void bindEntity(LivingEntity entity);

    /**
     * Get the entity the data bind to.
     *
     * @return entity
     */
    LivingEntity getEntity();

    /**
     * Pickup a prop
     *
     * If an entity can't pickup the prop, returns itself.
     * If this is an active prop, returns replaced active prop while exist.
     *
     * @param prop prop
     * @return The prop that an entity pickup a prop and remove one.
     */
    PropItem pickupProp(PropItem prop);

    /**
     * Remove a prop
     *
     * @param prop prop
     * @return True if the prop removed from the entity.
     */
    boolean removeProp(PropItem prop);

    /**
     * Remove all props
     *
     * @param removeActiveProp True if remove active props
     * @return The count of props removed.
     */
    int removeAllProps(boolean removeActiveProp);

    /**
     * Returns the first active prop.
     * If not exist, return {@link PropItem#EMPTY}
     *
     * @return An active prop
     */
    PropItem getActiveProp();

    /**
     * Switch the first active prop.
     *
     * If entity has second active prop slot, it will swap slot0 and slot1.
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
     *  @param second True if the second active prop slot is enabled.
     *
     */
    void setHasSecondAction(boolean second);

    /**
     * Get all prop types the entity picked up.
     * It contains props entity picked up but removed.
     *
     * @return All prop type
     */
    ImmutableSet<PropType> getAllHeldProps();

    /**
     * Get all passive props the entity hold, exclude active props.
     *
     * @return All passive props
     */
    ImmutableList<PropItem> getAllPassiveProps();

    /**
     * Get all props contains active props.
     *
     * @return All props
     */
    ImmutableList<PropItem> getAllProps();

    /**
     * Copy props from another data.
     *  @param data another data
     *
     */
    void copyFrom(IIsaacPropData data);

    /**
     * Called in {@link net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent}
     *
     * 1 If the data is created but not applied to entity, apply it.
     * 2 Update all prop implement {@link lq2007.mcmod.isaacformc.common.isaac.prop.type.IUpdateType}
     *
     */
    void update();

    class DummyData implements IIsaacPropData {

        private static final DummyData INSTANCE = new DummyData();

        private LivingEntity entity;

        @Override
        public void bindEntity(LivingEntity entity) {
            this.entity = entity;
        }

        @Override
        public LivingEntity getEntity() {
            return entity;
        }

        @Override
        public PropItem pickupProp(PropItem prop) {
            return prop;
        }

        @Override
        public boolean removeProp(PropItem prop) {
            return false;
        }

        @Override
        public int removeAllProps(boolean removeActiveProp) {
            return 0;
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
        public void setHasSecondAction(boolean second) { }

        @Override
        public ImmutableSet<PropType> getAllHeldProps() {
            return ImmutableSet.of();
        }

        @Override
        public ImmutableList<PropItem> getAllPassiveProps() {
            return ImmutableList.of();
        }

        @Override
        public ImmutableList<PropItem> getAllProps() {
            return ImmutableList.of();
        }

        @Override
        public void copyFrom(IIsaacPropData data) { }

        @Override
        public void update() { }

        @Override
        public CompoundNBT serializeNBT() {
            return new CompoundNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) { }
    }
}
