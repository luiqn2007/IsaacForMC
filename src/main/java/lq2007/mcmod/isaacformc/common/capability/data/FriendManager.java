package lq2007.mcmod.isaacformc.common.capability.data;

import lq2007.mcmod.isaacformc.common.entity.friend.EntityFriend;
import lq2007.mcmod.isaacformc.common.util.EntityHolder;
import lq2007.mcmod.isaacformc.common.util.serializer.nbt.INBTSerializable;
import lq2007.mcmod.isaacformc.common.util.serializer.nbt.NBTData;
import lq2007.mcmod.isaacformc.common.util.serializer.network.BufferData;
import lq2007.mcmod.isaacformc.common.util.serializer.network.IPacketSerializable;
import net.minecraft.world.World;

import java.util.*;

public abstract class FriendManager implements Iterable<Map.Entry<UUID, EntityHolder<EntityFriend<?>>>>, INBTSerializable, IPacketSerializable {

    @BufferData @NBTData
    protected List<UUID> uuidList = new ArrayList<>();
    protected Map<UUID, EntityHolder<EntityFriend<?>>> friendMap = new HashMap<>();

    public boolean contains(UUID friend) {
        return uuidList.contains(friend);
    }

    public boolean contains(EntityFriend<?> friend) {
        return contains(friend.getUniqueID());
    }

    public int getIndex(UUID friend) {
        return uuidList.indexOf(friend);
    }

    public int getIndex(EntityFriend<?> friend) {
        return getIndex(friend.getUniqueID());
    }

    public EntityHolder<EntityFriend<?>> getEntity(UUID uuid) {
        return friendMap.getOrDefault(uuid, EntityHolder.empty());
    }

    public EntityHolder<EntityFriend<?>> getEntity(int index) {
        if (uuidList.isEmpty()) {
            return EntityHolder.empty();
        }
        int current = index;
        while (current < 0) {
            current += uuidList.size();
        }
        if (current >= uuidList.size()) {
            return EntityHolder.empty();
        }
        return friendMap.getOrDefault(uuidList.get(current), EntityHolder.empty());
    }

    public void add(UUID uuid) {
        uuidList.add(uuid);
        friendMap.put(uuid, new EntityHolder<>(null, uuid));
    }

    public void add(EntityFriend<?> entity) {
        UUID uuid = entity.getUniqueID();
        uuidList.add(uuid);
        friendMap.put(uuid, new EntityHolder<>(entity));
    }

    public void remove(UUID uuid) {
        uuidList.remove(uuid);
        friendMap.remove(uuid);
    }

    public void remove(EntityFriend<?> entity) {
        remove(entity.getUniqueID());
    }

    @Override
    public Iterator<Map.Entry<UUID, EntityHolder<EntityFriend<?>>>> iterator() {
        return new MapIterator();
    }

    public Iterator<UUID> uuidIterator() {
        return new ListIterator();
    }

    protected class MapIterator implements Iterator<Map.Entry<UUID, EntityHolder<EntityFriend<?>>>> {

        Iterator<Map.Entry<UUID, EntityHolder<EntityFriend<?>>>> itr = friendMap.entrySet().iterator();
        Map.Entry<UUID, EntityHolder<EntityFriend<?>>> next = null;

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

        @Override
        public Map.Entry<UUID, EntityHolder<EntityFriend<?>>> next() {
            next = itr.next();
            return next;
        }

        @Override
        public void remove() {
            itr.remove();
            uuidList.remove(next.getKey());
        }
    }

    protected class ListIterator implements Iterator<UUID> {

        Iterator<UUID> itr = uuidList.iterator();
        UUID next = null;

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

        @Override
        public UUID next() {
            next = itr.next();
            return next;
        }

        @Override
        public void remove() {
            itr.remove();
            friendMap.remove(next);
        }
    }

    public abstract void updateAllEntities(World world);
}
