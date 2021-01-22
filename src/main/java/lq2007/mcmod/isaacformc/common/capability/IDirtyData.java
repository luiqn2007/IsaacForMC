package lq2007.mcmod.isaacformc.common.capability;

public interface IDirtyData {

    void markDirty();

    /**
     * Return true if the data is changed and need to keep synchronization.
     * @return True if the data is dirty
     */
    boolean isDirty();
}
