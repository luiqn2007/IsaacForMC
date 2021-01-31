package lq2007.mcmod.isaacformc.common.entity;

import lq2007.mcmod.isaacformc.common.capability.IIsaacRuntimeData;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.entity.friend.EnumFriendTypes;
import lq2007.mcmod.isaacformc.common.network.IPacketReader;
import lq2007.mcmod.isaacformc.common.network.IPacketWriter;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

/**
 * todo
 *  uuid 处理：currentUuid 永远是用于确定上次保存的结果
 *  currentEntity 如果不存在则永远都是重新生成
 */
public abstract class EntityHandler implements INBTSerializable<CompoundNBT> {

    public static final EntityHandler EMPTY = new EntityHandler(PropItem.EMPTY, EnumFriendTypes.NONE) {
        @Override
        protected Optional<Entity> newEntity(LivingEntity owner) {
            return Optional.empty();
        }

        @Override
        public void back() { }

        @Override
        public void previous() { }
    };

    protected Object createFrom;
    protected EnumFriendTypes type;

    protected UUID currentUuid;
    protected Entity currentEntity;
    protected UUID ownerUuid;
    protected LivingEntity ownerEntity;

    protected int index = -1;
    protected EntityHandler prev = EMPTY;
    protected EntityHandler next = EMPTY;

    public EntityHandler(Object createFrom, EnumFriendTypes type) {
        this.createFrom = createFrom;
        this.type = type;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        if (currentUuid != null) {
            nbt.putUniqueId("_entity_uuid", currentUuid);
        }
        if (ownerUuid != null) {
            nbt.putUniqueId("_owner_uuid", ownerUuid);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.hasUniqueId("_entity_uuid")) {
            currentUuid = nbt.getUniqueId("_entity_uuid");
        };
        if (nbt.hasUniqueId("_owner_uuid")) {
            ownerUuid = nbt.getUniqueId("_owner_uuid");
        }
    }

    public Optional<Entity> getEntity(@Nullable World world) {
        if (world != null) {
            if (currentEntity == null && currentUuid != null && world instanceof ServerWorld) {
                currentEntity = ((ServerWorld) world).getEntityByUuid(currentUuid);
            }
        }
        return Optional.ofNullable(currentEntity);
    }

    public Optional<LivingEntity> getOwner(@Nullable World world) {
        if (world != null) {
            if (ownerEntity == null && ownerUuid != null) {
                ownerEntity = world.getPlayerByUuid(ownerUuid);
                if (ownerEntity == null && world instanceof ServerWorld) {
                    ownerEntity = (LivingEntity) ((ServerWorld) world).getEntityByUuid(ownerUuid);
                }
            }
        }
        return Optional.ofNullable(ownerEntity);
    }

    public EntityHandler getNext() {
        return next;
    }

    public EntityHandler getPrev() {
        return prev;
    }

    public void bindTo(EntityHandler prev) {
        this.prev = prev;
        this.next = prev.next;
        prev.next = this;

        this.index = this.prev.index + 1;
        this.next.back();
    }

    public void back() {
        index++;
        next.back();
    }

    public void previous() {
        index--;
        prev.previous();
    }

    public void spawnEntityIfNotExist(LivingEntity owner) {
        if (currentEntity == null || !currentEntity.isAlive()) {
            spawnEntity(owner);
        }
    }

    public void spawnEntityAndReplace(LivingEntity owner) {
        if (currentEntity != null && currentEntity.isAlive()) {
            currentEntity.remove();
        }
        spawnEntity(owner);
    }

    private void spawnEntity(LivingEntity owner) {
        IIsaacRuntimeData runtimeData = IsaacCapabilities.getRuntimeData(owner);
        newEntity(owner).ifPresent(entity -> {
            owner.world.addEntity(entity);

            this.ownerEntity = owner;
            this.ownerUuid = owner.getUniqueID();
            this.currentEntity = entity;
            this.currentUuid = entity.getUniqueID();

            entity.setHandler(this);
            runtimeData.insertFriend(this);
        });
    }

    abstract protected Optional<Entity> newEntity(LivingEntity owner);
}
