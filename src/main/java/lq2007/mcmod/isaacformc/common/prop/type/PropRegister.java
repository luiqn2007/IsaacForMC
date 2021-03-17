package lq2007.mcmod.isaacformc.common.prop.type;

import lq2007.mcmod.isaacformc.register.IAutoApply;
import lq2007.mcmod.isaacformc.register.registers.IRegister;
import net.minecraft.util.ResourceLocation;
import org.objectweb.asm.Type;

import java.util.*;

public class PropRegister implements IRegister, IAutoApply {

    public static final AbstractPropType EMPTY = register(EmptyProp.EMPTY);

    private static final Map<ResourceLocation, AbstractPropType> PROPS = new HashMap<>();
    private static final Map<ResourceLocation, AbstractPropType> PASSIVE_PROPS = new HashMap<>();
    private static final Map<ResourceLocation, AbstractPropType> ACTIVE_PROPS = new HashMap<>();

    public static <T extends AbstractPropType> Optional<T> get(ResourceLocation key) {
        return Optional.ofNullable((T) PROPS.get(key));
    }

    public static AbstractPropType get(ResourceLocation key, AbstractPropType defaultValue) {
        return PROPS.getOrDefault(key, defaultValue);
    }

    public static AbstractPropType register(AbstractPropType prop) {
        ResourceLocation key = prop.key;
        if (PROPS.containsKey(key)) {
            throw new RuntimeException("Reduplicated Key " + key + "!");
        }
        PROPS.put(key, prop);
        if (prop.isActive()) {
            ACTIVE_PROPS.put(key, prop);
        } else {
            PASSIVE_PROPS.put(key, prop);
        }
        return prop;
    }

    private List<Class<?>> types = new LinkedList<>();

    @Override
    public void cache(ClassLoader classLoader, Type clazz, String className, String packageName, Class<?> aClass) {
        if (inSubPackage(packageName, "lq2007.mcmod.isaacformc.common.prop.type")
                && isInstantiable(AbstractPropType.class, aClass)) {
            types.add(aClass);
        }
    }

    @Override
    public void apply() {
        for (Class<?> type : types) {
            try {
                register((AbstractPropType) type.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
