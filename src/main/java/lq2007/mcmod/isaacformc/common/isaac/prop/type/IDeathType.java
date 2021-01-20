package lq2007.mcmod.isaacformc.common.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.isaac.prop.PropItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public interface IDeathType {

    boolean onEntityDeath(PropItem item, LivingEntity entity, DamageSource source);
}
