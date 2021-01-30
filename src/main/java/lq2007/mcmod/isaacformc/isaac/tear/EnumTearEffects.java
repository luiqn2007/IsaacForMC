package lq2007.mcmod.isaacformc.isaac.tear;

/**
 * Effects applied to tear and provided some special actions, like track etc.
 */
public enum EnumTearEffects {

    // -1 留空，用于通信结尾
    NORMAL((byte) 0),
    TRACK((byte) 1),
    REFLECTION((byte) 2),
    ;

    public final byte index;

    EnumTearEffects(byte index) {
        this.index = index;
    }

    public static EnumTearEffects get(byte type) {
        switch (type) {
            case 1: return TRACK;
            case 2: return REFLECTION;
            default: return NORMAL;
        }
    }
}
