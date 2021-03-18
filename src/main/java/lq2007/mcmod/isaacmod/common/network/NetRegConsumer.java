package lq2007.mcmod.isaacmod.common.network;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;

public class NetRegConsumer implements BiConsumer {

    public final Class<?> aClass;
    public final String name;

    private Method method = null;

    public NetRegConsumer(Class<?> aClass, String name, Class<?> parameter) {
        this.aClass = aClass;
        this.name = name;

        for (Method method : aClass.getDeclaredMethods()) {
            Class<?>[] types = method.getParameterTypes();
            if (!method.getName().equals(name) || types.length != 2) {
                continue;
            }
            if (parameter == types[1] || parameter.isAssignableFrom(types[1])) {
                this.method = method;
                method.setAccessible(true);
                break;
            }
        }
    }

    @Override
    public void accept(Object o, Object o2) {
        if (method != null) {
            try {
                method.invoke(null, o, o2);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
