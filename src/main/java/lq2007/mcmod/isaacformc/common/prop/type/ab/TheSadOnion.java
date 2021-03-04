package lq2007.mcmod.isaacformc.common.prop.type.ab;

import lq2007.mcmod.isaacformc.common.capability.IIsaacProperty;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacformc.common.prop.type.EnumPropTags;
import lq2007.mcmod.isaacformc.common.prop.type.PropTag;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.entity.LivingEntity;

// https://isaac.huijiwiki.com/wiki/%E4%BC%A4%E5%BF%83%E6%B4%8B%E8%91%B1
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.SHOOT_DELAY})
public class TheSadOnion extends AbstractPropType {

    public TheSadOnion(int id) {
        super("the_sad_onion", id, EnumPropPools.NORMAL);
    }

    @Override
    public void onPickup(LivingEntity entity, Prop item, Prop itemBeforeEvent) {
        super.onPickup(entity, item, itemBeforeEvent);
        IIsaacProperty property = IsaacCapabilities.getProperty(entity);
        // 射速 +0.7
        property.tearSpeed(property.tearSpeed() + 0.7F);
    }
}
