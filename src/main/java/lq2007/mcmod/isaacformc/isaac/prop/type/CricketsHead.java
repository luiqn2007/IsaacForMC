package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.Isaac;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.property.Property;
import lq2007.mcmod.isaacformc.isaac.property.EnumPropertyType;
import net.minecraft.util.ResourceLocation;

public class CricketsHead extends PropType {

    public CricketsHead() {
        super(new ResourceLocation(Isaac.ID, "crickets_head"), false, 4);
    }

    @Override
    public void setProperty(PropItem item, Property property) {
        if (property.type == EnumPropertyType.DAMAGE_BASE) {
            property.add(0.5F);
        } else if (property.type == EnumPropertyType.DAMAGE_MULTIPLE) {
            if (property.hasMark(this) || property.hasMark()) {
                property.mul(1.5F);
            }
        }
    }
}
