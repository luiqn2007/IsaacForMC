package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.isaac.EnumIsaacVersion;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.room.EnumRoom;
import net.minecraft.util.ResourceLocation;

// https://isaac.huijiwiki.com/wiki/%E5%86%85%E7%9C%BC
// The Inner Eye
// 内眼
// todo
public class TheInnerEye extends PropType {

    public TheInnerEye() {
        super(new ResourceLocation(Isaac.ID, "the_inner_eye"), EnumIsaacVersion.ISAAC_AB, false, 2, EnumRoom.NORMAL);
    }
}
