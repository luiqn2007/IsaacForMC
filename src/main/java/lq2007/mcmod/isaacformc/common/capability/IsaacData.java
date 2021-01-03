package lq2007.mcmod.isaacformc.common.capability;

import lq2007.mcmod.isaacformc.common.isaac.prop.PropItem;

import java.util.ArrayList;
import java.util.List;

public class IsaacData implements IIsaacData {

    private PropItem active0 = PropItem.EMPTY;
    private PropItem active1 = PropItem.EMPTY;
    private List<PropItem> passive = new ArrayList<>();

    @Override
    public PropItem getFirstActionProp() {
        return active0;
    }

    @Override
    public void setFirstActionProp(PropItem prop) {
        active0 = prop;
    }

    @Override
    public PropItem getSecondActionProp() {
        return PropItem.EMPTY;
    }

    @Override
    public void setSecondActionProp(PropItem prop) {

    }

    @Override
    public void isSecondActionEnable() {

    }

    @Override
    public List<PropItem> getAllProps() {
        return null;
    }

    @Override
    public List<PropItem> getAllPassiveProps() {
        return null;
    }
}
