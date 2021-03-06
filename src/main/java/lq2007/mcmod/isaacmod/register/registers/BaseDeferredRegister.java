package lq2007.mcmod.isaacmod.register.registers;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lq2007.mcmod.isaacmod.register.IAutoApply;
import lq2007.mcmod.isaacmod.register.ObjectConstructor;
import lq2007.mcmod.isaacmod.register.Register;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.objectweb.asm.Type;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

import static lq2007.mcmod.isaacmod.Isaac.LOGGER;

public abstract class BaseDeferredRegister<T extends IForgeRegistryEntry<T>, V> implements IRegister, Iterable<RegistryObject<T>>, IAutoApply {

    public final DeferredRegister<T> register;
    public final IForgeRegistry<T> registry;

    public final Class<T> objectType; // the type saved in register, like EntityType
    public final Class<V> resultType; // the type created, like Entity
    public final Register context;
    public final String classPath;

    public final Map<Class<? extends V>, RegistryObject<T>> objMap = new HashMap<>();
    public final BiMap<Class<? extends V>, String> nameMap = HashBiMap.create();

    protected final List<Class<? extends V>> classes = new ArrayList<>();

    public BaseDeferredRegister(IForgeRegistry<T> registry, Register context, Class<?> resultType, @Nullable String classPath) {
        this.registry = registry;
        this.objectType = registry.getRegistrySuperType();
        this.resultType = (Class<V>) resultType;
        this.context = context;
        this.classPath = classPath;
        this.register = DeferredRegister.create(registry, context.modId);
        this.register.register(context.bus);
    }

    public BaseDeferredRegister(IForgeRegistry<T> registry, Register context, Class<?> resultType) {
        this(registry, context, resultType, null);
    }

    public BaseDeferredRegister(IForgeRegistry<T> registry, Register context, @Nullable String classPath) {
        this(registry, context, registry.getRegistrySuperType(), classPath);
    }

    public BaseDeferredRegister(IForgeRegistry<T> registry, Register context) {
        this(registry, context, registry.getRegistrySuperType(), null);
    }

    @Override
    public void cache(ClassLoader classLoader, Type clazz, String className, String packageName, Class<?> aClass) {
        if (isPackage(packageName, classPath) && isInstantiable(aClass) && isExtends(aClass, resultType)) {
            classes.add((Class<? extends V>) aClass);
            LOGGER.warn("\tCached as {}", objectType.getSimpleName());
        }
    }

    @Override
    public void apply() {
        if (classes.isEmpty()) return;
        for (Class<? extends V> aClass : classes) {
            try {
                String name = aClass.getSimpleName().toLowerCase(Locale.ROOT);
                Supplier<? extends T> build = build(name, aClass);
                if (build == null) {
                    LOGGER.warn("Skip register " + aClass.getName());
                    continue;
                }
                register(aClass, name, build);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @Nonnull
    public Iterator<RegistryObject<T>> iterator() {
        return objMap.values().iterator();
    }

    @Nullable
    protected Supplier<? extends T> build(String name, Class<? extends V> aClass) throws Exception {
        if (objectType.isAssignableFrom(aClass)) {
            return new ObjectConstructor<>((Class<? extends T>) aClass);
        }
        return null;
    }

    protected RegistryObject<T> register(Class<? extends V> aClass, String name, Supplier<? extends T> build) {
        RegistryObject<T> registryObject = register.register(name, build);
        objMap.put(aClass, registryObject);
        nameMap.put(aClass, name);
        LOGGER.warn("Isaac.{}: id={}, {}", objectType.getSimpleName(), registryObject.getId(), aClass.getName());
        return registryObject;
    }

    public RegistryObject<T> getObj(Class<? extends V> aClass) {
        return objMap.get(aClass);
    }

    public T get(Class<? extends V> aClass, T defaultValue) {
        return objMap.containsKey(aClass) ? objMap.get(aClass).get() : defaultValue;
    }

    public <T2 extends T> T2 get(Class<? extends V> aClass) {
        return (T2) objMap.get(aClass).get();
    }

    @Nullable
    public <T2 extends T> T2 getOrNull(@Nullable Class<? extends V> aClass) {
        if (aClass == null) return null;
        RegistryObject<T> object = objMap.get(aClass);
        if (object == null) return null;
        return (T2) object.get();
    }
}
