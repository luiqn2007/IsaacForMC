package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.util.EntityUtil;
import lq2007.mcmod.isaacformc.isaac.prop.EnumPropTags;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropTag;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.data.NoData;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.entity.LivingEntity;

import java.util.UUID;

// https://isaac.huijiwiki.com/wiki/%E5%85%8B%E9%87%8C%E5%90%89%E7%89%B9%E7%9A%84%E5%A4%B4
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.DAMAGE, EnumPropTags.DAMAGE_FIX, EnumPropTags.TEAR_EFFECT})
public class CricketsHead extends PropType<NoData> {

    public static final UUID DAMAGE_UP = UUID.fromString("3f42abf0-3b84-50d9-1d27-b064fc30badd");

    public CricketsHead(String name, boolean isActive, int id, EnumPropPools... rooms) {
        super(name, isActive, id, rooms);
    }

    @Override
    protected NoData createData() {
        return NoData.INSTANCE;
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item, PropItem itemBeforeEvent) {
        if (!entity.world.isRemote) {
            EntityUtil.damageUp(entity, DAMAGE_UP, "isaac.crickets_head.damage_up", 0.5);
            EntityUtil.damageFixConflict(entity, TypeGroups.DAMAGE_FIX_0, 1.5);
        }
        super.onPickup(entity, item);
    }
}
