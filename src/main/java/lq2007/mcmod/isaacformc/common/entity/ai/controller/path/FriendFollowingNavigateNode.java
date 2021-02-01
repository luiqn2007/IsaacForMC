package lq2007.mcmod.isaacformc.common.entity.ai.controller.path;

import lq2007.mcmod.isaacformc.common.entity.friend.EntityFriend;

public class FriendFollowingNavigateNode extends NavigateNode {
    @Override
    public boolean check(EntityFriend<?> friend) {
        return false;
    }
}
