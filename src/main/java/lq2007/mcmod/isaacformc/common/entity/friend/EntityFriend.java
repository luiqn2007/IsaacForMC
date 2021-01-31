package lq2007.mcmod.isaacformc.common.entity.friend;

import lq2007.mcmod.isaacformc.isaac.IsaacElement;
import lq2007.mcmod.isaacformc.isaac.data.friend.IDataWithFriends;
import lq2007.mcmod.isaacformc.isaac.util.IsaacUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

/**
 * 类似于 TameableEntity，但不带有坐/捆绑的操作
 * todo 非 player 的 Owner
 * @see net.minecraft.entity.passive.TameableEntity
 */
public abstract class EntityFriend extends Entity implements IEntityAdditionalSpawnData {

    public static final DataParameter<Integer> PARAMETER_INDEX = EntityDataManager.createKey(EntityFriend.class, DataSerializers.VARINT);

    protected LivingEntity owner;
    protected final EnumFriendTypes friendType;
    public IDataWithFriends data;

    protected EntityFriend(EntityType<?> type, World worldIn, EnumFriendTypes friendType) {
        super(type, worldIn);
        this.friendType = friendType;
        this.setupTamedAI();
    }

    public EntityFriend(EntityType<?> entityTypeIn, LivingEntity owner, EnumFriendTypes friendType) {
        super(entityTypeIn, owner.world);
        this.friendType = friendType;
        this.setOwner(owner);
        this.setupTamedAI();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void registerData() {
        getDataManager().register(PARAMETER_OWNER, Optional.empty());
        getDataManager().register(PARAMETER_INDEX, 0);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        getOwnerId().ifPresent(uuid -> compound.putUniqueId("Owner", uuid));
        compound.putInt("FriendIndex", getFriendIndex());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        UUID ownerId;
        if (compound.hasUniqueId("Owner")) {
            ownerId = compound.getUniqueId("Owner");
        } else {
            String s = compound.getString("Owner");
            ownerId = PreYggdrasilConverter.convertMobOwnerIfNeeded(getServer(), s);
        }

        if (ownerId != null) {
            setOwnerId(ownerId);
            setFriendIndex(compound.getInt("FriendIndex"));
        } else {
            setDead();
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeUniqueId(getOwnerId().orElseThrow(RuntimeException::new));
        buffer.writeVarInt(getFriendIndex());
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        setOwnerId(additionalData.readUniqueId());
        setFriendIndex(additionalData.readVarInt());
    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
        getDataManager().set(PARAMETER_OWNER, Optional.of(owner.getUniqueID()));
        getDataManager().set(PARAMETER_INDEX, IsaacUtil.nextFriendIndex(owner, friendType));
    }

    public Optional<UUID> getOwnerId() {
        return getDataManager().get(PARAMETER_OWNER);
    }

    public void setOwnerId(UUID uuid) {
        getDataManager().set(PARAMETER_OWNER, Optional.of(uuid));
    }

    public Optional<LivingEntity> getOwner() {
        if (owner == null) {
            Optional<UUID> uuid = getOwnerId();
            if (uuid.isPresent()) {
                UUID ownerId = uuid.get();
                owner = world.getPlayerByUuid(ownerId);
                if (this.owner == null) {
                    if (world instanceof ServerWorld) {
                        this.owner = (LivingEntity) ((ServerWorld) world).getEntityByUuid(ownerId);
                    }
                }
            }
        }
        return Optional.ofNullable(owner);
    }

    public int getFriendIndex() {
        return getDataManager().get(PARAMETER_INDEX);
    }

    public void setFriendIndex(int friendIndex) {
        getDataManager().set(PARAMETER_INDEX, friendIndex);
    }

    public EnumFriendTypes getFriendType() {
        return friendType;
    }

    protected void setupTamedAI() { }
}
