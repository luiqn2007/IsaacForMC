package lq2007.mcmod.isaacformc.common.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Map;

public class ReflectionUtil {

    public static Table<Class<?>, String, Field> FIELD_TABLE = HashBasedTable.create();
    
    public static <T> T getOrCreate(Class<?> aClass, Map cache) {
        Object result = cache.get(aClass);
        if (result == null) {
            try {
                result = aClass.newInstance();
                cache.put(aClass, result);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return (T) result;
    }
    
    public static <T> T get(Class<?> aClass, @Nullable Object object, String name) {
        try {
            return (T) getField(aClass, name).get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Field getField(Class<?> aClass, String name) {
        return CollectionUtils.computeIfAbsent(FIELD_TABLE, aClass, name, () -> {
            try {
                Field f = aClass.getDeclaredField(name);
                f.setAccessible(true);
                return f;
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
