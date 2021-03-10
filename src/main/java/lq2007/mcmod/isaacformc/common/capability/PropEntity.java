package lq2007.mcmod.isaacformc.common.capability;

import lq2007.mcmod.isaacformc.common.util.EntityHolder;
import lq2007.mcmod.isaacformc.common.util.serializer.nbt.INBTSerializable;
import lq2007.mcmod.isaacformc.common.util.serializer.nbt.NBTData;
import net.minecraft.entity.Entity;

import javax.annotation.Nullable;

public class PropEntity implements IPropEntity, INBTSerializable {

    @NBTData
    private EntityHolder<?> entity = null;

    @Override
    public boolean hasEntity() {
        return entity == null;
    }

    @Nullable
    @Override
    public EntityHolder<?> getEntity() {
        return entity;
    }

    @Override
    public void bindEntity(Entity entity) {
        this.entity = new EntityHolder<>(entity);
    }

    @Override
    public void copy(IPropEntity source) {
        this.entity = source.getEntity();
    }
}
