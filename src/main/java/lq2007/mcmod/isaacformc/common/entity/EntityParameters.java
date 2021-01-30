package lq2007.mcmod.isaacformc.common.entity;

import com.google.common.collect.ImmutableList;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearEffects;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearAppearances;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

public class EntityParameters {

    public static final IDataSerializer<Vector4f> SERIALIZER_V4F = new IDataSerializer<Vector4f>() {

        @Override
        public void write(PacketBuffer buf, Vector4f value) {
            buf.writeFloat(value.getX());
            buf.writeFloat(value.getY());
            buf.writeFloat(value.getZ());
            buf.writeFloat(value.getW());
        }

        @Override
        public Vector4f read(PacketBuffer buf) {
            return new Vector4f(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
        }

        @Override
        public Vector4f copyValue(Vector4f value) {
            return new Vector4f(value.getX(), value.getY(), value.getZ(), value.getW());
        }
    };
    public static final IDataSerializer<Vector3f> SERIALIZER_V3F = new IDataSerializer<Vector3f>() {

        @Override
        public void write(PacketBuffer buf, Vector3f value) {
            buf.writeFloat(value.getX());
            buf.writeFloat(value.getY());
            buf.writeFloat(value.getZ());
        }

        @Override
        public Vector3f read(PacketBuffer buf) {
            return new Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat());
        }

        @Override
        public Vector3f copyValue(Vector3f value) {
            return new Vector3f(value.getX(), value.getY(), value.getZ());
        }
    };
    public static final IDataSerializer<Vector2f> SERIALIZER_V2F = new IDataSerializer<Vector2f>() {

        @Override
        public void write(PacketBuffer buf, Vector2f value) {
            buf.writeFloat(value.x);
            buf.writeFloat(value.y);
        }

        @Override
        public Vector2f read(PacketBuffer buf) {
            return new Vector2f(buf.readFloat(), buf.readFloat());
        }

        @Override
        public Vector2f copyValue(Vector2f value) {
            return new Vector2f(value.x, value.y);
        }
    };
    public static final IDataSerializer<ImmutableList<EnumTearEffects>> SERIALIZER_TEAR_EFFECTS = new IDataSerializer<ImmutableList<EnumTearEffects>>() {
        @Override
        public void write(PacketBuffer buf, ImmutableList<EnumTearEffects> value) {
            buf.writeVarInt(value.size());
            for (EnumTearEffects effect : value) {
                buf.writeByte(effect.index);
            }
        }

        @Override
        public ImmutableList<EnumTearEffects> read(PacketBuffer buf) {
            EnumTearEffects[] effects = new EnumTearEffects[buf.readVarInt()];
            for (int i = 0; i < effects.length; i++) {
                effects[i] = EnumTearEffects.get(buf.readByte());
            }
            return ImmutableList.copyOf(effects);
        }

        @Override
        public ImmutableList<EnumTearEffects> copyValue(ImmutableList<EnumTearEffects> value) {
            return ImmutableList.copyOf(value);
        }
    };
    public static final IDataSerializer<ImmutableList<EnumTearAppearances>> SERIALIZER_TEAR_TEXTURES = new IDataSerializer<ImmutableList<EnumTearAppearances>>() {
        @Override
        public void write(PacketBuffer buf, ImmutableList<EnumTearAppearances> value) {
            buf.writeVarInt(value.size());
            for (EnumTearAppearances effect : value) {
                buf.writeByte(effect.index);
            }
        }

        @Override
        public ImmutableList<EnumTearAppearances> read(PacketBuffer buf) {
            EnumTearAppearances[] effects = new EnumTearAppearances[buf.readVarInt()];
            for (int i = 0; i < effects.length; i++) {
                effects[i] = EnumTearAppearances.get(buf.readByte());
            }
            return ImmutableList.copyOf(effects);
        }

        @Override
        public ImmutableList<EnumTearAppearances> copyValue(ImmutableList<EnumTearAppearances> value) {
            return ImmutableList.copyOf(value);
        }
    };
}
