package lq2007.mcmod.isaacformc.isaac;

/**
 * The version of the game [The Binding of Isaac]
 */
public enum EnumIsaacVersion {

    EMPTY,
    ISAAC_REBIRTH,
    ISAAC_AFTER_REBIRTH,
    ISAAC_AFTER_REBIRTH2,
    MOD;

    public final int maxPropId;

    public boolean isAddedByMod() {
        return this == MOD;
    }

    public static EnumIsaacVersion fromPropId(int id) {
        if (id == 0) {
            return EMPTY;
        } else if (id > 0) {
            if (id <= ISAAC_REBIRTH.maxPropId) {
                return ISAAC_REBIRTH;
            } else if (id <= ISAAC_AFTER_REBIRTH.maxPropId) {
                return ISAAC_AFTER_REBIRTH;
            } else if (id <= ISAAC_AFTER_REBIRTH2.maxPropId) {
                return ISAAC_AFTER_REBIRTH2;
            }
        }
        return MOD;
    }
}
