package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.util.EntityUtil;
import lq2007.mcmod.isaacformc.isaac.prop.EnumPropTags;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropTag;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.data.IPropData;
import lq2007.mcmod.isaacformc.isaac.prop.data.NoData;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearAppearances;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearEffects;
import net.minecraft.entity.LivingEntity;

import java.util.UUID;

// https://isaac.huijiwiki.com/wiki/%E6%AE%89%E9%81%93%E8%80%85%E4%B9%8B%E8%A1%80
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.DAMAGE, EnumPropTags.DAMAGE_FIX, EnumPropTags.TEAR_TEXTURE})
public class BloodOfTheMartyr extends PropType<NoData> {

    public static final UUID DAMAGE_UP = UUID.fromString("56efdcb6-572a-62bd-b462-d8bdae5b1ad5");

    public BloodOfTheMartyr(String name, boolean isActive, int id, EnumPropPools... rooms) {
        super(name, isActive, id, rooms);
    }

    @Override
    protected NoData createData() {
        return new NoData();
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item, PropItem itemBeforeEvent) {
        super.onPickup(entity, item);
        if (!entity.world.isRemote) {
            EntityUtil.damageUp(entity, DAMAGE_UP, "isaac.blood_of_the_martyr.damage_up", 1);
            EntityUtil.damageFixConflict(entity, TypeGroups.DAMAGE_FIX_0, 1.5);
            IsaacCapabilities.getProperty(entity).addTearAppearance(EnumTearAppearances.BLOOD);
        }
    }
}
