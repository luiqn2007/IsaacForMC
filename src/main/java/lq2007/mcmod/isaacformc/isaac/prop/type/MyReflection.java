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
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearEffects;
import net.minecraft.entity.LivingEntity;

// https://isaac.huijiwiki.com/wiki/%E6%88%91%E7%9A%84%E9%95%9C%E5%83%8F
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.SHOOT_RANGE, EnumPropTags.SHOOT_HIGH, EnumPropTags.TEAR_SPEED, EnumPropTags.TEAR_EFFECT})
public class MyReflection extends PropType<NoData> {

    public MyReflection(String name, boolean isActive, int id, EnumPropPools... rooms) {
        super(name, isActive, id, rooms);
    }

    @Override
    protected NoData createData() {
        return NoData.INSTANCE;
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item, PropItem itemBeforeEvent) {
        super.onPickup(entity, item, itemBeforeEvent);
        if (!entity.world.isRemote) {
            IIsaacProperty property = IsaacCapabilities.getProperty(entity);
            IIsaacPropData propData = IsaacCapabilities.getPropData(entity);

            property.range(property.range() + 1.5F);
            property.tossUpSpeed(property.tossUpSpeed() + 1);
            property.addTearEffect(EnumTearEffects.REFLECTION);
            property.tearSpeedMultiple(property.tearSpeedMultiple() * 1.375F);
            if (!propData.contains(this)) {
                property.tearSpeed(property.tearSpeed() + 0.6F);
            }
        }
    }
}
