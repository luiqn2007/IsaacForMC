package lq2007.mcmod.isaacmod.common.capability;

import lq2007.mcmod.isaacmod.common.capability.storage.NBTStorage;
import lq2007.mcmod.isaacmod.common.capability.storage.NoStorage;
import lq2007.mcmod.isaacmod.common.prop.Prop;
import lq2007.mcmod.isaacmod.register.ObjectConstructor;
import lq2007.mcmod.isaacmod.register.registers.IRegister;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.INBTSerializable;
import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;

import static lq2007.mcmod.isaacmod.Isaac.LOGGER;

public class CapabilityRegister implements IRegister {

    @CapabilityInject(IIsaacProps.class)
    public static Capability<IIsaacProps> CAPABILITY_PROPS;

    @CapabilityInject(IIsaacProperty.class)
    public static Capability<IIsaacProperty> CAPABILITY_PROPERTY;

    @CapabilityInject(IIsaacRuntimeData.class)
    public static Capability<IIsaacRuntimeData> CAPABILITY_RUNTIME_DATA;

    @CapabilityInject(IPropEntity.class)
    public static Capability<IPropEntity> CAPABILITY_PROP_ENTITY;

    // interface, defaultInstance
    private Map<Class, Class> CAP_MAP = new HashMap<>();

    @Override
    public void cache(ClassLoader classLoader, Type clazz, String className, String packageName, Class<?> aClass) {
        // check package and skip inner class
        if (isPackage(packageName, "lq2007.mcmod.isaacmod.common.capability") && !className.contains("$")) {
            if (!aClass.isInterface()) {
                String interfaceName = "I" + aClass.getSimpleName();
                for (Class<?> anInterface : aClass.getInterfaces()) {
                    if (interfaceName.equals(anInterface.getSimpleName())) {
                        CAP_MAP.put(anInterface, aClass);
                        LOGGER.warn("\tCached as Capability");
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void apply() {
        int count = 0;
        LOGGER.warn("Capability apply begin");
        CapabilityManager manager = CapabilityManager.INSTANCE;
        for (Map.Entry<Class, Class> entry : CAP_MAP.entrySet()) {
            Class type = entry.getKey();
            Class instance = entry.getValue();
            Capability.IStorage storage = INBTSerializable.class.isAssignableFrom(type) ? NBTStorage.get() : NoStorage.get();
            try {
                LOGGER.warn("\tRegister {}", type);
                ObjectConstructor factory = new ObjectConstructor(instance);
                manager.register(type, storage, factory);
                count++;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        LOGGER.warn("Capability apply end, total {}", count);
    }

    public IIsaacProps getProps(LivingEntity entity) {
        return entity.getCapability(CAPABILITY_PROPS).orElseGet(IIsaacProps::dummy);
    }

    public IIsaacProperty getProperty(LivingEntity entity) {
        return entity.getCapability(CAPABILITY_PROPERTY).orElseGet(IIsaacProperty::dummy);
    }

    public IPropEntity getPropEntity(Prop prop) {
        return prop.getCapability(CAPABILITY_PROP_ENTITY).orElseGet(IPropEntity::dummy);
    }

    public IIsaacRuntimeData getRuntimeData(LivingEntity entity) {
        return entity.getCapability(CAPABILITY_RUNTIME_DATA).orElseGet(IIsaacRuntimeData::dummy);
    }
}
