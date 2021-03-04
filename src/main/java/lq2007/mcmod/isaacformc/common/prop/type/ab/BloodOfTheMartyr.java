package lq2007.mcmod.isaacformc.common.prop.type.ab;

import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacformc.common.prop.type.EnumPropTags;
import lq2007.mcmod.isaacformc.common.prop.type.PropTag;
import lq2007.mcmod.isaacformc.common.prop.type.TypeGroups;
import lq2007.mcmod.isaacformc.common.util.EntityUtil;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearAppearances;
import net.minecraft.entity.LivingEntity;

import java.util.UUID;

// https://isaac.huijiwiki.com/wiki/%E6%AE%89%E9%81%93%E8%80%85%E4%B9%8B%E8%A1%80
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.DAMAGE, EnumPropTags.DAMAGE_FIX, EnumPropTags.TEAR_TEXTURE})
public class BloodOfTheMartyr extends AbstractPropType {

    public static final UUID DAMAGE_UP = UUID.fromString("56efdcb6-572a-62bd-b462-d8bdae5b1ad5");

    public BloodOfTheMartyr(int id) {
        super("blood_of_the_martyr", id, 3, EnumPropPools.NORMAL_AND_ANGLE);
    }

    @Override
    public void onPickup(LivingEntity entity, Prop item, Prop itemBeforeEvent) {
        super.onPickup(entity, item);
        if (!entity.world.isRemote) {
            EntityUtil.damageUp(entity, DAMAGE_UP, "isaac.blood_of_the_martyr.damage_up", 1);
            EntityUtil.damageFixConflict(entity, TypeGroups.DAMAGE_FIX_0, 1.5);
            IsaacCapabilities.getProperty(entity).addTearAppearance(EnumTearAppearances.BLOOD);
        }
    }
}
