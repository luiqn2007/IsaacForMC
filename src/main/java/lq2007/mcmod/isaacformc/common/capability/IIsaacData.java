package lq2007.mcmod.isaacformc.common.capability;

import lq2007.mcmod.isaacformc.common.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.common.isaac.prop.PropType;
import net.minecraft.entity.Entity;

import java.util.Collection;
import java.util.List;

public interface IIsaacData {

    PropItem getActionProp();

    void switchActiveProp();

    void addProp(PropItem prop, Entity entity);

    boolean hasSecondAction();

    Collection<PropType> getAllHeldProps();

    List<PropItem> getAllPassiveProps();
}
