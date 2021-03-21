package lq2007.mcmod.isaacmod.common.entity.friend;

import lq2007.mcmod.isaacmod.Isaac;
import lq2007.mcmod.isaacmod.common.entity.EnumEntityType;
import lq2007.mcmod.isaacmod.register.registers.EntityRegister;
import net.minecraft.entity.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * https://isaac.huijiwiki.com/wiki/%E6%B3%A2%E6%AF%94%E5%BC%9F%E5%BC%9F
 * 实体ID：3.1
 * 波比弟弟跟班跟随着角色，每秒发射1颗普通眼泪（相当于29点射击延迟），每颗造成3.50点伤害，弹速为1.00，射程为23.75
 */
@EntityRegister.EntityInfo(classification = EntityClassification.MISC)
public class EntityBobby extends EntityFriend<EntityBobby> {

    public static final String NAME = "brother_bobby";
    public static final EnumEntityType TYPE = EnumEntityType.FRIEND;

    protected AxisAlignedBB attackRange = new AxisAlignedBB(-16, -16, -16, 16, 16, 16);

    public EntityBobby(EntityType<EntityBobby> type, World worldIn) {
        super(type, worldIn, null);
    }

    public EntityBobby(LivingEntity owner) {
        super(Isaac.ENTITIES.get(EntityBobby.class), owner);
    }

    @Override
    public void update(LivingEntity owner) {
        if (!world.isRemote && ticksExisted % 20 == 0) {
            LivingEntity attackingEntity = owner.getAttackingEntity();
            if (attackingEntity == null) {
                EntityPredicate predicate = new EntityPredicate().setCustomPredicate(e -> e instanceof MobEntity);
                attackingEntity = world.getClosestEntityWithinAABB(MobEntity.class,
                        new EntityPredicate().setCustomPredicate(entity -> predicate.canTarget(owner, entity)),
                        owner, owner.getPosX(), owner.getPosY(), owner.getPosZ(), attackRange);
            }
            if (attackingEntity != null) {
                // todo spawn tear towards entity
            }
        }
    }
}
