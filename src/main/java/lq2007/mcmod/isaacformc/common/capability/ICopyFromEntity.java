package lq2007.mcmod.isaacformc.common.capability;

import net.minecraft.entity.LivingEntity;

public interface ICopyFromEntity<T> {

    /**
     * Copy props from another entity.
     *
     * @param entity another entity
     * @return The side-effect of the operation.
     */
    T copyFrom(LivingEntity entity);
}
