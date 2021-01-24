package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.capability.IIsaacProperty;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.util.EntityUtil;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.data.NoData;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public class MagicMushroom extends PropType<NoData> {

    private static final UUID MODIFIER_HEALTH = UUID.fromString("57761f44-1b40-e862-2e72-07a43db0049a");

    public MagicMushroom(ResourceLocation key, boolean isActive, EnumPropPools... rooms) {
        super(key, isActive, rooms);
    }

    @Override
    protected NoData createData() {
        return NoData.INSTANCE;
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item, PropItem itemBeforeEvent) {
        if (!entity.world.isRemote) {
            IIsaacProperty property = IsaacCapabilities.getProperty(entity);

            property.bodySize(property.bodySize() + 1);
            EntityUtil.healthUp(entity, MODIFIER_HEALTH, "isaac.magic_mushroom.health", 2);
            EntityUtil.fullHealth(entity);
            EntityUtil.damageFixConflict(entity, TypeGroups.DAMAGE_FIX_0, 1.5);
        }
        super.onPickup(entity, item);
    }
}
