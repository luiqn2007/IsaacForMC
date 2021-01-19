package lq2007.mcmod.isaacformc.common.capability;

import lq2007.mcmod.isaacformc.common.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.common.isaac.prop.PropType;
import net.minecraft.entity.Entity;

import java.util.*;

public class IsaacData implements IIsaacData {

    private PropItem active0 = PropItem.EMPTY;
    private PropItem active1 = PropItem.EMPTY;
    private List<PropItem> passives = new ArrayList<>();
    private Set<PropType> passiveTypes = new HashSet<>();
    private Set<PropType> heldTypes = new HashSet<>();
    private boolean hasSecondAction = false;

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
            passives.add(prop);
            passiveTypes.add(prop.type);
            add = true;
        } else if (prop.type.allowMultiItem()) {
            passives.add(prop);
            add = true;
        }

        if (add) {
            prop.type.onPickUp(entity, prop);
        }
    }

    @Override
    public boolean hasSecondAction() {
        return hasSecondAction;
    }

    @Override
    public Collection<PropType> getAllHeldProps() {
        return heldTypes;
    }

    @Override
    public List<PropItem> getAllPassiveProps() {
        return passives;
    }
}
