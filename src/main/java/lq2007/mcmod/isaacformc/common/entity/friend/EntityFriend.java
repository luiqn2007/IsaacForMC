package lq2007.mcmod.isaacformc.common.entity.friend;

import lq2007.mcmod.isaacformc.common.util.EntityHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
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
 * It will not save to disk.
 * @see net.minecraft.entity.passive.TameableEntity
 *
 * todo override since swiming
 */
public class EntityFriend<T extends EntityFriend<T>> extends Entity implements IEntityAdditionalSpawnData {

    private final EntityHolder<LivingEntity> owner;

    protected EntityFriend(EntityType<T> type, World worldIn, @Nullable UUID ownerId) {
        super(type, worldIn);
        this.owner = new EntityHolder<>(worldIn, ownerId);
    }

    public EntityFriend(EntityType<T> entityTypeIn, LivingEntity owner) {
        this(entityTypeIn, owner.world, null);
        this.owner.setEntity(owner);
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
        owner.setEntity(world, additionalData.readVarInt());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        owner.setEntity(world, compound.getUniqueId("ownerUuid"));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putUniqueId("ownerUuid", getOwnerId());
    }

    @Override
    public boolean handleFluidAcceleration(ITag<Fluid> fluidTag, double p_210500_2_) {
        return false;
    }

    @Override
    public boolean writeUnlessPassenger(CompoundNBT compound) {
        return false;
    }

    @Override
    public boolean writeUnlessRemoved(CompoundNBT compound) {
        return false;
    }

    @Override
    public CompoundNBT writeWithoutTypeId(CompoundNBT compound) {
        return compound;
    }

    public Optional<LivingEntity> getOwner() {
        return owner.getEntity(world);
    }

    public UUID getOwnerId() {
        return owner.getUuid(world);
    }

    public boolean isOwner(@Nullable Entity entity) {
        return owner.match(entity);
    }
}
