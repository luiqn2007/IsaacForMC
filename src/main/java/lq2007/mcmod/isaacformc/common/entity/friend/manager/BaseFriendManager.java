package lq2007.mcmod.isaacformc.common.entity.friend.manager;

import lq2007.mcmod.isaacformc.common.entity.friend.EntityFriend;
import lq2007.mcmod.isaacformc.common.util.EntityHolder;
import lq2007.mcmod.isaacformc.common.util.serializer.packet.NBTData;
import lq2007.mcmod.isaacformc.common.util.serializer.buffer.BufferData;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public abstract class BaseFriendManager implements IFriendManager {

    @NBTData(V = EntityHolder.class)
    @BufferData(V = EntityHolder.class)
    protected List<EntityHolder<EntityFriend<?>>> friendList = new ArrayList<>();
    protected Map<UUID, EntityHolder<EntityFriend<?>>> friendMap = new HashMap<>();

    @Override
    public boolean contains(UUID friend) {
        return friendMap.containsKey(friend);
    }

    @Override
    public boolean contains(EntityFriend<?> friend) {
        return contains(friend.getUniqueID());
    }

    @Override
    public int getIndex(UUID friend) {
        for (int i = 0; i < friendList.size(); i++) {
            if (friendList.get(i).match(friend)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getIndex(EntityFriend<?> friend) {
        return getIndex(friend.getUniqueID());
    }

    @Override
    public EntityHolder<EntityFriend<?>> getEntity(UUID uuid) {
        return friendList.stream()
                .filter(holder -> holder.match(uuid))
                .findFirst()
                .orElse(EntityHolder.empty());
    }

    @Override
    public EntityHolder<EntityFriend<?>> getEntity(int index) {
        if (friendList.isEmpty()) {
            return EntityHolder.empty();
        }
        // do if index < 0
        int current = index;
        while (current < 0) {
            current += friendList.size();
        }
        if (current >= friendList.size()) {
            return EntityHolder.empty();
        }
        return friendList.get(current);
    }

    @Override
    public void add(@Nullable World world, UUID uuid) {
        EntityHolder<EntityFriend<?>> holder = new EntityHolder<>(world, uuid);
        friendList.add(holder);
        friendMap.put(uuid, holder);
    }

    @Override
    public void add(EntityFriend<?> entity) {
        UUID uuid = entity.getUniqueID();
        EntityHolder<EntityFriend<?>> holder = new EntityHolder<>(entity);
        friendList.add(holder);
        friendMap.put(uuid, holder);
    }

    @Override
    public EntityHolder<EntityFriend<?>> remove(UUID uuid) {
        EntityHolder<EntityFriend<?>> h = friendMap.remove(uuid);
        if (friendList.removeIf(holder -> holder.match(uuid))) {
            return h;
        }
        return EntityHolder.empty();
    }

    @Override
    public Iterator<EntityHolder<EntityFriend<?>>> iterator() {
        return new ListIterator();
    }

    @Override
    public Iterator<Map.Entry<UUID, EntityHolder<EntityFriend<?>>>> mapIterator() {
        return new MapIterator();
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
            friendList.removeIf(holder -> holder.match(next.getKey()));
        }
    }

    protected class ListIterator implements Iterator<EntityHolder<EntityFriend<?>>> {

        Iterator<EntityHolder<EntityFriend<?>>> itr = friendList.iterator();
        EntityHolder<EntityFriend<?>> next = null;

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

        @Override
        public EntityHolder<EntityFriend<?>> next() {
            next = itr.next();
            return next;
        }

        @Override
        public void remove() {
            itr.remove();
            friendMap.remove(next.uuid);
        }
    }
}
