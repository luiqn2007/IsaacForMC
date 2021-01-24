package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.capability.IIsaacPropData;
import lq2007.mcmod.isaacformc.common.capability.IIsaacProperty;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.isaac.prop.EnumPropTags;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropTag;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.data.NoData;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.entity.LivingEntity;

// https://isaac.huijiwiki.com/wiki/%E5%86%85%E7%9C%BC
@PropTag({ EnumPropTags.PASSIVE, EnumPropTags.SHOOT_DELAY_FIX, EnumPropTags.ATTACK_TYPE })
public class TheInnerEye extends PropType<NoData> implements TypeGroups.ShootDelay0 {

    public TheInnerEye(String name, boolean isActive, int id, EnumPropPools... rooms) {
        super(name, isActive, id, rooms);
    }

    @Override
    protected NoData createData() {
        return NoData.INSTANCE;
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item, PropItem itemBeforeEvent) {
        if (!entity.world.isRemote) {
            IIsaacPropData propData = IsaacCapabilities.getPropData(entity);
            IIsaacProperty property = IsaacCapabilities.getProperty(entity);

            if (!propData.contains(TypeGroups.ShootDelay0.class)) {
                apply(propData, property);
            }
            property.tearCount(property.tearCount() + 3, 16);
            propData.pickupProp(item);
        }
        super.onPickup(entity, item);
    }
}
