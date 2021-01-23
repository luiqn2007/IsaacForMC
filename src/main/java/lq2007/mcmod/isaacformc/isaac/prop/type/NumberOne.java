package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.capability.IIsaacPropData;
import lq2007.mcmod.isaacformc.common.capability.IIsaacProperty;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.isaac.prop.EnumPropTags;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropTag;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.data.NoData;
import lq2007.mcmod.isaacformc.isaac.room.EnumRoom;
import net.minecraft.entity.LivingEntity;

// https://isaac.huijiwiki.com/wiki/%E5%B0%8F%E5%8F%B7
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.SHOOT_DELAY, EnumPropTags.SHOOT_RANGE, EnumPropTags.SHOOT_HIGH, EnumPropTags.TEAR_TEXTURE})
public class NumberOne extends PropType<NoData> {

    public NumberOne() {
        super("number_one", false, 6, EnumRoom.NORMAL);
    }

    @Override
    protected NoData createData() {
        return NoData.INSTANCE;
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item, PropItem itemBeforeEvent) {
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
