package lq2007.mcmod.isaacformc.common.entity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

/**
 * 类似于 TameableEntity，但不带有坐/捆绑的操作
 * todo 非 player 的 Owner
 * @see net.minecraft.entity.passive.TameableEntity
 */
public class EntityFriend extends Entity {

    protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(TameableEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);

    protected LivingEntity owner;

    protected EntityFriend(EntityType<? extends EntityFriend> type, World worldIn) {
        super(type, worldIn);
        this.setupTamedAI();
    }

    @Override
    protected void registerData() {
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putUniqueId("Owner", this.getOwnerId());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
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
            this.setOwnerId(ownerId);
        } else {
            setDead();
        }
    }

    protected void setupTamedAI() { }

    public UUID getOwnerId() {
        return this.dataManager.get(OWNER_UNIQUE_ID).orElseThrow(RuntimeException::new);
    }

    public void setOwnerId(@Nullable UUID uuid) {
        if (uuid == null) {
            setDead();
        } else {
            this.dataManager.set(OWNER_UNIQUE_ID, Optional.of(uuid));
        }
    }

    public void setOwner(LivingEntity entity) {
        this.setOwnerId(entity.getUniqueID());
        this.owner = entity;
    }

    public LivingEntity getOwner() {
        if (owner == null) {
            UUID uuid = this.getOwnerId();
            owner = this.world.getPlayerByUuid(uuid);
            if (owner == null) {
                setDead();
            }
        }
        return owner;
    }
}
