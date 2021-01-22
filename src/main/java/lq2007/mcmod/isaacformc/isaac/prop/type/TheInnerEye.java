package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.common.capability.IIsaacPropData;
import lq2007.mcmod.isaacformc.common.capability.IIsaacProperty;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.isaac.EnumIsaacVersion;
import lq2007.mcmod.isaacformc.isaac.prop.*;
import lq2007.mcmod.isaacformc.isaac.room.EnumRoom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

// https://isaac.huijiwiki.com/wiki/%E5%86%85%E7%9C%BC
@PropTag({ EnumPropTags.PASSIVE, EnumPropTags.SHOOT_DELAY_FIX, EnumPropTags.ATTACK_TYPE })
public class TheInnerEye extends PropType {

    public TheInnerEye() {
        super(new ResourceLocation(Isaac.ID, "the_inner_eye"), EnumIsaacVersion.ISAAC_REBIRTH, false, 2, EnumRoom.NORMAL);
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item) {
        if (entity.isServerWorld()) {
            IIsaacPropData propData = IsaacCapabilities.getPropData(entity);
            IIsaacProperty property = IsaacCapabilities.getProperty(entity);
            // 仅生效一次: 内眼，蜘蛛，大眼
            if (!propData.contains(this)
                    && !propData.contains(PropTypes.MUTANT_SPIDER)
                    && !propData.contains(PropTypes.POLYPHEMUS)) {
                // 射速延迟 +3 *210%
                property.shootDelay(property.shootDelay() + 3);
                property.shootDelayMultiple(property.shootDelayMultiple() * 2.1F /* 210% */);
            }
            // 眼泪 +3 最高 16
            property.tearCount(property.tearCount() + 3, 16);
            propData.pickupProp(item);
        }
        super.onPickup(entity, item);
    }
}
