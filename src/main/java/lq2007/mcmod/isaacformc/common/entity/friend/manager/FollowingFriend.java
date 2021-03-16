package lq2007.mcmod.isaacformc.common.entity.friend.manager;

import net.minecraft.entity.LivingEntity;

public class FollowingFriend extends BaseFriendManager {

    @Override
    public FriendType getType() {
        return FriendType.FOLLOWING;
    }

    @Override
    public void updateEntities(LivingEntity owner) {
        // todo set position
    }
}
