package lq2007.mcmod.isaacformc.common.entity.ai.controller;

import lq2007.mcmod.isaacformc.common.entity.friend.EntityFriend;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.world.World;

public class FriendPathNavigator extends FlyingPathNavigator {

    public final EntityFriend<?> friend;
    public final NavigateNode node;

    public FriendPathNavigator(EntityFriend<?> entityIn, World worldIn, NavigateNode node) {
        super(entityIn, worldIn);
        this.friend = entityIn;
        this.node = node;
    }

    @Override
    public void tick() {
        if (node.check(friend)) {
            super.tick();
        }
    }
}
