package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.property.Property;
import lq2007.mcmod.isaacformc.isaac.property.EnumPropertyType;
import net.minecraft.util.ResourceLocation;

import static lq2007.mcmod.isaacformc.common.Isaac.ID;

/*
被动道具,射击延迟相关

名称
The Sad Onion
伤心洋葱

描述
Tears up
射速上升

添加
DLC

生成
宝箱房
 */
public class TheSadOnion extends PropType {

    public TheSadOnion() {
        super(new ResourceLocation(ID, "the_sad_onion"), false, 1);
    }

    @Override
    public void setProperty(PropItem item, Property property) {
        if (property.type == EnumPropertyType.SHOOT_SPEED) {
            property.add(0.7f);
        }
    }
}
