package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.isaac.prop.EnumPropTags;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropTag;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.data.NoData;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearEffects;
import net.minecraft.entity.LivingEntity;

// https://isaac.huijiwiki.com/wiki/%E5%BC%AF%E5%8B%BA%E8%80%85
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.TEAR_EFFECT})
public class SpoonBender extends PropType<NoData> {

    public SpoonBender(String name, boolean isActive, int id, EnumPropPools... rooms) {
        super(name, isActive, id, rooms);
    }

    @Override
    protected NoData createData() {
        return NoData.INSTANCE;
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item, PropItem itemBeforeEvent) {
        super.onPickup(entity, item);
        IsaacCapabilities.getProperty(entity).addTearEffect(EnumTearEffects.TRACK);
    }
}
