package lq2007.mcmod.isaacmod.common.prop.type;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lq2007.mcmod.isaacmod.register.IAutoApply;
import lq2007.mcmod.isaacmod.register.registers.IRegister;
import net.minecraft.util.ResourceLocation;
import org.objectweb.asm.Type;

import java.util.*;

import static lq2007.mcmod.isaacmod.Isaac.LOGGER;

public class PropRegister implements IRegister, IAutoApply {

    private static final Map<ResourceLocation, AbstractPropType> PROPS = new HashMap<>();
    private static final Map<ResourceLocation, AbstractPropType> PASSIVE_PROPS = new HashMap<>();
    private static final Map<ResourceLocation, AbstractPropType> ACTIVE_PROPS = new HashMap<>();
    private static final Int2ObjectMap<AbstractPropType> PROP_BY_ID = new Int2ObjectArrayMap<>();

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
        if (prop.id >= 0) {
            PROP_BY_ID.put(prop.id, prop);
        }
        if (prop.isActive()) {
            ACTIVE_PROPS.put(key, prop);
        } else {
            PASSIVE_PROPS.put(key, prop);
        }
        return prop;
    }

    public AbstractPropType get(int id, AbstractPropType defaultValue) {
        return PROP_BY_ID.getOrDefault(id, defaultValue);
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
        for (Class<?> type : types) {
            try {
                LOGGER.warn("Isaac.prop {}", type);
                type.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
