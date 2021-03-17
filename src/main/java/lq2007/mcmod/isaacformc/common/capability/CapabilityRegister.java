package lq2007.mcmod.isaacformc.common.capability;

import lq2007.mcmod.isaacformc.common.capability.storage.NBTStorage;
import lq2007.mcmod.isaacformc.common.capability.storage.NoStorage;
import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.register.ObjectConstructor;
import lq2007.mcmod.isaacformc.register.registers.IRegister;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.INBTSerializable;
import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;

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
        if (inPackage(aClass, "lq2007.mcmod.isaacformc.common.capability") && !className.contains("$")) {
            if (!aClass.isInterface()) {
                String interfaceName = "I" + aClass.getSimpleName();
                for (Class<?> anInterface : aClass.getInterfaces()) {
                    if (interfaceName.equals(anInterface.getSimpleName())) {
                        CAP_MAP.put(anInterface, aClass);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void apply() {
        CapabilityManager manager = CapabilityManager.INSTANCE;
        CAP_MAP.forEach((type, instance) -> {
            Capability.IStorage storage = INBTSerializable.class.isAssignableFrom(type) ? NBTStorage.get() : NoStorage.get();
            try {
                ObjectConstructor factory = new ObjectConstructor(instance);
                manager.register(type, storage, factory);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
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