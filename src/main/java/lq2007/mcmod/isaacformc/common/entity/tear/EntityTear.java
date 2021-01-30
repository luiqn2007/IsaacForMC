package lq2007.mcmod.isaacformc.common.entity.tear;

import com.google.common.collect.ImmutableList;
import lq2007.mcmod.isaacformc.common.entity.EntityParameters;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearEffects;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearAppearances;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityTear extends Entity implements IEntityAdditionalSpawnData {

    public static DataParameter<ImmutableList<EnumTearAppearances>> TEXTURES = EntityDataManager.createKey(EntityTear.class, EntityParameters.SERIALIZER_TEAR_TEXTURES);

    public ImmutableList<EnumTearEffects> effects = ImmutableList.of();
    public ImmutableList<EnumTearAppearances> textures = ImmutableList.of();
    public int existsTime = 100; /* todo */
    public int existsLength = 100; /* todo */
    public int flyDistance = 0;
    public float speed = 100; /* todo */
    public float damage = 100; /* todo */
    public float red = 0; /* todo */
    public float green = 0; /* todo */
    public float blue = 0; /* todo */
    public float alpha = 1; /* todo */

    public EntityTear(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public EntityTear(EntityType<?> entityTypeIn, World worldIn,
                      int existsTime, int existsLength,
                      float speed, float damage,
                      EnumTearEffects[] effects, EnumTearAppearances[] textures) {
        super(entityTypeIn, worldIn);
        this.existsTime = existsTime;
        this.existsLength = existsLength;
        this.speed = speed;
        this.damage = damage;
        this.effects = ImmutableList.copyOf(effects);

        this.textures = ImmutableList.copyOf(textures);
        resetData();
    }

    @Override
    protected void registerData() {
        getDataManager().register(TEXTURES, textures);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        existsTime = compound.getInt("tear_exist_time");
        existsLength = compound.getInt("tear_exist_len");
        flyDistance = compound.getInt("tear_distance");
        speed = compound.getFloat("tear_speed");
        damage = compound.getFloat("tear_damage");
        red = compound.getFloat("tear_red");
        green = compound.getFloat("tear_green");
        blue = compound.getFloat("tear_blue");
        alpha = compound.getFloat("tear_alpha");
        byte[] effectsIdxArray = compound.getByteArray("tear_effects");
        EnumTearEffects[] effectsArray = new EnumTearEffects[effectsIdxArray.length];
        for (int i = 0; i < effectsIdxArray.length; i++) {
            effectsArray[i] = EnumTearEffects.get(effectsIdxArray[i]);
        }
        effects = ImmutableList.copyOf(effectsArray);
        byte[] texturesIdxArray = compound.getByteArray("tear_textures");
        EnumTearAppearances[] texturesArray = new EnumTearAppearances[texturesIdxArray.length];
        for (int i = 0; i < texturesIdxArray.length; i++) {
            texturesArray[i] = EnumTearAppearances.get(texturesIdxArray[i]);
        }
        textures = ImmutableList.copyOf(texturesArray);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("tear_exist_time", existsTime);
        compound.putInt("tear_exist_len", existsLength);
        compound.putInt("tear_distance", flyDistance);
        compound.putFloat("tear_speed", speed);
        compound.putFloat("tear_damage", damage);
        compound.putFloat("tear_red", red);
        compound.putFloat("tear_green", green);
        compound.putFloat("tear_blue", blue);
        compound.putFloat("tear_alpha", alpha);
        byte[] effectsIdxArray = new byte[effects.size()];
        for (int i = 0; i < effectsIdxArray.length; i++) {
            effectsIdxArray[i] = effects.get(i).index;
        }
        compound.putByteArray("tear_effects", effectsIdxArray);
        byte[] texturesIdxArray = new byte[effects.size()];
        for (int i = 0; i < texturesIdxArray.length; i++) {
            texturesIdxArray[i] = effects.get(i).index;
        }
        compound.putByteArray("tear_textures", texturesIdxArray);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        for (EnumTearAppearances texture : textures) {
            buffer.writeByte(texture.index);
        }
        buffer.writeByte(-1);
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        ImmutableList.Builder<EnumTearAppearances> builder = new ImmutableList.Builder<>();
        byte next = additionalData.readByte();
        while (next != -1) {
            builder.add(EnumTearAppearances.get(next));
            next = additionalData.readByte();
        }
        textures = builder.build();
        resetData();
    }

    private void resetData() {
        // todo set color and position
    }
}
