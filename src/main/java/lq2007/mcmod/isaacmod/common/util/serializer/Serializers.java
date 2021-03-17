package lq2007.mcmod.isaacmod.common.util.serializer;

import lq2007.mcmod.isaacmod.common.util.ReflectionUtil;
import lq2007.mcmod.isaacmod.common.util.serializer.packet.*;
import lq2007.mcmod.isaacmod.common.util.serializer.buffer.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Serializers {

    private static final Map<Class<? extends Enum<?>>, EnumSerializer<?>> ENUM_SERIALIZER_MAP = new HashMap<>();

    public static final Map<Class<? extends INBTReader<?>>, INBTReader<?>> NBT_READER_MAP = new HashMap<>();
    public static final Map<Class<? extends INBTWriter<?>>, INBTWriter<?>> NBT_WRITER_MAP = new HashMap<>();
    public static final Map<Class<? extends INBTSerializer<?>>, INBTSerializer<?>> NBT_SERIALIZER_MAP = new HashMap<>();

    public static final Map<Class<? extends IPacketReader<?>>, IPacketReader<?>> PKT_READER_MAP = new HashMap<>();
    public static final Map<Class<? extends IPacketWriter<?>>, IPacketWriter<?>> PKT_WRITER_MAP = new HashMap<>();
    public static final Map<Class<? extends IPacketSerializer<?>>, IPacketSerializer<?>> PKT_SERIALIZER_MAP = new HashMap<>();

    public static final Map<Class<? extends ISerializer<?>>, ISerializer<?>> SERIALIZER_MAP = new HashMap<>();

    public static <T> IPacketWriter<T> getPacketWriter(Class<T> dataType, boolean compress) {
        PacketSerializer annotation = dataType.getAnnotation(PacketSerializer.class);
        if (annotation != null && annotation.writer() != IPacketWriter.class) {
            return ReflectionUtil.getOrCreate(annotation.reader(), PKT_WRITER_MAP);
        }
        IPacketSerializer<T> serializer = getPacketSerializer(dataType, compress);
        if (serializer != null) return serializer;
        if (IPacketWriteable.class.isAssignableFrom(dataType)) {
            return ObjectPacketSerializer.INSTANCE;
        }
        throw new NullPointerException("Unknown type " + dataType);
    }

    public static <T> IPacketReader<T> getPacketReader(Class<T> dataType, boolean compress) {
        PacketSerializer annotation = dataType.getAnnotation(PacketSerializer.class);
        if (annotation != null && annotation.reader() != IPacketReader.class) {
            return ReflectionUtil.getOrCreate(annotation.reader(), PKT_READER_MAP);
        }
        IPacketSerializer<T> serializer = getPacketSerializer(dataType, compress);
        if (serializer != null) return serializer;
        if (IPacketReadable.class.isAssignableFrom(dataType)) {
            return ObjectPacketSerializer.INSTANCE;
        }
        throw new NullPointerException("Unknown type " + dataType);
    }

    @Nullable
    public static <T> IPacketSerializer<T> getPacketSerializer(Class<?> dataType, boolean compress) {
        PacketSerializer annotation = dataType.getAnnotation(PacketSerializer.class);
        if (annotation != null && annotation.serializer() != IPacketSerializer.class) {
            return ReflectionUtil.getOrCreate(annotation.serializer(), PKT_SERIALIZER_MAP);
        }
        ISerializer<T> serializer = (ISerializer<T>) getSerializer(dataType, compress);
        if (serializer != null) return serializer;
        if (IPacketSerializable.class.isAssignableFrom(dataType)) {
            return ObjectPacketSerializer.INSTANCE;
        }
        return null;
    }

    public static <T> INBTWriter<T> getNBTWriter(Class<T> dataType) {
        lq2007.mcmod.isaacmod.common.util.serializer.packet.NBTSerializer annotation =
                dataType.getAnnotation(lq2007.mcmod.isaacmod.common.util.serializer.packet.NBTSerializer.class);
        if (annotation != null && annotation.writer() != INBTWriter.class) {
            return ReflectionUtil.getOrCreate(annotation.writer(), NBT_WRITER_MAP);
        }
        INBTSerializer<T> serializer = getNBTSerializer(dataType);
        if (serializer != null) return serializer;
        if (INBTWriteable.class.isAssignableFrom(dataType)) {
            return ObjectNBTSerializer.INSTANCE;
        }
        throw new NullPointerException("Unknown type " + dataType);
    }

    public static <T> INBTReader<T> getNBTReader(Class<T> dataType) {
        lq2007.mcmod.isaacmod.common.util.serializer.packet.NBTSerializer annotation =
                dataType.getAnnotation(lq2007.mcmod.isaacmod.common.util.serializer.packet.NBTSerializer.class);
        if (annotation != null && annotation.reader() != INBTReader.class) {
            return ReflectionUtil.getOrCreate(annotation.reader(), NBT_READER_MAP);
        }
        INBTSerializer<T> serializer = getNBTSerializer(dataType);
        if (serializer != null) return serializer;
        if (INBTReadable.class.isAssignableFrom(dataType)) {
            return ObjectNBTSerializer.INSTANCE;
        }
        throw new NullPointerException("Unknown type " + dataType);
    }

    @Nullable
    public static <T> INBTSerializer<T> getNBTSerializer(Class<?> dataType) {
        lq2007.mcmod.isaacmod.common.util.serializer.packet.NBTSerializer annotation =
                dataType.getAnnotation(lq2007.mcmod.isaacmod.common.util.serializer.packet.NBTSerializer.class);
        if (annotation != null && annotation.serializer() != INBTSerializer.class) {
            return ReflectionUtil.getOrCreate(annotation.serializer(), NBT_SERIALIZER_MAP);
        }
        ISerializer<T> serializer = (ISerializer<T>) getSerializer(dataType, false);
        if (serializer != null) return serializer;
        if (INBTSerializable.class.isAssignableFrom(dataType)) {
            return ObjectNBTSerializer.INSTANCE;
        }
        return null;
    }

    @Nullable
    public static <T> ISerializer<T> getSerializer(Class<T> dataType, boolean compress) {
        Serializer annotation = dataType.getAnnotation(Serializer.class);
        if (annotation != null) {
            return ReflectionUtil.getOrCreate(annotation.value(), SERIALIZER_MAP);
        }
        if (dataType == BlockPos.class) {
            return (ISerializer<T>) BlockPosSerializer.INSTANCE;
        } else if (dataType == boolean.class || dataType == Boolean.class) {
            return (ISerializer<T>) BooleanSerializer.INSTANCE;
        } else if (dataType == byte[].class) {
            return (ISerializer<T>) ByteArraySerializer.INSTANCE;
        } else if (dataType == float.class || dataType == Float.class) {
            return (ISerializer<T>) FloatSerializer.INSTANCE;
        } else if (dataType == int.class || dataType == Integer.class) {
            return (ISerializer<T>) (compress ? VarIntSerializer.INSTANCE : IntegerSerializer.INSTANCE);
        } else if (dataType == ItemStack.class) {
            return (ISerializer<T>) ItemStackSerializer.INSTANCE;
        } else if (dataType == long.class || dataType == Long.class) {
            return (ISerializer<T>) (compress ? VarLongSerializer.INSTANCE : LongSerializer.INSTANCE);
        } else if (dataType == CompoundNBT.class) {
            return (ISerializer<T>) NBTSerializer.INSTANCE;
        } else if (dataType == ResourceLocation.class) {
            return (ISerializer<T>) ResourceLocationSerializer.INSTANCE;
        } else if (dataType == String.class) {
            return (ISerializer<T>) StringSerializer.INSTANCE;
        } else if (dataType == UUID.class) {
            return (ISerializer<T>) UUIDSerializer.INSTANCE;
        } else if (Enum.class.isAssignableFrom(dataType)) {
            return (ISerializer<T>) ENUM_SERIALIZER_MAP.computeIfAbsent((Class<? extends Enum<?>>) dataType, c -> new EnumSerializer(c));
        } else {
            return null;
        }
    }
}
