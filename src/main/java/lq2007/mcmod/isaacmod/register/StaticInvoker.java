package lq2007.mcmod.isaacmod.register;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class StaticInvoker implements BiConsumer, Function {

    public final Class<?> aClass;
    public final String name;
    public final Class<?>[] parameters;

    private Method method = null;

    public StaticInvoker(Class<?> aClass, String name, Class<?>... parameters) {
        this.aClass = aClass;
        this.name = name;
        this.parameters = parameters;

        for (Method method : aClass.getDeclaredMethods()) {
            Class<?>[] types = method.getParameterTypes();
            if (!method.getName().equals(name) || types.length != parameters.length) {
                continue;
            }
            boolean current = true;
            for (int i = 0; i < types.length; i++) {
                if (parameters[i] != Object.class && types[i] != parameters[i] && !parameters[i].isAssignableFrom(types[i])) {
                    current = false;
                    break;
                }
            }
            if (current) {
                this.method = method;
                method.setAccessible(true);
                break;
            }
        }
    }

    public <T> T invoke(Object... parameters) {
        if (method != null) {
            try {
                return (T) method.invoke(null, parameters);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void accept(Object o, Object o2) {
        invoke(o, o2);
    }

    @Override
    public Object apply(Object o) {
        return invoke(o);
    }
}
