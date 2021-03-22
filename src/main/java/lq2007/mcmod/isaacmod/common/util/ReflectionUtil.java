package lq2007.mcmod.isaacmod.common.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static lq2007.mcmod.isaacmod.Isaac.LOGGER;

public class ReflectionUtil {

    public static Table<Class<?>, String, Field> FIELD_TABLE = HashBasedTable.create();
    public static String[] CLASSES_SKIPPED = new String[0];

    public static void setSkipClass(String... prefix) {
        CLASSES_SKIPPED = prefix;
    }

    @Nullable
    public static <T> T instantiate(@Nullable Class<?> aClass) {
        if (aClass != null) {
            try {
                Constructor<?> c = aClass.getDeclaredConstructor();
                c.setAccessible(true);
                return (T) c.newInstance();
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                LOGGER.warn("Can't instantiate class {} because {}", aClass.getName(), e.getClass().getSimpleName());
            }
        }
        LOGGER.warn("Can't instantiate class {} because class is null", aClass.getName());
        return null;
    }

    @Nullable
    public static <T> T instantiate(String className) {
        return instantiate(loadClass(className, ReflectionUtil.class.getClassLoader()));
    }

    @Nullable
    public static <T> T instantiate(Constructor<?> c, Object parameter) {
        try {
            return (T) c.newInstance(parameter);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            LOGGER.warn("Can't instantiate class {} because {}", c.getDeclaringClass().getName(), e.getClass().getSimpleName());
            return null;
        }
    }

    @Nullable
    public static Constructor<?> getConstructor(@Nullable Class<?> aClass, Class<?> pType) {
        try {
            Constructor<?> c = aClass.getDeclaredConstructor(pType);
            c.setAccessible(true);
            return c;
        } catch (NoSuchMethodException e) {
            LOGGER.warn("Can't get constructor with parameter {} in {}", pType.getName(), aClass.getName());
            return null;
        }
    }

    @Nullable
    public static <T> Class<? extends T> loadClass(String className) {
        return loadClass(className, ReflectionUtil.class.getClassLoader());
    }

    @Nullable
    public static <T> Class<? extends T> loadClass(String className, ClassLoader classLoader) {
        for (String s : CLASSES_SKIPPED) {
            if (className.startsWith(s)) {
                LOGGER.warn("Skip load class because class {} start with {}", className, s);
                return null;
            }
        }
        try {
            return (Class<? extends T>) classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            LOGGER.warn("Skip {} because not found.", className);
        } catch (Exception e) {
            LOGGER.warn("Unexpected exception {} while loading class {}", e.getClass().getSimpleName(), className);
            e.printStackTrace();
        }
        return null;
    }

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
