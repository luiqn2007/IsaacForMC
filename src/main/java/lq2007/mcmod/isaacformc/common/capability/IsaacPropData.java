package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lq2007.mcmod.isaacformc.common.util.NBTUtil;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.PropTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

public class IsaacPropData extends VersionCapability implements IIsaacPropData, ICapabilityProvider {

    private static final String KEY_ACT0 = "_act0";
    private static final String KEY_ACT1 = "_act1";
    private static final String KEY_HAS_ACT2 = "_has2";
    private static final String KEY_PASSIVE = "_passive";
    private static final String KEY_HELD = "_held";

    private PropItem active0 = PropItem.EMPTY;
    private PropItem active1 = PropItem.EMPTY;
    private boolean hasSecondActive = false;
    private List<PropItem> passiveItems = new ArrayList<>();
    private Set<PropType> passiveTypes = new HashSet<>();
    private Set<PropType> heldTypes = new HashSet<>();

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
        PropItem replacedItem;
        if (prop.type.isActive()) {
            if (active0 == PropItem.EMPTY) {
                active0 = prop;
                replacedItem = PropItem.EMPTY;
            } else {
                replacedItem = removeProp(active0);
                if (replacedItem == PropItem.EMPTY) {
                    replacedItem = prop;
                } else {
                    if (hasSecondActive() && active1 == PropItem.EMPTY) {
                        active1 = active0;
                    }
                    active0 = prop;
                }
            }
        } else if (!passiveTypes.contains(prop.type)) {
            passiveItems.add(prop);
            passiveTypes.add(prop.type);
            replacedItem = PropItem.EMPTY;
        } else {
            passiveItems.add(prop);
            replacedItem = PropItem.EMPTY;
        }

