package lq2007.mcmod.isaacformc.isaac.prop;

import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import net.minecraft.entity.LivingEntity;

public interface IUpdateType {

    void onUpdate(PropItem item, LivingEntity entity);
}
