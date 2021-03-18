package lq2007.mcmod.isaacmod.common.network;

import net.minecraft.network.PacketBuffer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class NetRegConstructor implements Function {

    private Constructor constructor = null;

    public NetRegConstructor(Class<?> aClass) {
        try {
            constructor = aClass.getDeclaredConstructor(PacketBuffer.class);
        } catch (NoSuchMethodException e) {
            for (Constructor<?> declaredConstructor : aClass.getDeclaredConstructors()) {
                if (declaredConstructor.getParameterCount() == 1
                        && declaredConstructor.getParameterTypes()[0].isAssignableFrom(PacketBuffer.class)) {
                    // compatible parent type
                    constructor = declaredConstructor;
                    constructor.setAccessible(true);
                    break;
                }
            }
        }
    }

    @Override
    public Object apply(Object o) {
        if (constructor != null) {
            try {
                return constructor.newInstance(o);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
