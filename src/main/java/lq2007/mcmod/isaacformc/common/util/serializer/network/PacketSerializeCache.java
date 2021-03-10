package lq2007.mcmod.isaacformc.common.util.serializer.network;

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
                        wrapper.reader = IPacketReader.createReader(data.reader());
                    }
                    if (data.writer() != IPacketWriter.class) {
                        wrapper.writer = IPacketWriter.createWriter(data.writer());
                    }
                    if (data.serializer() != IPacketSerializer.class) {
                        IPacketSerializer<?> synchronizer = IPacketSerializer.createSerializer(data.serializer());
                        if (wrapper.reader == null) wrapper.reader = synchronizer;
                        if (wrapper.writer == null) wrapper.writer = synchronizer;
                    }
                    Class<?> type = field.getType();
                    if (Collection.class.isAssignableFrom(type)) {
                        wrapper.parent = data.collection();
                        wrapper.isCollection = true;
                        wrapper.readerV = IPacketReader.getReader(data.V(), data.compress());
                        wrapper.writerV = IPacketWriter.getWriter(data.V(), data.compress());
                    } else if (Map.class.isAssignableFrom(type)) {
                        wrapper.parent = data.map();
                        wrapper.isMap = true;
                        wrapper.readerK = IPacketReader.getReader(data.K(), data.compress());
                        wrapper.writerK = IPacketWriter.getWriter(data.K(), data.compress());
                        wrapper.readerV = IPacketReader.getReader(data.V(), data.compress());
                        wrapper.writerV = IPacketWriter.getWriter(data.V(), data.compress());
                    } else {
                        if (wrapper.reader == null) wrapper.reader = IPacketReader.getReader(type, data.compress());
                        if (wrapper.writer == null) wrapper.writer = IPacketWriter.getWriter(type, data.compress());
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
