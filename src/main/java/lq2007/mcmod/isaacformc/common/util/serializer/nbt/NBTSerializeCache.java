package lq2007.mcmod.isaacformc.common.util.serializer.nbt;

import lq2007.mcmod.isaacformc.common.util.ReflectionUtil;
import lq2007.mcmod.isaacformc.common.util.serializer.Serializers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class NBTSerializeCache {

    public static final Map<Class<?>, NBTSerializeCache> FIELDS_MAP = new HashMap<>();

    public static NBTSerializeCache getOrCreate(Class<?> aClass) {
        return FIELDS_MAP.computeIfAbsent(aClass, NBTSerializeCache::new);
    }

    ArrayList<FieldNBTWrapper> fields = null;

    final Class<?> aClass;

    protected NBTSerializeCache(Class<?> aClass) {
        this.aClass = aClass;
    }

    void initFields() {
        if (fields == null) {
            fields = new ArrayList<>();

            for (Field field : aClass.getDeclaredFields()) {
                NBTData data = field.getAnnotation(NBTData.class);
                if (data != null) {
                    field.setAccessible(true);
                    FieldNBTWrapper wrapper = new FieldNBTWrapper(field);
                    if (data.reader() != INBTReader.class) {
                        wrapper.reader = ReflectionUtil.getOrCreate(data.reader(), Serializers.NBT_READER_MAP);
                    }
                    if (data.writer() != INBTWriter.class) {
                        wrapper.writer = ReflectionUtil.getOrCreate(data.writer(), Serializers.NBT_WRITER_MAP);
                    }
                    if (data.serializer() != INBTSerializer.class) {
                        INBTSerializer<?> synchronizer = ReflectionUtil.getOrCreate(data.serializer(), Serializers.NBT_SERIALIZER_MAP);
                        if (wrapper.reader == null) wrapper.reader = synchronizer;
                        if (wrapper.writer == null) wrapper.writer = synchronizer;
                    }
                    Class<?> type = field.getType();
                    if (Collection.class.isAssignableFrom(type)) {
                        wrapper.parent = data.collection();
                        wrapper.isCollection = true;
                        wrapper.readerV = Serializers.getNBTReader(data.V());
                        wrapper.writerV = Serializers.getNBTWriter(data.V());
                    } else if (Map.class.isAssignableFrom(type)) {
                        wrapper.parent = data.map();
                        wrapper.isMap = true;
                        wrapper.readerK = Serializers.getNBTReader(data.K());
                        wrapper.writerK = Serializers.getNBTWriter(data.K());
                        wrapper.readerV = Serializers.getNBTReader(data.V());
                        wrapper.writerV = Serializers.getNBTWriter(data.V());
                    } else {
                        if (wrapper.reader == null) wrapper.reader = Serializers.getNBTReader(type);
                        if (wrapper.writer == null) wrapper.writer = Serializers.getNBTWriter(type);
                    }
                    fields.add(wrapper);
                }
            }
        }
    }
}
