package lq2007.mcmod.isaacformc.common.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.isaac.prop.PropItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public interface IAttackType {

    default boolean cancelEntityAttack(PropItem item, LivingEntity attacker, DamageSource source, float damage) {
        return false;
    }

    float onEntityAttack(PropItem item, LivingEntity attacker, DamageSource source, float damage);
}
