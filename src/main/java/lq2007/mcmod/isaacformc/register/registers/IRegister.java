package lq2007.mcmod.isaacformc.register.registers;

import org.objectweb.asm.Type;

public interface IRegister {

    /**
     * Save this class to cache
     * @param classLoader ClassLoader
     * @param clazz class
     */
    void cache(ClassLoader classLoader, Type clazz);

    /**
     * Register cached elements
     */
    void apply();
}
