package lq2007.mcmod.isaacformc.common.entity.friend;

import lq2007.mcmod.isaacformc.common.entity.Entities;
import lq2007.mcmod.isaacformc.common.entity.EnumEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
/**
 * https://isaac.huijiwiki.com/wiki/%E6%B3%A2%E6%AF%94%E5%BC%9F%E5%BC%9F
 * 实体ID：3.1
 * 波比弟弟跟班跟随着角色，每秒发射1颗普通眼泪（相当于29点射击延迟），每颗造成3.50点伤害，弹速为1.00，射程为23.75
 */
public class EntityBobby extends EntityFriend {

    public static final String NAME = "brother_bobby";
    public static final EnumEntityType TYPE = EnumEntityType.FRIEND;

    public EntityBobby(EntityType<?> type, World worldIn) {
        super(type, worldIn, EnumFriendTypes.FOLLOWING);
    }

    public EntityBobby(LivingEntity owner) {
        super(Entities.TYPE_BOBBY.get(), owner, EnumFriendTypes.FOLLOWING);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
