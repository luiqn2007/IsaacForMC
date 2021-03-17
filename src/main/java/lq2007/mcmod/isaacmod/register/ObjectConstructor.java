package lq2007.mcmod.isaacmod.register;

import java.lang.reflect.Constructor;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class ObjectConstructor<T> implements Supplier<T>, Callable<T> {

    private final Constructor<? extends T> constructor;

    public ObjectConstructor(Class<? extends T> aClass) throws NoSuchMethodException {
        this.constructor = aClass.getConstructor();
        this.constructor.setAccessible(true);
    }

    @Override
    public T get() {
        try {
            return call();
        } catch (Exception e) {
            throw new RuntimeException("Can't create a instance with constructor.", e);
        }
    }

    @Override
    public T call() throws Exception {
        return constructor.newInstance();
    }
}
