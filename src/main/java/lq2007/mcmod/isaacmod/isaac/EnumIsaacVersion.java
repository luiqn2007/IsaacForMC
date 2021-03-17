package lq2007.mcmod.isaacmod.isaac;

/**
 * The version of the game [The Binding of Isaac]
 */
public enum EnumIsaacVersion {

    // todo id
    EMPTY(0),
    ISAAC_REBIRTH(0),
    ISAAC_AFTER_REBIRTH(0),
    ISAAC_AFTER_REBIRTH2(0),
    MOD(0);

    public final int lastPropId;

    EnumIsaacVersion(int lastPropId) {
        this.lastPropId = lastPropId;
    }

    public boolean isAddedByMod() {
        return this == MOD;
    }

    public static EnumIsaacVersion fromPropId(int id) {
        if (id == 0) {
            return EMPTY;
        } else if (id > 0) {
            if (id <= ISAAC_REBIRTH.lastPropId) {
                return ISAAC_REBIRTH;
            } else if (id <= ISAAC_AFTER_REBIRTH.lastPropId) {
                return ISAAC_AFTER_REBIRTH;
            } else if (id <= ISAAC_AFTER_REBIRTH2.lastPropId) {
                return ISAAC_AFTER_REBIRTH2;
            }
        }
        return MOD;
    }
}
