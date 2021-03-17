package lq2007.mcmod.isaacmod.register;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class BiObjectConstructor<P, T> implements Function<P, T> {

    private Constructor<? extends T> constructor = null;

    public BiObjectConstructor(Class<? extends T> aClass, Class<P> parameter) {
        try {
            constructor = aClass.getDeclaredConstructor(parameter);
        } catch (NoSuchMethodException e) {
            for (Constructor<?> declaredConstructor : aClass.getDeclaredConstructors()) {
                if (declaredConstructor.getParameterCount() == 1
                        && declaredConstructor.getParameterTypes()[0].isAssignableFrom(parameter)) {
                    // compatible parent type
                    constructor = (Constructor<? extends T>) declaredConstructor;
                    constructor.setAccessible(true);
                    break;
                }
            }
        }
    }

    @Override
    public T apply(P p) {
        if (constructor != null) {
            try {
                return constructor.newInstance(p);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
