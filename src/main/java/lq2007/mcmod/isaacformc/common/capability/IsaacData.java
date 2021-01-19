package lq2007.mcmod.isaacformc.common.capability;

import lq2007.mcmod.isaacformc.common.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.common.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.common.isaac.prop.PropTypes;
import lq2007.mcmod.isaacformc.common.util.NBTUtils;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

import java.util.*;
import java.util.stream.Collectors;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

public class IsaacData implements IIsaacData {

    private static final String KEY_ACT0 = "_act0";
    private static final String KEY_ACT1 = "_act1";
    private static final String KEY_HAS_ACT2 = "_has2";
    private static final String KEY_PASSIVE = "_passive";
    private static final String KEY_HELD = "_held";

    private PropItem active0 = PropItem.EMPTY;
    private PropItem active1 = PropItem.EMPTY;
    private boolean hasSecondAction = false;
    private List<PropItem> passiveItems = new ArrayList<>();
    private Set<PropType> passiveTypes = new HashSet<>();
    private Set<PropType> heldTypes = new HashSet<>();

    @Override
    public PropItem getActionProp() {
        return active0;
    }

    @Override
    public void switchActiveProp() {
        if (hasSecondAction() && active1 != PropItem.EMPTY) {
            PropItem temp = active0;
            active0 = active1;
            active1 = temp;
        }
    }

    @Override
    public void addProp(PropItem prop, Entity entity) {
        heldTypes.add(prop.type);
        boolean add = false;
        if (prop.type.isActive()) {
            if (hasSecondAction() && active1 == PropItem.EMPTY) {
                active1 = prop;
            } else {
                active0 = prop;
            }
            add = true;
        } else if (!passiveTypes.contains(prop.type)) {
            passiveItems.add(prop);
            passiveTypes.add(prop.type);
            add = true;
        } else if (prop.type.allowMultiItem()) {
            passiveItems.add(prop);
            add = true;
        }

        if (add) {
            prop.type.onPickUp(entity, prop);
        }
    }

    @Override
    public void removeProp(PropItem item) {
        PropType type = item.type;
        if (type.isActive()) {
            if (active0 == item) {
                active0 = PropItem.EMPTY;
            } else if (active1 == item) {
                active1 = PropItem.EMPTY;
            }
        } else if (passiveItems.remove(item)) {
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
        }
    }

    @Override
    public boolean hasSecondAction() {
        return hasSecondAction;
    }

    @Override
    public void setHasSecondAction(boolean hasSecondAction) {
        this.hasSecondAction = hasSecondAction;
    }

    @Override
    public Collection<PropType> getAllHeldProps() {
        return heldTypes;
    }

    @Override
    public List<PropItem> getAllPassiveProps() {
        return passiveItems;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT data = new CompoundNBT();
        if (active0 != PropItem.EMPTY) data.put(KEY_ACT0, active0.serializeNBT());
        if (active1 != PropItem.EMPTY) data.put(KEY_ACT1, active1.serializeNBT());
        data.putBoolean(KEY_HAS_ACT2, hasSecondAction);
        data.put(KEY_PASSIVE, NBTUtils.convert(passiveItems));
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
        hasSecondAction = data.getBoolean(KEY_HAS_ACT2);
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

}
