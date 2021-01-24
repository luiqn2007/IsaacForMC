package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.util.EntityUtil;
import lq2007.mcmod.isaacformc.isaac.EnumIsaacVersion;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.data.ChargeData;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.entity.LivingEntity;

public class TheBookOfBelial extends PropType<ChargeData> {

    public TheBookOfBelial(String name, EnumIsaacVersion version, boolean isActive, int id, EnumPropPools... rooms) {
        super(name, isActive, id, rooms);
    }

    @Override
    protected ChargeData createData() {
        return new ChargeData(3, 0);
    }

    @Override
    public void onActiveStart(LivingEntity entity, PropItem prop) {
        super.onActiveStart(entity, prop);
        boolean added = EntityUtil.damageFixConflict(entity, TypeGroups.DAMAGE_FIX_0, 1.5);
    }
}
