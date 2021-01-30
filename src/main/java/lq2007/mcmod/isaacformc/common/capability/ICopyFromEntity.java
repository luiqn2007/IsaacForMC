package lq2007.mcmod.isaacformc.common.capability;

import net.minecraft.entity.LivingEntity;

public interface ICopyFromEntity<T> {

    /**
     * Copy props from another entity.
     *
     * @param entity another entity
     */
    void copyFrom(LivingEntity entity);
}
