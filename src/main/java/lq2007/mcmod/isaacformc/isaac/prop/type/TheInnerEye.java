package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import net.minecraft.util.ResourceLocation;

/*
被动道具,射击延迟修正相关,攻击方式相关

名称
The Inner Eye
内眼

描述
Triple shot
三重射击

添加
DLC

生成
宝箱房
 */
public class TheInnerEye extends PropType {

    public TheInnerEye() {
        super(new ResourceLocation(Isaac.ID, "the_inner_eye"), false, 2);
    }
}
