package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import net.minecraft.util.ResourceLocation;

/*
被动道具,眼泪特效相关

弯勺者
Spoon Bende

追踪射击
Homing shots

DLC
 */
public class SpoonBender extends PropType {

    public SpoonBender() {
        super(new ResourceLocation(Isaac.ID, "spoon_bender"), false, 3);
    }
}
