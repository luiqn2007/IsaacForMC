package lq2007.mcmod.isaacformc.common.entity.friend.manager;

import lq2007.mcmod.isaacformc.common.entity.friend.EntityFriend;
import lq2007.mcmod.isaacformc.common.util.EntityHolder;
import lq2007.mcmod.isaacformc.common.util.serializer.buffer.BufferData;
import lq2007.mcmod.isaacformc.common.util.serializer.packet.NBTData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class FlyFriend implements IFriendManager {

    @NBTData(K = UUID.class, V = EntityHolder.class)
    @BufferData(K = UUID.class, V = EntityHolder.class)
    private Map<UUID, EntityHolder<EntityFriend<?>>> friends = new HashMap<>();

    @Override
    public FriendType getType() {
        return FriendType.FLY;
    }

    @Override
    public boolean contains(UUID friend) {
        return friends.containsKey(friend);
    }

    @Override
    public boolean contains(EntityFriend<?> friend) {
        return friends.containsKey(friend.getUniqueID());
    }

    @Override
    public int getIndex(UUID friend) {
        return 0;
    }

    @Override
    public int getIndex(EntityFriend<?> friend) {
        return 0;
    }

    @Override
    public EntityHolder<EntityFriend<?>> getEntity(UUID uuid) {
        return friends.getOrDefault(uuid, EntityHolder.empty());
    }

    @Override
    public EntityHolder<EntityFriend<?>> getEntity(int index) {
        return EntityHolder.empty();
    }

    @Override
    public void add(@Nullable World world, UUID uuid) {
        friends.put(uuid, new EntityHolder<>(world, uuid));
    }

    @Override
    public void add(EntityFriend<?> entity) {
        friends.put(entity.getUniqueID(), new EntityHolder<>(entity));
    }

    @Override
    public EntityHolder<EntityFriend<?>> remove(UUID uuid) {
        EntityHolder<EntityFriend<?>> removed = friends.remove(uuid);
        return removed == null ? EntityHolder.empty() : removed;
    }

    @Override
    public EntityHolder<EntityFriend<?>> remove(EntityFriend<?> entity) {
        return remove(entity.getUniqueID());
    }

    @Override
    public EntityHolder<EntityFriend<?>> remove(EntityHolder<?> entity) {
        return remove(entity.uuid);
    }

    @Override
    public void updateEntities(LivingEntity owner) {
        // todo set position
    }

    @Override
    public Iterator<Map.Entry<UUID, EntityHolder<EntityFriend<?>>>> mapIterator() {
        return friends.entrySet().iterator();
    }

    @Override
    public Iterator<EntityHolder<EntityFriend<?>>> iterator() {
        return friends.values().iterator();
    }
}
