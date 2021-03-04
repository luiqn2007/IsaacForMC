package lq2007.mcmod.isaacformc.common.capability.data;

import lq2007.mcmod.isaacformc.common.entity.friend.EntityFriend;
import lq2007.mcmod.isaacformc.common.util.EntityUtil;
import lq2007.mcmod.isaacformc.common.util.NBTUtils;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.*;

import static net.minecraftforge.common.util.Constants.NBT.TAG_INT_ARRAY;
import static net.minecraftforge.common.util.Constants.NBT.TAG_LIST;

public class IsaacFriends implements INBTSerializable<CompoundNBT> {

    public static final Map<String, Type> TYPES = new HashMap<>();

    public final Type type;
    private List<UUID> friendList = new ArrayList<>();
    private final Map<UUID, EntityFriend<?>> friendMap = new HashMap<>();

    public IsaacFriends(Type type) {
        this.type = type;
    }

    public void remove(Entity owner, EntityFriend<?> friend) {
        UUID uuid = friend.getUniqueID();
        friendMap.remove(uuid);
        friendList.remove(uuid);
    }

    public void add(Entity owner, EntityFriend<?> friend) {
        UUID uuid = friend.getUniqueID();
        friendMap.put(uuid, friend);
        friendList.add(uuid);
    }

    public <T extends EntityFriend<?>> Optional<T> get(Entity owner, int index) {
        return (owner == null || friendList.size() <= index) ? Optional.empty() : get(owner, friendList.get(index));
    }

    public <T extends EntityFriend<?>> Optional<T> get(Entity owner, UUID uuid) {
        EntityFriend<?> friend = friendMap.getOrDefault(uuid, null);
        if (friend == null) {
            friend = EntityUtil.findEntityByUuid(owner.world, uuid, EntityFriend.class);
        }
        return Optional.of((T) friend);
    }

    public int getIndex(@Nullable UUID id) {
        if (id == null) return -1;
        return friendList.indexOf(id);
    }

    public int getIndex(EntityFriend<?> friend) {
        return getIndex(friend.getUniqueID());
    }

    public boolean isEmpty() {
        return friendList.isEmpty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("_friends", NBTUtils.convert(friendList, NBTUtil::func_240626_a_));
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        friendMap.clear();
        friendList.clear();
        if (nbt.contains("_friends", TAG_LIST)) {
            friendList = NBTUtils.convert(nbt.getList("_friends", TAG_INT_ARRAY), NBTUtil::readUniqueId);
        }
    }

    /**
     * empty. use for null-safe.
     */
    public static final Type NONE = new Type("none") {

        final IsaacFriends i = new IsaacFriends(this) {
            @Override
            public void add(Entity owner, EntityFriend<?> friend) { }

            @Override
            public void remove(Entity owner, EntityFriend<?> friend) { }

            @Override
            public <T extends EntityFriend<?>> Optional<T> get(Entity owner, int index) {
                return Optional.empty();
            }

            @Override
            public <T extends EntityFriend<?>> Optional<T> get(Entity owner, UUID uuid) {
                return Optional.empty();
            }

            @Override
            public int getIndex(@Nullable UUID id) {
                return -1;
            }

            @Override
            public int getIndex(EntityFriend<?> friend) {
                return -1;
            }

            @Override
            public boolean isEmpty() {
                return true;
            }

            @Override
            public CompoundNBT serializeNBT() {
                return new CompoundNBT();
            }

            @Override
            public void deserializeNBT(CompoundNBT nbt) { }
        };

        @Override
        public IsaacFriends newInstance() {
            return i;
        }
    };

    /**
     * friends following the owner
     */
    public static final Type FOLLOWING = new Type("following");

    /**
     * friends around the owner
     */
    public static final Type SURROUND = new Type("surround");

    /**
     * use custom path
     */
    public static final Type FREEDOM = new Type("freedom");

    /**
     * blue fly, white fly, etc...
     */
    public static final Type FLY = new Type("fly");

    public static class Type {

        protected final String name;

        public Type(String name) {
            this.name = name;
            TYPES.put(name, this);
        }

        public String getName() {
            return name;
        }

        public IsaacFriends newInstance() {
            return new IsaacFriends(this);
        }
    }
}
