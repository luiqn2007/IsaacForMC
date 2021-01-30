package lq2007.mcmod.isaacformc.isaac.prop.type;

import com.google.common.collect.ImmutableList;
import lq2007.mcmod.isaacformc.common.entity.Entities;
import lq2007.mcmod.isaacformc.common.entity.friend.EntityBobby;
import lq2007.mcmod.isaacformc.isaac.prop.EnumPropTags;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropTag;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.data.DataBrotherBobby;
import lq2007.mcmod.isaacformc.isaac.prop.data.IDataWithFriends;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

// https://isaac.huijiwiki.com/wiki/%E6%B3%A2%E6%AF%94%E5%BC%9F%E5%BC%9F
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.BABY})
public class BrotherBobby extends PropType<DataBrotherBobby> {

    public BrotherBobby(String name, boolean isActive, int id, EnumPropPools... rooms) {
        super(name, isActive, id, rooms);
    }

    @Override
    protected DataBrotherBobby createData() {
        return new DataBrotherBobby();
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item, PropItem itemBeforeEvent) {
        if (!entity.world.isRemote) {
            EntityBobby bobby = new EntityBobby(entity);
            IDataWithFriends.FriendData data = ((DataBrotherBobby) item.data).bobby;
            data.index = bobby.getFriendIndex();
            data.type = bobby.getFriendType();
            data.id = bobby.getUniqueID();
            entity.world.addEntity(bobby);
        }
        super.onPickup(entity, item, itemBeforeEvent);
    }

    @Override
    public void onRemove(LivingEntity entity, PropItem item, ImmutableList<PropItem> removedItems) {
        super.onRemove(entity, item, removedItems);
        // todo remove
    }
}
