package lq2007.mcmod.isaacformc.isaac.util;

import lq2007.mcmod.isaacformc.common.capability.IIsaacPropData;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.entity.friend.EnumFriendTypes;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.data.IDataWithFriends;
import lq2007.mcmod.isaacformc.isaac.prop.data.IPropData;
import net.minecraft.entity.LivingEntity;

public class IsaacUtil {

    public static void checkAchievement(IIsaacPropData propData) {}

    public static void checkSuit() {}

    public static int nextFriendIndex(LivingEntity owner, EnumFriendTypes type) {
        IIsaacPropData propData = IsaacCapabilities.getPropData(owner);
        int maxIndex = 0;
        for (PropItem allProp : propData.getAllProps()) {
            IPropData data = allProp.data;
            if (data instanceof IDataWithFriends) {
                for (IDataWithFriends.FriendData friend : ((IDataWithFriends) data).getFriends()) {
                    if (type == friend.type && friend.hasEntity()) {
                        if (maxIndex < friend.index) {
                            maxIndex = friend.index;
                        }
                    }
                }
            }
        }
        return maxIndex;
    }
}
