package lq2007.mcmod.isaacformc.common.prop.type;

import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.util.EntityUtil;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.entity.LivingEntity;

public class TheBookOfBelial extends AbstractPropType {

    public TheBookOfBelial(String name, int id, EnumPropPools... rooms) {
        super(name, 3, id, rooms);
    }

    @Override
    public void onActiveStart(LivingEntity entity, Prop prop) {
        super.onActiveStart(entity, prop);
        boolean added = EntityUtil.damageFixConflict(entity, TypeGroups.DAMAGE_FIX_0, 1.5);
    }
}
