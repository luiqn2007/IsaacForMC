package lq2007.mcmod.isaacformc.common.entity.friend;

// todo to English
public enum EnumFriendTypes {

    /**
     * 其他
     */
    SPECIAL((byte) 1),
    /**
     * 环绕物
     */
    SURROUND((byte) 2),
    /**
     * 跟随
     */
    FOLLOWING((byte) 3),
    /**
     * 追踪敌人
     */
    TRACKER((byte) 4),

    NONE((byte) 0);

    public final byte index;

    EnumFriendTypes(byte index) {
        this.index = index;
    }

    public static EnumFriendTypes get(byte index) {
        switch (index) {
            case 1: return SPECIAL;
            case 2: return SURROUND;
            case 3: return FOLLOWING;
            case 4: return TRACKER;
            default: return NONE;
        }
    }
}
