package lq2007.mcmod.isaacformc.register;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class StaticInvoker implements BiConsumer, Function {

    public final Class<?> aClass;
    public final String name;

    private Method method = null;

    public StaticInvoker(Class<?> aClass, String name) {
        this.aClass = aClass;
        this.name = name;
    }

    public <T> T invoke(Object... parameters) {
        if (method == null) {
            try {
                method = aClass.getDeclaredMethod(name);
                method.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
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
