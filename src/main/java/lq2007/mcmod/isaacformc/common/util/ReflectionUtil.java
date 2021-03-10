package lq2007.mcmod.isaacformc.common.util;

import java.util.Map;

public class ReflectionUtil {

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
}
