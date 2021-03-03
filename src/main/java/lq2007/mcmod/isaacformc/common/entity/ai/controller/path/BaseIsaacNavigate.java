package lq2007.mcmod.isaacformc.common.entity.ai.controller.path;

import lq2007.mcmod.isaacformc.common.entity.friend.EntityFriend;
import lq2007.mcmod.isaacformc.common.util.EntityUtil;
import lq2007.mcmod.isaacformc.common.util.NBTUtils;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.*;

import static net.minecraftforge.common.util.Constants.NBT.TAG_INT_ARRAY;
import static net.minecraftforge.common.util.Constants.NBT.TAG_LIST;

/**
 * IsaacNavigate class must have a zero-parameter constructor!!!
 */
public abstract class BaseIsaacNavigate implements INBTSerializable<CompoundNBT> {

    protected List<UUID> friendList = new ArrayList<>();
    protected Map<UUID, EntityFriend<?>> friendMap = new HashMap<>();

    public abstract Vector3f getPosition(Entity owner, EntityFriend<?> friend);

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
}
