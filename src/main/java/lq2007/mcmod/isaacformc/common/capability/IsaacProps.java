package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableList;
import lq2007.mcmod.isaacformc.common.capability.data.IPropRecords;
import lq2007.mcmod.isaacformc.common.capability.data.PropRecord;
import lq2007.mcmod.isaacformc.common.entity.friend.manager.FriendType;
import lq2007.mcmod.isaacformc.common.entity.friend.manager.IFriendManager;
import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacformc.common.prop.type.EmptyProp;
import lq2007.mcmod.isaacformc.common.prop.type.Props;
import lq2007.mcmod.isaacformc.common.util.serializer.packet.INBTSerializable;
import lq2007.mcmod.isaacformc.common.util.serializer.packet.NBTData;
import lq2007.mcmod.isaacformc.common.util.serializer.buffer.BufferData;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class IsaacProps implements IIsaacProps, INBTSerializable {

    @BufferData(nullable = true) @NBTData
    private PropRecord activeRecord0 = null;
    @BufferData(nullable = true) @NBTData
    private PropRecord activeRecord1 = null;
    @BufferData @NBTData
    private boolean hasSecondaryActive = false;
    @BufferData(K = AbstractPropType.class, V = PropRecord.class)
    @NBTData(K = AbstractPropType.class, V = PropRecord.class)
    private HashMap<AbstractPropType, PropRecord> propMap = new HashMap<>();
    @BufferData(K = AbstractPropType.class, V = PropRecord.class)
    @NBTData(K = AbstractPropType.class, V = PropRecord.class)
    private HashMap<AbstractPropType, PropRecord> activeMap = new HashMap<>();
    @BufferData(K = FriendType.class, V = IFriendManager.class)
    @NBTData(K = FriendType.class, V = IFriendManager.class)
    private Map<FriendType, IFriendManager> friends = new HashMap<>();

    private final IPropRecords records = new PropRecords();

    @Override
    public Optional<Prop> pickup(Prop prop) {
        if (prop == Prop.EMPTY) {
            return Optional.empty();
        }
        AbstractPropType type = prop.type;
        if (type.isActive()) {
            if (activeRecord0 == null || activeRecord0.isEmpty()) {
                activeRecord0 = getRecord(type);
                activeRecord0.add(prop);
                return Optional.empty();
            } else if (hasSecondaryActive && activeRecord1 == null) {
                activeRecord1 = getRecord(type);
                activeRecord1.add(prop);
                return Optional.empty();
            } else {
                Prop oldActive = activeRecord0.getFirst();
                activeRecord0.clear(false);
                activeRecord0 = getRecord(type);
                activeRecord0.add(prop);
                return Optional.of(oldActive);
            }
        } else {
            return Optional.ofNullable(propMap.computeIfAbsent(type, PropRecord::new).add(prop));
        }
    }

    @Override
    public Optional<Prop> remove(Prop prop, boolean removeRecord) {
        AbstractPropType type = prop.type;
        PropRecord record = getRecord(type);
        Prop oldProp = record.isEmpty() ? null : record.getFirst();
        record.remove(removeRecord);
        if (type.isActive()) {
            if (activeRecord0 != null && activeRecord0.type == type) {
                activeRecord0 = null;
            }
            if (hasSecondaryActive && activeRecord1 != null) {
                if (activeRecord1.type == type) {
                    activeRecord1 = null;
                } else if (activeRecord0 == null) {
                    activeRecord0 = activeRecord1;
                    activeRecord1 = null;
                }
            }
        }
        return Optional.ofNullable(oldProp);
    }

    @Override
    public void clear(boolean removeActive, boolean removeRecord) {
        if (removeActive) {
            clear(activeMap, removeRecord);
        }
        clear(propMap, removeRecord);
    }

    protected void clear(Map<AbstractPropType, PropRecord> map, boolean removeRecord) {
        if (removeRecord) {
            map.values().forEach(record -> record.clear(true));
            map.clear();
        } else {
            map.values().forEach(record -> record.clear(false));
        }
    }

    @Override
    public Optional<Prop> getActive() {
        if (activeRecord0 == null) {
            return Optional.empty();
        } else if (activeRecord0.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(activeRecord0.getFirst());
        }
    }

    @Override
    public boolean contains(AbstractPropType type) {
        if (type == Props.EMPTY) return false;
        return !getRecord(type).isEmpty();
    }

    @Override
    public boolean contains(Class<?> type) {
        if (EmptyProp.class.isAssignableFrom(type)) return false;
        boolean p0 = propMap.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .map(Map.Entry::getKey)
                .anyMatch(type::isInstance);
        if (p0) return true;
        return propMap.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .map(Map.Entry::getKey)
                .anyMatch(type::isInstance)
                || (activeRecord0 != null && type.isInstance(activeRecord0.type))
                || (hasSecondaryActive && activeRecord1 != null && type.isInstance(activeRecord1.type));
    }

    @Override
    public boolean containsRecord(AbstractPropType type) {
        return getRecord(type).isPicked();
    }

    @Override
    public IPropRecords getAllRecords() {
        return records;
    }

    @Override
    public void copy(IIsaacProps source) {
        if (source instanceof IIsaacProps.DummyData) {
            clear(true, true);
            hasSecondaryActive = false;
        } else if (source instanceof IsaacProps) {
            IsaacProps props = (IsaacProps) source;
            activeRecord0 = props.activeRecord0;
            activeRecord1 = props.activeRecord1;
            hasSecondaryActive = props.hasSecondaryActive;
            propMap.clear();
            activeMap.clear();
            propMap.putAll(props.propMap);
            activeMap.putAll(props.activeMap);
        } else {
            IPropRecords records = source.getAllRecords();
            hasSecondaryActive = records.hasSecondaryProp();
            activeRecord0 = records.getMainActiveProp();
            activeRecord1 = records.getSecondaryActiveProp();
            propMap.clear();
            activeMap.clear();
            records.iterPassiveProps(record -> { if (record.isPicked()) propMap.put(record.type, record); });
            records.iterActiveProps(record -> { if (record.isPicked()) activeMap.put(record.type, record); });
        }
    }

    @Override
    public boolean hasSecondaryActive() {
        return hasSecondaryActive;
    }

    @Override
    public void switchActive() {
        if (hasSecondaryActive && activeRecord1 != null) {
            PropRecord ar = activeRecord0;
            activeRecord0 = activeRecord1;
            activeRecord1 = ar;
        }
    }

    @Override
    public Map<FriendType, IFriendManager> getFriends() {
        return friends;
    }

    @Override
    public IFriendManager getFriends(FriendType type) {
        return friends.getOrDefault(type, IFriendManager.EMPTY);
    }

    private PropRecord getRecord(AbstractPropType type) {
        Map<AbstractPropType, PropRecord> map = type.isActive() ? activeMap : propMap;
        return map.computeIfAbsent(type, PropRecord::new);
    }

    public class PropRecords implements IPropRecords {

        @Override
        public boolean hasSecondaryProp() {
            return hasSecondaryActive;
        }

        @Nullable
        @Override
        public PropRecord getMainActiveProp() {
            return activeRecord0;
        }

        @Nullable
        @Override
        public PropRecord getSecondaryActiveProp() {
            return activeRecord1;
        }

        @Override
        public ImmutableList<PropRecord> getPassiveRecords() {
            return ImmutableList.copyOf(propMap.values());
        }

        @Override
        public ImmutableList<PropRecord> getActiveRecords() {
            return ImmutableList.copyOf(activeMap.values());
        }

        @Override
        public void iterPassiveProps(Consumer<PropRecord> records) {
            propMap.values().forEach(records);
        }

        @Override
        public void iterActiveProps(Consumer<PropRecord> records) {
            activeMap.values().forEach(records);
        }
    }
}
