package lq2007.mcmod.isaacmod.common.prop.type;

import lq2007.mcmod.isaacmod.register.IAutoApply;
import lq2007.mcmod.isaacmod.register.registers.IRegister;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import java.util.*;

public class PropRegister implements IRegister, IAutoApply {

    public static final Logger LOGGER = LogManager.getLogger();

    private static final Map<ResourceLocation, AbstractPropType> PROPS = new HashMap<>();
    private static final Map<ResourceLocation, AbstractPropType> PASSIVE_PROPS = new HashMap<>();
    private static final Map<ResourceLocation, AbstractPropType> ACTIVE_PROPS = new HashMap<>();

    public <T extends AbstractPropType> Optional<T> get(ResourceLocation key) {
        return Optional.ofNullable((T) PROPS.get(key));
    }

    public AbstractPropType get(ResourceLocation key, AbstractPropType defaultValue) {
        return PROPS.getOrDefault(key, defaultValue);
    }

    public AbstractPropType register(AbstractPropType prop) {
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
        if (aClass == EmptyProp.class) return;
        if (isExtends(aClass, AbstractPropType.class) && isInstantiable(aClass)) {
            LOGGER.warn("\tCached as prop");
            types.add(aClass);
        }
    }

    @Override
    public void apply() {
        int count = 0;
        LOGGER.warn("Prop apply begin");
        for (Class<?> type : types) {
            try {
                LOGGER.warn("\tRegister {}", type);
                type.newInstance();
                count++;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        LOGGER.warn("Prop apply end, total {}", count);
    }
}
