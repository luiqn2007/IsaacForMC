package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lq2007.mcmod.isaacformc.common.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.common.isaac.prop.type.IUpdateType;
import lq2007.mcmod.isaacformc.common.isaac.prop.type.PropType;
import lq2007.mcmod.isaacformc.common.isaac.prop.PropTypes;
import lq2007.mcmod.isaacformc.common.util.NBTUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.loading.FMLCommonLaunchHandler;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

public class IsaacPropData implements IIsaacPropData, ICapabilityProvider {

    private static final String KEY_OWNER = "_owner";
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
    private LivingEntity entity;
    private UUID ownerId;

    private final LazyOptional<IIsaacPropData> dataOptional = LazyOptional.of(() -> this);
    private IsaacPropData capture = null;

    public IsaacPropData() { }

    private IsaacPropData(PropItem active0, PropItem active1, boolean hasSecondActive, List<PropItem> passiveItems, Set<PropType> passiveTypes, Set<PropType> heldTypes) {
        this.active0 = active0;
        this.active1 = active1;
        this.hasSecondActive = hasSecondActive;
        this.passiveItems = passiveItems;
        this.passiveTypes = passiveTypes;
        this.heldTypes = heldTypes;
    }

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
        }
    }

    @Override
    public void bindEntity(LivingEntity entity) {

    }

    @Override
    public LivingEntity getEntity() {
        if (entity == null) {
            // todo owner
        }
        return entity;
    }

    @Override
    public PropItem pickupProp(PropItem prop) {
        apply(entity);
        PropItem replacedItem;
        if (prop.type.isActive()) {
            if (active0 == PropItem.EMPTY) {
                active0 = prop;
                replacedItem = PropItem.EMPTY;
            } else {
                replacedItem = active0;
                if (removeProp(active0)) {
                    if (hasSecondActive() && active1 == PropItem.EMPTY) {
                        active1 = active0;
                    }
                    active0 = prop;
                } else {
                    replacedItem = prop;
                }
            }
        } else if (!passiveTypes.contains(prop.type)) {
            passiveItems.add(prop);
            passiveTypes.add(prop.type);
            replacedItem = PropItem.EMPTY;
        } else if (prop.type.allowMultiItem()) {
            passiveItems.add(prop);
            replacedItem = PropItem.EMPTY;
        } else {
            replacedItem = prop;
        }

        if (replacedItem != prop) {
            heldTypes.add(prop.type);
            prop.type.onPickUp(entity, prop);
            replacedItem.type.onRemove(entity, replacedItem);
        }
        return replacedItem;
    }

    @Override
    public boolean removeProp(PropItem prop) {
        apply(entity);
        if (prop == PropItem.EMPTY) {
            return false;
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
            if (type.allowMultiItem()) {
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
            } else {
                passiveTypes.remove(type);
            }
            isRemoved = true;
        }
        if (isRemoved) {
            prop.type.onRemove(entity, prop);
        }
        return isRemoved;
    }

    @Override
    public int removeAllProps(boolean removeActiveProp) {
        int count = 0;
        if (isApplied) {
            if (removeActiveProp) {
                if (hasSecondActive() && removeProp(active1)) {
                    count++;
                }
                if (removeProp(active0)) {
                    count++;
                }
            }
            for (PropItem prop : getAllPassiveProps()) {
                if (removeProp(prop)) {
                    count++;
                }
            }
        } else {
            if (active0 != PropItem.EMPTY) {
                active0 = PropItem.EMPTY;
                count++;
            }
            if (active1 != PropItem.EMPTY) {
                active1 = PropItem.EMPTY;
                count++;
            }
            count += passiveItems.size();
            passiveItems.clear();
        }
        return count;
    }

    @Override
    public boolean hasSecondActive() {
        return hasSecondActive;
    }

    @Override
    public void setHasSecondAction(boolean hasSecondActive) {
        apply(entity);
        if (hasSecondActive) {
            this.hasSecondActive = true;
        } else {
            removeProp(active1);
            this.hasSecondActive = false;
            this.active1 = PropItem.EMPTY;
        }
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
    public void copyFrom(IIsaacPropData data) {
        if (isApplied) {
            removeAllProps(true);
        }
        heldTypes.addAll(data.getAllHeldProps());
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

    @Override
    public void update() {
        apply(entity);
        if (active0.type instanceof IUpdateType) {
            ((IUpdateType) active0.type).onUpdate(active0, entity);
        }
        if (active1.type instanceof IUpdateType) {
            ((IUpdateType) active1.type).onUpdate(active1, entity);
        }
        for (PropItem item : passiveItems) {
            if (item.type instanceof IUpdateType) {
                ((IUpdateType) item.type).onUpdate(item, entity);
            }
        }
    }

    private void apply(LivingEntity entity) {
        if (!isApplied) {
            if (capture != null) {
                capture.removeAllProps(true);
            }
            active0.type.onPickUp(entity, active0);
            active1.type.onPickUp(entity, active1);
            for (PropItem item : passiveItems) {
                item.type.onPickUp(entity, item);
            }
            isApplied = true;
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (isApplied) {
            capture = new IsaacPropData(active0, active1, hasSecondActive, passiveItems, passiveTypes, heldTypes);
        }
        CompoundNBT data = new CompoundNBT();
        if (active0 != PropItem.EMPTY) data.put(KEY_ACT0, active0.serializeNBT());
        if (active1 != PropItem.EMPTY) data.put(KEY_ACT1, active1.serializeNBT());
        data.putBoolean(KEY_HAS_ACT2, hasSecondActive());
        data.put(KEY_PASSIVE, NBTUtils.convert(passiveItems));
        ListNBT held = new ListNBT();
        for (PropType type : heldTypes) {
            if (active0.type != type && active1.type != type && !passiveTypes.contains(type)) {
                held.add(StringNBT.valueOf(type.key.toString()));
            }
        }
        data.put(KEY_HELD, held);
        isApplied = false;
        return data;
    }

    @Override
    public void deserializeNBT(CompoundNBT data) {
        active0 = data.contains(KEY_ACT0, TAG_COMPOUND) ? PropItem.fromNbt(data.getCompound(KEY_ACT0)) : PropItem.EMPTY;
        active1 = data.contains(KEY_ACT1, TAG_COMPOUND) ? PropItem.fromNbt(data.getCompound(KEY_ACT1)) : PropItem.EMPTY;
        hasSecondActive = data.getBoolean(KEY_HAS_ACT2);
        passiveItems = NBTUtils.convert(data.getList(KEY_PASSIVE, TAG_COMPOUND), PropItem::fromNbt);
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
        return IsaacCapabilities.CAPABILITY.orEmpty(cap, this.dataOptional);
    }
}
