package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.LivingEntity;

public class IsaacRuntimeData implements IIsaacRuntimeData {

    private boolean isLoaded = false;

    public IsaacRuntimeData() {
        friendCountMap.defaultReturnValue(0);
    }

    @Override
    public void updateFriends(LivingEntity entity, IIsaacPropData prop) {

    }

    @Override
    public ImmutableList<FriendData> getFriends(EnumFriendTypes types) {
        return null;
    }

    @Override
    public void insertAndUpdateFriends(IDataWithFriends data) {

    }

    @Override
    public void removeAndUpdateFriends(IDataWithFriends data) {

    }
}
