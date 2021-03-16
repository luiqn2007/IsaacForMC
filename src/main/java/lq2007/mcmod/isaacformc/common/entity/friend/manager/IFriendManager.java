package lq2007.mcmod.isaacformc.common.entity.friend.manager;

import lq2007.mcmod.isaacformc.common.entity.friend.EntityFriend;
import lq2007.mcmod.isaacformc.common.util.EntityHolder;
import lq2007.mcmod.isaacformc.common.util.serializer.ISerializer;
import lq2007.mcmod.isaacformc.common.util.serializer.Serializer;
import lq2007.mcmod.isaacformc.common.util.serializer.Serializers;
import lq2007.mcmod.isaacformc.common.util.serializer.packet.INBTSerializable;
import lq2007.mcmod.isaacformc.common.util.serializer.buffer.IPacketSerializable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Serializer(IFriendManager.Serializer.class)
public interface IFriendManager extends Iterable<EntityHolder<EntityFriend<?>>>, INBTSerializable, IPacketSerializable {

    IFriendManager EMPTY = Dummy.INSTANCE;

    FriendType getType();

    /**
     * Return if the manager contains the friend entity
     * @param friend friend
     * @return True if the manager contains the entity
     */
    boolean contains(UUID friend);

    /**
     * Return if the manager contains the friend entity
     * @param friend friend
     * @return True if the manager contains the entity
     */
    boolean contains(EntityFriend<?> friend);

    /**
     * Get the friend's index in the manager
     * @param friend friend
     * @return index
     */
    int getIndex(UUID friend);

    /**
     * Get the friend's index in the manager
     * @param friend friend
     * @return index
     */
    int getIndex(EntityFriend<?> friend);

    /**
     * Get the friend entity from the uuid
     * @param uuid uuid
     * @return entity holder
     */
    EntityHolder<EntityFriend<?>> getEntity(UUID uuid);

    /**
     * Get the friend entity at the index
     * @param index index
     * @return entity holder
     */
    EntityHolder<EntityFriend<?>> getEntity(int index);

    /**
     * Add a friend to the manager
     * @param world world if you can get the world, or use null
     * @param uuid the friend's uuid
     */
    void add(@Nullable World world, UUID uuid);

    /**
     * Add a friend to the manager
     * @param entity friend
     */
    void add(EntityFriend<?> entity);

    /**
     * Remove a friend from the manager
     * @param uuid friend's uuid
     * @return the removed friend holder
     */
    EntityHolder<EntityFriend<?>> remove(UUID uuid);

    default EntityHolder<EntityFriend<?>> remove(EntityFriend<?> entity) {
        return remove(entity.getUniqueID());
    }

    default EntityHolder<EntityFriend<?>> remove(EntityHolder<?> entity) {
        return remove(entity.uuid);
    }

    /**
     * Update the entity manager, set friend's position, etc.
     * @param owner owner
     */
    void updateEntities(LivingEntity owner);

    Iterator<Map.Entry<UUID, EntityHolder<EntityFriend<?>>>> mapIterator();

    class Dummy implements IFriendManager {

        private static final Dummy INSTANCE = new Dummy();

        @Override
        public FriendType getType() {
            return FriendType.NONE;
        }

        @Override
        public boolean contains(UUID friend) {
            return false;
        }

        @Override
        public boolean contains(EntityFriend<?> friend) {
            return false;
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
            return EntityHolder.empty();
        }

        @Override
        public EntityHolder<EntityFriend<?>> getEntity(int index) {
            return EntityHolder.empty();
        }

        @Override
        public void add(@Nullable World world, UUID uuid) { }

        @Override
        public void add(EntityFriend<?> entity) { }

        @Override
        public EntityHolder<EntityFriend<?>> remove(UUID uuid) {
            return EntityHolder.empty();
        }

        @Override
        public void updateEntities(LivingEntity owner) { }

        @Override
        public Iterator<Map.Entry<UUID, EntityHolder<EntityFriend<?>>>> mapIterator() {
            return Collections.emptyIterator();
        }

        @Override
        public Iterator<EntityHolder<EntityFriend<?>>> iterator() {
            return Collections.emptyIterator();
        }
    }

    class Serializer implements ISerializer<IFriendManager> {

        @Override
        public IFriendManager read(CompoundNBT nbt, String key) {
            CompoundNBT data = nbt.getCompound(key);
            IFriendManager manager = Serializers.getNBTReader(FriendType.class).read(data, "type").newInstance();
            manager.read(data.getCompound("data"));
            return manager;
        }

        @Override
        public CompoundNBT write(CompoundNBT nbt, String key, IFriendManager item) {
            CompoundNBT data = new CompoundNBT();
            Serializers.getNBTWriter(FriendType.class).write(data, "type", item.getType());
            data.put("data", item.serializeNBT());
            nbt.put(key, data);
            return nbt;
        }

        @Override
        public IFriendManager read(PacketBuffer buffer) {
            IFriendManager manager = Serializers.getPacketReader(FriendType.class, false).read(buffer).newInstance();
            manager.read(buffer);
            return manager;
        }

        @Override
        public PacketBuffer write(IFriendManager item, PacketBuffer buffer) {
            Serializers.getPacketWriter(FriendType.class, false).write(item.getType(), buffer);
            item.write(buffer);
            return buffer;
        }
    }
}
