package lq2007.mcmod.isaacformc.isaac.tear;

public enum EnumTearEffects {

    NORMAL(-1),
    TRACK(0),
    ;

    public final byte index;

    EnumTearEffects(int index) {
        this.index = (byte) index;
    }

    public static EnumTearEffects get(byte type) {
        switch (type) {
            case 0: return TRACK;
            default: return NORMAL;
        }
    }
}
