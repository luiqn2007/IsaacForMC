package lq2007.mcmod.isaacformc.common.prop.type.ab;

import com.google.common.collect.ImmutableList;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.capability.data.IsaacFriends;
import lq2007.mcmod.isaacformc.common.entity.friend.EntityBobby;
import lq2007.mcmod.isaacformc.common.prop.PropItem;
import lq2007.mcmod.isaacformc.common.prop.data.DataBrotherBobby;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacformc.common.prop.type.EnumPropTags;
import lq2007.mcmod.isaacformc.common.prop.type.PropTag;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.server.ServerWorld;

// https://isaac.huijiwiki.com/wiki/%E6%B3%A2%E6%AF%94%E5%BC%9F%E5%BC%9F
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.BABY})
public class BrotherBobby extends AbstractPropType {

    public BrotherBobby(int id) {
        super("brother_bobby", id, EnumPropPools.NORMAL_AND_DEVIL);
    }

    @Override
    protected DataBrotherBobby createData() {
        return new DataBrotherBobby();
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item, PropItem itemBeforeEvent) {
        if (!entity.world.isRemote) {
            EntityBobby bobby = new EntityBobby(entity);
            ((DataBrotherBobby) item.data).bobby = bobby.getUniqueID();
            IsaacCapabilities.getPropData(entity).getOrCreateFriends(IsaacFriends.FOLLOWING).add(entity, bobby);
            entity.world.addEntity(bobby);
        }
        super.onPickup(entity, item, itemBeforeEvent);
    }

    @Override
    public void onRemove(LivingEntity entity, PropItem item, ImmutableList<PropItem> removedItems) {
        if (!entity.world.isRemote) {
            ServerWorld world = (ServerWorld) entity.world;
            DataBrotherBobby data = (DataBrotherBobby) item.data;
            data.bobby.getEntity(world).ifPresent(Entity::remove);
        }
        super.onRemove(entity, item, removedItems);
    }
}
