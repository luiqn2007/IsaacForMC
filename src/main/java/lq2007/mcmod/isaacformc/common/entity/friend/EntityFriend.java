package lq2007.mcmod.isaacformc.common.entity.friend;

import lq2007.mcmod.isaacformc.common.entity.ai.controller.FriendMovementController;
import lq2007.mcmod.isaacformc.common.entity.ai.controller.FriendPathNavigator;
import lq2007.mcmod.isaacformc.common.util.EntityUtil;
import net.minecraft.entity.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.tags.ITag;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

/**
 * Like TameableEntity，but can't sit and ride, not have age, etc.
 * @see net.minecraft.entity.passive.TameableEntity
 *
 * todo override since swiming
 */
public class EntityFriend<T extends EntityFriend<T>> extends Entity implements IEntityAdditionalSpawnData {

    private LivingEntity owner;
    private Integer ownerId = null;
    private UUID ownerUuid = null;

    protected FriendMovementController moveController;
    protected PathNavigator navigator;

    protected EntityFriend(EntityType<T> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new FriendMovementController(this, 10);
        this.navigator = new FriendPathNavigator(this, worldIn, new FriendFollowingNavigateNode());
    }

    public EntityFriend(EntityType<T> entityTypeIn, LivingEntity owner) {
        this(entityTypeIn, owner.world);
        this.owner = owner;
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return false;
    }

    @Override
    public boolean hitByEntity(Entity entityIn) {
        return true;
    }

    @Override
    protected void registerData() {
        setNoGravity(true);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return true;
    }

    // todo kill by special command
    @Override
    public void onKillCommand() { }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeVarInt(getOwner().map(Entity::getEntityId).orElseThrow(NullPointerException::new));
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        ownerId = additionalData.readVarInt();
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        ownerUuid = compound.getUniqueId("_owner_uuid");
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putUniqueId("_owner_uuid", getOwnerId());
    }

    @Override
    public boolean handleFluidAcceleration(ITag<Fluid> fluidTag, double p_210500_2_) {
        return false;
    }

    public Optional<LivingEntity> getOwner() {
        if (owner == null) {
            if (ownerId != null) {
                owner = (LivingEntity) world.getEntityByID(ownerId);
            } else if (ownerUuid != null) {
                owner = (LivingEntity) EntityUtil.findEntityByUuid(world, ownerUuid);
            }
        }
        return Optional.ofNullable(owner);
    }

    public UUID getOwnerId() {
        if (ownerUuid == null) {
            ownerUuid = getOwner().map(LivingEntity::getUniqueID).orElseThrow(NullPointerException::new);
        }
        return ownerUuid;
    }

    public boolean isOwner(@Nullable Entity entity) {
        return entity instanceof LivingEntity && getOwner().orElse(null) == entity;
    }
}
