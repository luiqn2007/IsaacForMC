package lq2007.mcmod.isaacformc.common.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.isaac.prop.PropItem;
import net.minecraft.entity.LivingEntity;

public interface IUpdateType {

    void onUpdate(PropItem item, LivingEntity entity);
}
