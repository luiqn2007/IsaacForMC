package lq2007.mcmod.isaacformc.common.capability;

import lq2007.mcmod.isaacformc.common.isaac.prop.PropItem;

import java.util.List;

public interface IIsaacData {

    PropItem getFirstActionProp();

    void setFirstActionProp(PropItem prop);

    PropItem getSecondActionProp();

    void setSecondActionProp(PropItem prop);

    void isSecondActionEnable();

    List<PropItem> getAllProps();

    List<PropItem> getAllPassiveProps();
}
