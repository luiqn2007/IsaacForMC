package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.isaac.EnumIsaacVersion;
import lq2007.mcmod.isaacformc.isaac.prop.EnumPropTags;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropTag;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.room.EnumRoom;
import net.minecraft.entity.LivingEntity;

// https://isaac.huijiwiki.com/wiki/%E5%85%8B%E9%87%8C%E5%90%89%E7%89%B9%E7%9A%84%E5%A4%B4
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.DAMAGE, EnumPropTags.DAMAGE_FIX, EnumPropTags.TEAR_EFFECT})
public class CricketsHead extends PropType {

    public CricketsHead() {
        super("crickets_head", EnumIsaacVersion.ISAAC_REBIRTH, false, 4, EnumRoom.NORMAL_AND_GOLDEN_CHEST);
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item) {

        super.onPickup(entity, item);
    }
}
