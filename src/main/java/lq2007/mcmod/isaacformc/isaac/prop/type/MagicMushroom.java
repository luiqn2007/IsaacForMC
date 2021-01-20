package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.Isaac;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.property.Property;
import net.minecraft.util.ResourceLocation;

// todo 获得红心 + 2
// todo 恢复所有红心
// todo 角色体型增大
public class MagicMushroom extends PropType {

    public MagicMushroom() {
        super(new ResourceLocation(Isaac.ID, "magic_mushroom"), false, 12);
    }

    @Override
    public void setProperty(PropItem item, Property property) {

    }
}
