package lq2007.mcmod.isaacformc.common.prop.type.ab;

import lq2007.mcmod.isaacformc.common.capability.CapabilityRegister;
import lq2007.mcmod.isaacformc.common.capability.PropEntity;
import lq2007.mcmod.isaacformc.common.entity.friend.EntityBobby;
import lq2007.mcmod.isaacformc.common.entity.friend.manager.FriendType;
import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacformc.common.prop.type.EnumPropTags;
import lq2007.mcmod.isaacformc.common.prop.type.PropTag;
import lq2007.mcmod.isaacformc.common.util.EntityHolder;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

// https://isaac.huijiwiki.com/wiki/%E6%B3%A2%E6%AF%94%E5%BC%9F%E5%BC%9F
@PropTag({EnumPropTags.PASSIVE, EnumPropTags.BABY})
public class BrotherBobby extends AbstractPropType {

    public BrotherBobby() {
        super("brother_bobby", 8, EnumPropPools.NORMAL_AND_DEVIL);
    }

    @Override
    public boolean onPickup(LivingEntity entity, Prop item) {
        if (!entity.world.isRemote) {
            EntityBobby bobby = new EntityBobby(entity);
            bobby.setOwner(entity.getUniqueID(), null);
            entity.world.addEntity(bobby);
        }
        return super.onPickup(entity, item);
    }

    @Override
    public void onRemove(LivingEntity entity, Prop item, boolean removeRecord) {
        super.onRemove(entity, item, removeRecord);
        EntityHolder<?> friend = CapabilityRegister.getPropEntity(item).getEntity();
        CapabilityRegister.getProps(entity).getFriends(FriendType.FOLLOWING).remove(friend);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities() {
        return new PropEntity();
    }
}
