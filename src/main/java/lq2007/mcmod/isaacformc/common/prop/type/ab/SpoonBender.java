package lq2007.mcmod.isaacformc.common.prop.type.ab;

import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.prop.PropItem;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacformc.common.prop.type.EnumPropTags;
import lq2007.mcmod.isaacformc.common.prop.type.PropTag;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearEffects;
import net.minecraft.entity.LivingEntity;

// https://isaac.huijiwiki.com/wiki/%E5%BC%AF%E5%8B%BA%E8%80%85
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.TEAR_EFFECT})
public class SpoonBender extends AbstractPropType {

    public SpoonBender(int id) {
        super("spoon_bender", id, EnumPropPools.NORMAL);
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item, PropItem itemBeforeEvent) {
        super.onPickup(entity, item);
        IsaacCapabilities.getProperty(entity).addTearEffect(EnumTearEffects.TRACK);
    }
}
