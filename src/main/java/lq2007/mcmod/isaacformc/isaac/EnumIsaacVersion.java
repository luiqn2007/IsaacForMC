package lq2007.mcmod.isaacformc.isaac;

/**
 * The version of the game [The Binding of Isaac]
 */
public enum EnumIsaacVersion {

    ISAAC, ISAAC_AB, ISAAC_AB2, MOD;

    public boolean isAddedByMod() {
        return this == MOD;
    }
}
