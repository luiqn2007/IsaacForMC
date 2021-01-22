package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.isaac.EnumIsaacVersion;
import lq2007.mcmod.isaacformc.isaac.prop.EnumPropTags;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropTag;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.room.EnumRoom;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearEffects;
import net.minecraft.entity.LivingEntity;

// https://isaac.huijiwiki.com/wiki/%E5%BC%AF%E5%8B%BA%E8%80%85
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.TEAR_EFFECT})
public class SpoonBender extends PropType {

    public SpoonBender() {
        super("spoon_bender", EnumIsaacVersion.ISAAC_REBIRTH, false, 3, EnumRoom.NORMAL);
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item) {
        super.onPickup(entity, item);
        IsaacCapabilities.getProperty(entity).addTearEffect(EnumTearEffects.TRACK);
    }
}
