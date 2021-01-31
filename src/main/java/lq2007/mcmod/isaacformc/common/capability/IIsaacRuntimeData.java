package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableList;
import lq2007.mcmod.isaacformc.common.entity.EntityHandler;
import lq2007.mcmod.isaacformc.common.entity.friend.EnumFriendTypes;

public interface IIsaacRuntimeData {

    static IIsaacRuntimeData dummy() {
        return DummyData.INSTANCE;
    }

    // friends
    ImmutableList<EntityHandler> getFriends(EnumFriendTypes types);

    void insertFriend(EntityHandler entity);

    void removeFriend(EntityHandler entity);

    class DummyData implements IIsaacRuntimeData {

        private static final DummyData INSTANCE = new DummyData();

        @Override
        public ImmutableList<EntityHandler> getFriends(EnumFriendTypes types) {
            return ImmutableList.of();
        }

        @Override
        public void insertFriend(EntityHandler entity) {

        }

        @Override
        public void removeFriend(EntityHandler entity) {

        }
    }
}
