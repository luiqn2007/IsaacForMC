package lq2007.mcmod.isaacformc.isaac.tear;

public enum EnumTearEffects {

    /**
     * todo 仅贴图变化
     */
    BLOOD(-3),
    YELLOW(-2),

    NORMAL(-1),
    TRACK(0),
    /**
     * 角色会吸引角色发射的眼泪。
     * 眼泪会不断飞向角色，若角色在发射眼泪后移动，那么眼泪将一直飞回角色身上。
     */
    REFLECTION(1),
    ;

    public final byte index;

    EnumTearEffects(int index) {
        this.index = (byte) index;
    }

    public static EnumTearEffects get(byte type) {
        switch (type) {
            case 0: return TRACK;
            case 1: return REFLECTION;
            default: return NORMAL;
        }
    }
}
