package lq2007.mcmod.isaacformc.isaac.tear;

/**
 * Effects provided special tear appearance, about size, color, texture, etc.
 */
public enum EnumTearAppearances {

    LION((byte) -2),
    // -1 留空，用于网络通信
    NORMAL((byte) 0),
    TRACK((byte) 1),
    BLOOD((byte) 2),
    YELLOW((byte) 3),
    BIGGER((byte) 4),
    BIGGER_W((byte) 5),
    SMALLER((byte) 6),
    ;

    public final byte index;

    EnumTearAppearances(byte index) {
        this.index = index;
    }

    public static EnumTearAppearances get(byte type) {
        switch (type) {
            case -1: return LION;
            case 1: return TRACK;
            case 2: return BLOOD;
            case 3: return YELLOW;
            case 4: return BIGGER;
            case 5: return BIGGER_W;
            case 6: return SMALLER;
            default: return NORMAL;
        }
    }
}
