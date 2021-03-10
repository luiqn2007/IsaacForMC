package lq2007.mcmod.isaacformc.common.util.serializer.network;

import lq2007.mcmod.isaacformc.common.util.ReflectionUtil;
import lq2007.mcmod.isaacformc.common.util.serializer.Serializers;
import net.minecraft.network.PacketBuffer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public interface IPacketWriter<T> {

    Map<Class<?>, IPacketWriter<?>> WRITER_MAP = new HashMap<>();

    /**
     * Write data to a buffer.
     *
     * @param buffer buffer
     */
    PacketBuffer write(T item, PacketBuffer buffer);

    static <T> IPacketWriter<T> getWriter(Class<?> type, boolean varInt) {
        PacketSerializer annotation = type.getAnnotation(PacketSerializer.class);
        if (annotation != null) {
            if (annotation.writer() != IPacketWriter.class) {
                return createWriter(annotation.writer());
            }
            if (annotation.serializer() != IPacketSerializer.class) {
                return IPacketSerializer.createSerializer(annotation.serializer());
            }
        }
        return Objects.requireNonNull((IPacketWriter<T>) Serializers.getPacketWriter(type, varInt), "Unknown type: " + type);
    }

    static <T> IPacketWriter<T> createWriter(Class<? extends IPacketWriter> aClass) {
        return ReflectionUtil.getOrCreate(aClass, WRITER_MAP);
    }
}
