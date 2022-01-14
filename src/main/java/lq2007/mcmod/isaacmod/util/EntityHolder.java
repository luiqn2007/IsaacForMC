package lq2007.mcmod.isaacmod.util;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class EntityHolder<T extends Entity> {

    public static final EntityHolder EMPTY = new EntityHolder(null, null);

    public static final <T extends Entity> EntityHolder<T> empty() {
        return EMPTY;
    }

    public int id;
    public UUID uuid;

    private boolean hasId;

    public T entity;

    public EntityHolder(T entity) {
        this.entity = entity;
        this.id = entity.getEntityId();
        this.uuid = entity.getUniqueID();

        this.hasId = true;
    }

    public EntityHolder(@Nullable World world, int id) {
        this.id = id;
        this.uuid = null;
        this.entity = null;

        this.hasId = true;

        loadEntity(world);
    }

    public EntityHolder(@Nullable World world, @Nullable UUID uuid) {
        this.id = 0;
        this.uuid = uuid;
        this.entity = null;

        this.hasId = false;

        loadEntity(world);
    }

    public void setEntity(T entity) {
        this.uuid = entity.getUniqueID();
        this.id = entity.getEntityId();
        this.hasId = true;
        this.entity = entity;
    }

    public void setEntity(@Nullable World world, int id) {
        this.uuid = null;
        this.id = id;
        this.hasId = true;
        this.entity = null;

        loadEntity(world);
    }

    public void setEntity(@Nullable World world, UUID uuid) {
        this.uuid = uuid;
        this.id = 0;
        this.hasId = false;
        this.entity = null;

        loadEntity(world);
    }

    public final Optional<T> getEntity(@Nullable World world) {
        loadEntity(world);
        return Optional.ofNullable(entity);
    }

    public UUID getUuid(@Nullable World world) {
        if (uuid != null) {
            return uuid;
        }
        loadEntity(world);
        return uuid;
    }

    public boolean match(@Nullable Entity entity) {
        if (entity == null) return false;
        if (this.entity != null) return this.entity == entity;
        if (this.uuid != null) return this.uuid.equals(entity.getUniqueID());
        if (this.hasId) return this.id == entity.getEntityId();
        return false;
    }

    public boolean match(UUID entity) {
        if (this.uuid != null) return uuid.equals(entity);
        else if (this.entity != null) return this.entity.getUniqueID().equals(entity);
        else return false;
    }

    public boolean isEmpty() {
        return entity != null && uuid != null && !hasId;
    }

    public boolean isExist(World world) {
        loadEntity(world);
        return entity != null;
    }

    protected void loadEntity(@Nullable World world) {
        if (world == null) return;
        if (entity != null && !entity.isAlive()) return;

        if (hasId) {
            entity = (T) world.getEntityByID(id);
            if (entity != null) {
                uuid = entity.getUniqueID();
            }
        }

        if (entity == null && uuid != null) {
            entity = (T) EntityUtil.findEntityByUuid(world, uuid, false);
            if (entity != null) {
                hasId = true;
                id = entity.getEntityId();
            }
        }
    }
}
