package lq2007.mcmod.isaacformc.common.entity.tear;

import com.google.common.collect.Lists;
import lq2007.mcmod.isaacformc.common.util.serializer.ISerializer;
import lq2007.mcmod.isaacformc.common.util.serializer.Serializers;
import lq2007.mcmod.isaacformc.common.util.serializer.packet.INBTSerializable;
import lq2007.mcmod.isaacformc.common.util.serializer.packet.NBTData;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearAppearances;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityTear extends ThrowableEntity implements IEntityAdditionalSpawnData, INBTSerializable {

    @NBTData(V = EnumTearEffects.class)
    public List<EnumTearEffects> effects = new ArrayList<>();
    @NBTData(V = EnumTearAppearances.class)
    public List<EnumTearAppearances> textures = new ArrayList<>();
    @NBTData public int existsTime = 100; /* todo */
    @NBTData public int existsLength = 100; /* todo */
    @NBTData public int flyDistance = 0;
    @NBTData public float speed = 100; /* todo */
    @NBTData public float damage = 100; /* todo */
    @NBTData public float red = 1; /* todo */
    @NBTData public float green = 1; /* todo */
    @NBTData public float blue = 1; /* todo */
    @NBTData public float alpha = 1; /* todo */

    public EntityTear(EntityType<? extends ThrowableEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public EntityTear(EntityType<? extends ThrowableEntity> entityTypeIn, World worldIn,
                      int existsTime, int existsLength,
                      float speed, float damage,
                      EnumTearEffects[] effects, EnumTearAppearances[] textures) {
        super(entityTypeIn, worldIn);
        this.existsTime = existsTime;
        this.existsLength = existsLength;
        this.speed = speed;
        this.damage = damage;
        Collections.addAll(this.effects, effects);
        Collections.addAll(this.textures, textures);
    }

    @Override
    protected void registerData() { }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        INBTSerializable.super.read(compound);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        INBTSerializable.super.write(compound);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeVarInt(textures.size());
        ISerializer<EnumTearAppearances> serializer = Serializers.getSerializer(EnumTearAppearances.class, false);
        for (EnumTearAppearances appearance : textures) serializer.write(appearance, buffer);
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        EnumTearAppearances[] appearances = new EnumTearAppearances[additionalData.readVarInt()];
        ISerializer<EnumTearAppearances> serializer = Serializers.getSerializer(EnumTearAppearances.class, false);
        for (int i = 0; i < appearances.length; i++) appearances[i] = serializer.read(additionalData);
        textures = Lists.newArrayList(appearances);
    }

    @Override
    public CompoundNBT serializeNBT() {
        return super.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt);
    }

    @Override
    public boolean isInWater() {
        return false;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }
}