        if (replacedItem != prop) {
            heldTypes.add(prop.type);
            markDirty();
        }
        return replacedItem;
    }

    @Override
    public PropItem removeProp(PropItem prop) {
        if (prop == PropItem.EMPTY) {
            return PropItem.EMPTY;
        }
        PropType type = prop.type;
        boolean isRemoved = false;
        if (type.isActive()) {
            if (active0 == prop) {
                active0 = active1;
                active1 = PropItem.EMPTY;
                isRemoved = true;
            } else if (active1 == prop) {
                active1 = PropItem.EMPTY;
                isRemoved = true;
            }
        } else if (passiveItems.remove(prop)) {
            boolean remove = true;
            for (PropItem passiveItem : passiveItems) {
                if (passiveItem.type == type) {
                    remove = false;
                    break;
                }
            }
            if (remove) {
                passiveTypes.remove(type);
            }
            isRemoved = true;
        }
        if (isRemoved) {
            markDirty();
            return prop;
        } else {
            return PropItem.EMPTY;
        }
    }

    @Override
    public Collection<PropItem> removeAllProps(boolean removeActiveProp, boolean clearHeldPropRecord) {
        ImmutableList.Builder<PropItem> builder = new ImmutableList.Builder<>();
        if (removeActiveProp) {
            if (hasSecondActive() && removeProp(active1) != PropItem.EMPTY) {
                builder.add(active1);
            }
            if (removeProp(active0) != PropItem.EMPTY) {
                builder.add(active0);
            }
        }
        for (PropItem prop : getAllPassiveProps()) {
            if (removeProp(prop) != PropItem.EMPTY) {
                builder.add(prop);
            }
        }
        if (clearHeldPropRecord) {
            // todo may be change some data
            heldTypes.clear();
        }
        markDirty();
        return builder.build();
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
    public ImmutableSet<PropType> getAllHeldProps() {
        return ImmutableSet.copyOf(heldTypes);
    }

    @Override
    public ImmutableList<PropItem> getAllPassiveProps() {
        return ImmutableList.copyOf(passiveItems);
    }

    @Override
    public ImmutableList<PropItem> getAllProps() {
        ImmutableList.Builder<PropItem> builder = new ImmutableList.Builder<>();
        if (active0 != PropItem.EMPTY) {
            builder.add(active0);
            if (hasSecondActive() && active1 != PropItem.EMPTY) {
                builder.add(active1);
            }
        }
        return builder.addAll(passiveItems).build();
    }

    @Override
    public Collection<PropItem> copyFrom(LivingEntity entity) {
        IIsaacPropData data = IsaacCapabilities.getPropData(entity);
        Collection<PropItem> propItems = removeAllProps(true, true);
        if (data instanceof IsaacPropData) {
            IsaacPropData pData = (IsaacPropData) data;
            active0 = pData.active0;
            active1 = pData.active1;
            hasSecondActive = pData.hasSecondActive;
            passiveItems = new ArrayList<>(pData.passiveItems);
            passiveTypes = new HashSet<>(pData.passiveTypes);
            heldTypes  = new HashSet<>(pData.heldTypes);
        } else if (!(data instanceof DummyData)) {
            active0 = PropItem.EMPTY;
            active1 = PropItem.EMPTY;
            hasSecondActive = false;
            passiveItems = new ArrayList<>();
            passiveTypes = new HashSet<>();
            heldTypes = new HashSet<>(data.getAllHeldProps());
            PropItem firstActiveProp = PropItem.EMPTY;
            for (PropItem prop : getAllProps()) {
                if (prop.type.isActive() && firstActiveProp == PropItem.EMPTY) {
                    firstActiveProp = prop;
                } else {
                    pickupProp(prop);
                }
            }
            setHasSecondAction(data.hasSecondActive());
            pickupProp(firstActiveProp);
        }
        markDirty();
        return propItems;
    }

    @Override
    protected void read(PacketBuffer buffer, int version) {
        hasSecondActive = buffer.readBoolean();
        active0 = PropItem.fromPacket(buffer);
        active1 = hasSecondActive ? PropItem.fromPacket(buffer) : PropItem.EMPTY;
        int passiveItemCount = buffer.readVarInt();
        passiveItems = new ArrayList<>(passiveItemCount);
        for (int i = 0; i < passiveItemCount; i++) {
            passiveItems.add(PropItem.fromPacket(buffer));
        }
        passiveTypes = new HashSet<>();
        for (PropItem passiveItem : passiveItems) {
            passiveTypes.add(passiveItem.type);
        }
        int heldCount = buffer.readVarInt();
        heldTypes = new HashSet<>(passiveItems.size() + heldCount + 2);
        heldTypes.addAll(passiveTypes);
        if (active0 != PropItem.EMPTY) {
            heldTypes.add(active0.type);
        }
        if (active1 != PropItem.EMPTY) {
            heldTypes.add(active1.type);
        }
        for (int i = 0; i < heldCount; i++) {
            PropTypes.get(buffer.readResourceLocation())
                    .filter(type -> type != PropTypes.EMPTY)
                    .ifPresent(heldTypes::add);
        }
    }

    @Override
    protected void write(PacketBuffer buffer, int version) {
        buffer.writeBoolean(hasSecondActive);
        active0.write(buffer);
        if (hasSecondActive) {
            active1.write(buffer);
        }
        buffer.writeVarInt(passiveItems.size());
        for (PropItem item : passiveItems) {
            item.write(buffer);
        }
        Set<PropType> add = new HashSet<>(heldTypes);
        add.removeAll(passiveTypes);
        add.remove(active0.type);
        add.remove(active1.type);
        buffer.writeVarInt(add.size());
        for (PropType type : add) {
            buffer.writeResourceLocation(type.key);
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT data = new CompoundNBT();
        if (active0 != PropItem.EMPTY) data.put(KEY_ACT0, active0.serializeNBT());
        if (active1 != PropItem.EMPTY) data.put(KEY_ACT1, active1.serializeNBT());
        data.putBoolean(KEY_HAS_ACT2, hasSecondActive());
        data.put(KEY_PASSIVE, NBTUtil.convert(passiveItems));
        ListNBT held = new ListNBT();
        for (PropType type : heldTypes) {
            if (active0.type != type && active1.type != type && !passiveTypes.contains(type)) {
                held.add(StringNBT.valueOf(type.key.toString()));
            }
        }
        data.put(KEY_HELD, held);
        return data;
    }

    @Override
    public void deserializeNBT(CompoundNBT data) {
        active0 = data.contains(KEY_ACT0, TAG_COMPOUND) ? PropItem.fromNbt(data.getCompound(KEY_ACT0)) : PropItem.EMPTY;
        active1 = data.contains(KEY_ACT1, TAG_COMPOUND) ? PropItem.fromNbt(data.getCompound(KEY_ACT1)) : PropItem.EMPTY;
        hasSecondActive = data.getBoolean(KEY_HAS_ACT2);
        passiveItems = NBTUtil.convert(data.getList(KEY_PASSIVE, TAG_COMPOUND), PropItem::fromNbt);
        passiveTypes = passiveItems.stream().map(item -> item.type).collect(Collectors.toSet());
        heldTypes = new HashSet<>();
        if (active0 != PropItem.EMPTY) heldTypes.add(active0.type);
        if (active1 != PropItem.EMPTY) heldTypes.add(active1.type);
        heldTypes.addAll(passiveTypes);
        ListNBT held = data.getList(KEY_HELD, Constants.NBT.TAG_STRING);
        for (INBT nbt : held) {
            PropTypes.get(new ResourceLocation(nbt.getString())).ifPresent(heldTypes::add);
        }
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
