package lq2007.mcmod.isaacmod.common.entity.friend.manager;

import net.minecraft.entity.LivingEntity;

public class SurroundFriend extends BaseFriendManager {

    @Override
    public FriendType getType() {
        return FriendType.SURROUND;
    }

    @Override
    public void updateEntities(LivingEntity owner) {
        // todo set position
    }
}
