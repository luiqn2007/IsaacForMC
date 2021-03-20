package lq2007.mcmod.isaacmod.register.registers;

import lq2007.mcmod.isaacmod.register.ModifierTester;
import org.apache.commons.lang3.ArrayUtils;
import org.objectweb.asm.Type;

import javax.annotation.Nullable;

public interface IRegister {

    /**
     * Save this class to cache
     * @param classLoader ClassLoader
     * @param clazz class type
     * @param className class name
     * @param packageName package name
     * @param aClass class
     */
    void cache(ClassLoader classLoader, Type clazz, String className, String packageName, Class<?> aClass);

    /**
     * Register cached elements
     */
    void apply();

    default boolean isPackage(String classPackage, @Nullable String packageName) {
        return packageName == null || classPackage.startsWith(packageName);
    }

    default boolean isPackages(String classPackage, String... packageNames) {
        for (String packageName : packageNames) {
            if (isPackage(classPackage, packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * test to skip interface, anonymousClass, localClass, memberClass, enum, annotation, array and static inner class
     * @param aClass class
     * @return is normal class, it means the class is not inner class and can instantiate.
     */
    default boolean isInstantiable(Class<?> aClass) {
        return !aClass.isInterface()
                && !aClass.isAnonymousClass()
                && !aClass.isLocalClass()
                && !aClass.isMemberClass()
                && !aClass.isEnum()
                && !aClass.isAnnotation()
                && !aClass.isArray()
                && ModifierTester.notAbstract.test(aClass);
    }

    /**
     * test to ensure the class is extends anther class, but skip parent self.
     * @param parent parent
     * @param aClass class
     * @return the class is extends parent
     */
    default boolean isExtends(Class<?> aClass, Class<?> parent) {
        return aClass != parent && parent.isAssignableFrom(aClass);
    }

    default int getPriority() {
        return 0;
    }
}
