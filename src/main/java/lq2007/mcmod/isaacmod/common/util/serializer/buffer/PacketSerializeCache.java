package lq2007.mcmod.isaacmod.common.util.serializer.buffer;

import lq2007.mcmod.isaacmod.common.util.ReflectionUtil;
import lq2007.mcmod.isaacmod.common.util.serializer.Serializers;

import java.lang.reflect.Field;
import java.util.*;

public class PacketSerializeCache {

    public static final Map<Class<?>, PacketSerializeCache> FIELDS_MAP = new HashMap<>();
    
    public static PacketSerializeCache getOrCreate(Class<?> aClass) {
        return FIELDS_MAP.computeIfAbsent(aClass, PacketSerializeCache::new);
    }
    
    ArrayList<FieldPacketWrapper> nullableFields = null;
    ArrayList<FieldPacketWrapper> nonnullFields = null;
    
    final Class<?> aClass;

    protected PacketSerializeCache(Class<?> aClass) {
        this.aClass = aClass;
    }

    void initFields() {
        if (nullableFields == null || nonnullFields == null) {
            nullableFields = new ArrayList<>();
            nonnullFields = new ArrayList<>();

            for (Field field : aClass.getDeclaredFields()) {
                BufferData data = field.getAnnotation(BufferData.class);
                if (data != null) {
                    field.setAccessible(true);
                    FieldPacketWrapper wrapper = new FieldPacketWrapper(field);
                    if (data.reader() != IPacketReader.class) {
                        wrapper.reader = ReflectionUtil.getOrCreate(data.reader(), Serializers.PKT_READER_MAP);
                    }
                    if (data.writer() != IPacketWriter.class) {
                        wrapper.reader = ReflectionUtil.getOrCreate(data.writer(), Serializers.PKT_WRITER_MAP);
                    }
                    if (data.serializer() != IPacketSerializer.class) {
                        IPacketSerializer<?> synchronizer = ReflectionUtil.getOrCreate(data.serializer(), Serializers.PKT_SERIALIZER_MAP);
                        if (wrapper.reader == null) wrapper.reader = synchronizer;
                        if (wrapper.writer == null) wrapper.writer = synchronizer;
                    }
                    Class<?> type = field.getType();
                    if (Collection.class.isAssignableFrom(type)) {
                        wrapper.parent = data.collection();
                        wrapper.isCollection = true;
                        wrapper.readerV = Serializers.getPacketReader(data.V(), data.compress());
                        wrapper.writerV = Serializers.getPacketWriter(data.V(), data.compress());
                    } else if (Map.class.isAssignableFrom(type)) {
                        wrapper.parent = data.map();
                        wrapper.isMap = true;
                        wrapper.readerK = Serializers.getPacketReader(data.K(), data.compress());
                        wrapper.writerK = Serializers.getPacketWriter(data.K(), data.compress());
                        wrapper.readerV = Serializers.getPacketReader(data.V(), data.compress());
                        wrapper.writerV = Serializers.getPacketWriter(data.V(), data.compress());
                    } else {
                        if (wrapper.reader == null) wrapper.reader = Serializers.getPacketReader(type, data.compress());
                        if (wrapper.writer == null) wrapper.writer = Serializers.getPacketWriter(type, data.compress());
                    }
                    if (data.nullable()) {
                        nullableFields.add(wrapper);
                    } else {
                        nonnullFields.add(wrapper);
                    }
                }
            }

            int nullableCount = nullableFields.size();
            if (nullableCount > 8) {
                throw new RuntimeException("We can only save 8 nullable fields at most");
            }

            Collections.sort(nullableFields);
            Collections.sort(nonnullFields);
        }
    }
}
