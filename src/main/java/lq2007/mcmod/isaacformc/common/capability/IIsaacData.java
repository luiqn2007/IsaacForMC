package lq2007.mcmod.isaacformc.common.capability;

import lq2007.mcmod.isaacformc.common.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.common.isaac.prop.PropType;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Collection;
import java.util.List;

public interface IIsaacData extends INBTSerializable<CompoundNBT> {

    void addProp(PropItem prop, Entity entity);

    void removeProp(PropItem item);

    PropItem getActionProp();

    void switchActiveProp();

    boolean hasSecondAction();

    void setHasSecondAction(boolean second);

    Collection<PropType> getAllHeldProps();

    List<PropItem> getAllPassiveProps();
}
