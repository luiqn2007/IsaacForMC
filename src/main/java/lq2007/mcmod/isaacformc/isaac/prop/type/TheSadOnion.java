package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.capability.IIsaacProperty;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.isaac.EnumIsaacVersion;
import lq2007.mcmod.isaacformc.isaac.prop.EnumPropTags;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropTag;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.room.EnumRoom;
import net.minecraft.entity.LivingEntity;

// https://isaac.huijiwiki.com/wiki/%E4%BC%A4%E5%BF%83%E6%B4%8B%E8%91%B1
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.SHOOT_DELAY})
public class TheSadOnion extends PropType {

    public TheSadOnion() {
        super("the_sad_onion", EnumIsaacVersion.ISAAC_REBIRTH, false, 1, EnumRoom.NORMAL);
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item) {
        super.onPickup(entity, item);
        IIsaacProperty property = IsaacCapabilities.getProperty(entity);
        // 射速 +0.7
        property.tearSpeed(property.tearSpeed() + 0.7F);
    }
}
