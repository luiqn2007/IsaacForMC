package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lq2007.mcmod.isaacformc.common.entity.EntityHandler;
import lq2007.mcmod.isaacformc.common.entity.friend.EnumFriendTypes;
import net.minecraft.entity.LivingEntity;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class IsaacRuntimeData implements IIsaacRuntimeData {

    private final Map<EnumFriendTypes, EntityHandler> friendHeadMap = new EnumMap<>(EnumFriendTypes.class);
    private final Map<EnumFriendTypes, EntityHandler> friendTailMap = new EnumMap<>(EnumFriendTypes.class);
    private final Object2IntMap<EnumFriendTypes> friendCountMap = new Object2IntOpenHashMap<>();
    private final Map<EnumFriendTypes, List<EntityHandler>> friendListMap = new EnumMap<>(EnumFriendTypes.class);
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
