package lq2007.mcmod.isaacformc.register;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class ObjectConstructor<T> implements Supplier<T> {

    private final Constructor<? extends T> constructor;

    public ObjectConstructor(Class<? extends T> aClass) throws NoSuchMethodException {
        this.constructor = aClass.getConstructor();
        this.constructor.setAccessible(true);
    }

    @Override
    public T get() {
        try {
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Can't create a instance with constructor.", e);
        }
    }
}
