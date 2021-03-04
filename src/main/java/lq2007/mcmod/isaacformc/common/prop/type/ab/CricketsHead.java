package lq2007.mcmod.isaacformc.common.prop.type.ab;

import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacformc.common.prop.type.EnumPropTags;
import lq2007.mcmod.isaacformc.common.prop.type.PropTag;
import lq2007.mcmod.isaacformc.common.prop.type.TypeGroups;
import lq2007.mcmod.isaacformc.common.util.EntityUtil;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.entity.LivingEntity;

import java.util.UUID;

// https://isaac.huijiwiki.com/wiki/%E5%85%8B%E9%87%8C%E5%90%89%E7%89%B9%E7%9A%84%E5%A4%B4
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.DAMAGE, EnumPropTags.DAMAGE_FIX, EnumPropTags.TEAR_EFFECT})
public class CricketsHead extends AbstractPropType {

    public static final UUID DAMAGE_UP = UUID.fromString("3f42abf0-3b84-50d9-1d27-b064fc30badd");

    public CricketsHead(int id) {
        super("crickets_head", id, EnumPropPools.NORMAL_AND_GOLDEN_CHEST);
    }

    @Override
    public void onPickup(LivingEntity entity, Prop item, Prop itemBeforeEvent) {
        if (!entity.world.isRemote) {
            EntityUtil.damageUp(entity, DAMAGE_UP, "isaac.crickets_head.damage_up", 0.5);
            EntityUtil.damageFixConflict(entity, TypeGroups.DAMAGE_FIX_0, 1.5);
        }
        super.onPickup(entity, item);
    }
}
