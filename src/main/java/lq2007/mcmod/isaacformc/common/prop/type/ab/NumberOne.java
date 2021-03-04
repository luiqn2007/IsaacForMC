package lq2007.mcmod.isaacformc.common.prop.type.ab;

import lq2007.mcmod.isaacformc.common.capability.IIsaacPropData;
import lq2007.mcmod.isaacformc.common.capability.IIsaacProperty;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacformc.common.prop.type.EnumPropTags;
import lq2007.mcmod.isaacformc.common.prop.type.PropTag;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.entity.LivingEntity;

// https://isaac.huijiwiki.com/wiki/%E5%B0%8F%E5%8F%B7
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.SHOOT_DELAY, EnumPropTags.SHOOT_RANGE, EnumPropTags.SHOOT_HIGH, EnumPropTags.TEAR_TEXTURE})
public class NumberOne extends AbstractPropType {

    public NumberOne(int id) {
        super("number_one", id, EnumPropPools.NORMAL);
    }

    @Override
    public void onPickup(LivingEntity entity, Prop item, Prop itemBeforeEvent) {
        if (!entity.world.isRemote) {
            IIsaacPropData propData = IsaacCapabilities.getPropData(entity);
            if (!propData.contains(this)) {
                IIsaacProperty property = IsaacCapabilities.getProperty(entity);
                property.tearSpeed(property.tearSpeed() + 1.5F);
                property.range(property.range() - 17.62F);
                property.tossUpSpeed(property.tossUpSpeed() - 0.45F);
            }

        }
        super.onPickup(entity, item, itemBeforeEvent);
    }
}
