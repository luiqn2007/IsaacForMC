package lq2007.mcmod.isaacformc.isaac.prop;

import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public interface IAttackType {

    default boolean cancelEntityAttack(PropItem item, LivingEntity attacker, DamageSource source, float damage) {
        return false;
    }

    float onEntityAttack(PropItem item, LivingEntity attacker, DamageSource source, float damage);
}
