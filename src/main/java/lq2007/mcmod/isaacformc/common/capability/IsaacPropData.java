package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lq2007.mcmod.isaacformc.common.data.IsaacFriends;
import lq2007.mcmod.isaacformc.common.util.NBTUtils;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.PropTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

public class IsaacPropData extends VersionCapability implements IIsaacPropData, ICapabilityProvider {

    private static final String KEY_ACT0 = "_act0";
    private static final String KEY_ACT1 = "_act1";
    private static final String KEY_HAS_ACT2 = "_has2";
    private static final String KEY_ITEMS = "_items";
    private static final String KEY_FRIENDS = "_friends";
    private static final String KEY_TYPE = "_type";
    private static final String KEY_DATA = "_data";

    private PropItem active0 = PropItem.EMPTY;
    private PropItem active1 = PropItem.EMPTY;
    private boolean hasSecondActive = false;

    private final Map<PropType<?>, List<PropItem>> itemMap = new HashMap<>();
    private final ArrayList<PropItem> itemList = new ArrayList<>();
    private final ArrayList<PropType<?>> itemTypeList = new ArrayList<>();
    private final Map<IsaacFriends.Type, IsaacFriends> friendsMap = new HashMap<>();

    private final LazyOptional<IIsaacPropData> dataOptional = LazyOptional.of(() -> this);

    @Override
    public PropItem getActiveProp() {
        return active0;
    }

    @Override
    public void switchActiveProp() {
        if (hasSecondActive() && active1 != PropItem.EMPTY) {
            PropItem temp = active0;
            active0 = active1;
            active1 = temp;
            markDirty();
        }
    }

    @Override
    public PropItem pickupProp(PropItem prop) {
        if (prop == PropItem.EMPTY) {
            return PropItem.EMPTY;
        }

        PropItem returnItem;
        PropType<?> type = prop.type;
        if (type.isActive()) {
            // active item
            if (active0 == PropItem.EMPTY) {
                // no active item
                active0 = prop;
                returnItem = PropItem.EMPTY;
            } else if (hasSecondActive()) {
                // enable second
                if (active1 == PropItem.EMPTY) {
                    // no second
                    active1 = active0;
                    active0 = prop;
                    returnItem = PropItem.EMPTY;
                } else {
                    // has second
                    returnItem = active0;
                    active0 = prop;
                }
            } else {
                // only one item
                returnItem = active0;
                active0 = prop;
            }
        } else {
            returnItem = PropItem.EMPTY;
        }

        // picked up
        if (returnItem != prop) {
            itemMap.computeIfAbsent(type, t -> new ArrayList<>()).add(prop);
            itemList.add(prop);
            itemTypeList.add(type);
            markDirty();
        }
        return returnItem;
    }

    @Override
    public Collection<PropItem> pickupProps(Collection<PropItem> props) {
        return props.stream()
                .map(this::pickupProp)
                .filter(item -> item != PropItem.EMPTY)
                .collect(Collectors.toList());
    }

    @Override
    public PropItem removeProp(PropItem prop) {
        if (prop == PropItem.EMPTY) {
            return PropItem.EMPTY;
        }
        if (itemList.remove(prop)) {
            PropType<?> type = prop.type;
            itemTypeList.remove(type);
            if (type.isActive()) {
                if (active0 == prop) {
                    active0 = active1;
                    active1 = PropItem.EMPTY;
                } else if (active1 == prop) {
                    active1 = PropItem.EMPTY;
                }
            }
            itemMap.get(type).remove(prop);
            markDirty();
            return prop;
        }
        return PropItem.EMPTY;
    }

    @Override
    public Collection<PropItem> removeAllProps(boolean removeActiveProp, boolean clearHeldPropRecord) {
        Collection<PropItem> items = ImmutableList.copyOf(itemList);
        itemList.clear();
        itemTypeList.clear();
        if (removeActiveProp) {
            active0 = PropItem.EMPTY;
            active1 = PropItem.EMPTY;
        } else {
            if (active0 != PropItem.EMPTY) {
                itemList.add(active0);
                itemTypeList.add(active0.type);
            }
            if (active1 != PropItem.EMPTY) {
                itemList.add(active1);
                itemTypeList.add(active1.type);
            }
        }
        if (clearHeldPropRecord) {
            itemMap.clear();
        } else {
            itemMap.values().forEach(List::clear);
        }
        return items;
    }

    @Override
    public boolean hasSecondActive() {
        return hasSecondActive;
    }

    @Override
    public PropItem setHasSecondAction(boolean hasSecondActive) {
        PropItem removedItem = PropItem.EMPTY;
        if (this.hasSecondActive && !hasSecondActive) {
            removeProp(active1);
            this.hasSecondActive = false;
            removedItem = this.active1;
            this.active1 = PropItem.EMPTY;
            markDirty();
        } else if (!this.hasSecondActive && hasSecondActive) {
            this.hasSecondActive = true;
            removedItem = this.active1;
            this.active1 = PropItem.EMPTY;
            markDirty();
        }
        return removedItem;
    }

    @Override
    public boolean contains(PropType<?> type) {
        return itemTypeList.contains(type);
    }

    @Override
    public boolean contains(Class<?> type) {
        for (PropType<?> propType : itemTypeList) {
            if (type.isInstance(propType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsIf(Predicate<PropItem> condition) {
        for (PropItem propItem : itemList) {
            if (condition.test(propItem)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsTypeIf(Predicate<PropType<?>> condition) {
        for (PropType<?> propType : itemTypeList) {
            if (condition.test(propType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isHold(PropType<?> type) {
        return itemMap.containsKey(type);
    }

    @Override
    public ImmutableSet<PropType<?>> getAllHeldProps() {
        return ImmutableSet.copyOf(itemMap.keySet());
    }

    @Override
    public ImmutableList<PropItem> getAllProps() {
        return ImmutableList.copyOf(itemList);
    }

    @Override
    public ImmutableList<PropType<?>> getAllPropTypes() {
        return ImmutableList.copyOf(itemTypeList);
    }

    @Override
    public IsaacFriends getOrCreateFriends(IsaacFriends.Type type) {
        if (type == IsaacFriends.NONE) {
            return type.newInstance();
        }
        if (friendsMap.containsKey(type)) {
            return friendsMap.get(type);
        }
        IsaacFriends newFriends = type.newInstance();
        friendsMap.put(type, newFriends);
        return newFriends;
    }

    @Override
    public ImmutableList<IsaacFriends.Type> getFriendTypes() {
        return ImmutableList.copyOf(friendsMap.keySet());
    }

    @Override
    public void copyFrom(LivingEntity entity) {
        IIsaacPropData data = IsaacCapabilities.getPropData(entity);
        Collection<PropItem> propItems = removeAllProps(true, true);
        if (data instanceof IsaacPropData) {
            IsaacPropData pData = (IsaacPropData) data;
            active0 = pData.active0;
            active1 = pData.active1;
            hasSecondActive = pData.hasSecondActive;
            itemMap.putAll(pData.itemMap);
            itemList.addAll(pData.itemList);
            itemTypeList.addAll(pData.itemTypeList);
        } else if (!(data instanceof DummyData)) {
            for (PropType<?> type : data.getAllHeldProps()) {
                itemMap.put(type, new ArrayList<>());
            }
            pickupProps(data.getAllProps());
        }
        markDirty();
    }

    @Override
    protected void read(PacketBuffer buffer, int version) {
        removeAllProps(true, true);
        hasSecondActive = buffer.readBoolean();
        int propItemCount = buffer.readVarInt();
        for (int i = 0; i < propItemCount; i++) {
            pickupProp(PropItem.fromPacket(buffer));
        }
        int heldItemCount = buffer.readVarInt();
        for (int i = 0; i < heldItemCount; i++) {
            PropTypes.get(buffer.readResourceLocation())
                    .ifPresent(type -> itemMap.put(type, new ArrayList<>()));
        }
    }

    @Override
    protected void write(PacketBuffer buffer, int version) {
        buffer.writeBoolean(hasSecondActive);
        buffer.writeVarInt(itemList.size());
        itemList.forEach(item -> item.write(buffer));
        ResourceLocation[] locations = itemMap.entrySet().stream()
                .filter(entry -> entry.getValue().isEmpty())
                .map(Map.Entry::getKey)
                .map(type -> type.key)
                .toArray(ResourceLocation[]::new);
        buffer.writeVarInt(locations.length);
        for (ResourceLocation location : locations) {
            buffer.writeResourceLocation(location);
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put(KEY_ACT0, active0.serializeNBT());
        nbt.put(KEY_ACT1, active1.serializeNBT());
        nbt.putBoolean(KEY_HAS_ACT2, hasSecondActive);
        nbt.put(KEY_ITEMS, NBTUtils.convert(itemMap.entrySet(), entry -> {
            CompoundNBT data = new CompoundNBT();
            data.putString(KEY_TYPE, entry.getKey().key.toString());
            data.put(KEY_DATA, NBTUtils.convert(entry.getValue(), PropItem::serializeNBT));
            return data;
        }));
        nbt.put(KEY_FRIENDS, NBTUtils.convert(friendsMap.entrySet(), entry -> {
            String type = entry.getKey().getName();
            CompoundNBT data = new CompoundNBT();
            data.putString(KEY_TYPE, type);
            data.put(KEY_DATA, entry.getValue().serializeNBT());
            return data;
        }));
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT data) {
        removeAllProps(true, true);
        active0 = PropItem.fromNbt(data.getCompound(KEY_ACT0));
        active1 = PropItem.fromNbt(data.getCompound(KEY_ACT1));
        hasSecondActive = data.getBoolean(KEY_HAS_ACT2);
        NBTUtils.<CompoundNBT, PropType<?>, List<PropItem>>convert(data.getList(KEY_ITEMS, TAG_COMPOUND), itemMap, (nbt, map) -> {
            ResourceLocation key = new ResourceLocation(nbt.getString(KEY_TYPE));
            PropTypes.get(key).ifPresent(propType -> {
                ListNBT list = nbt.getList(KEY_DATA, TAG_COMPOUND);
                map.put(propType, NBTUtils.convert(list, PropItem::fromNbt));
            });
        });
        NBTUtils.<CompoundNBT, IsaacFriends.Type, IsaacFriends>convert(data.getList(KEY_FRIENDS, TAG_COMPOUND), friendsMap, (nbt, map) -> {
            String t = nbt.getString(KEY_TYPE);
            CompoundNBT d = nbt.getCompound(KEY_DATA);
            IsaacFriends.Type type = IsaacFriends.TYPES.getOrDefault(t, null);
            if (type != null) {
                IsaacFriends friends = type.newInstance();
                friends.deserializeNBT(d);
                map.put(type, friends);
            }
        });
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return IsaacCapabilities.CAPABILITY_PROP.orEmpty(cap, this.dataOptional);
    }
}
