package lq2007.mcmod.isaacformc.common.capability;

import lq2007.mcmod.isaacformc.register.ObjectConstructor;
import lq2007.mcmod.isaacformc.register.registers.IRegister;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import org.objectweb.asm.Type;

import java.util.HashSet;
import java.util.Set;

public class CapabilityRegister implements IRegister {

    // interface, default, storage
    private Set<Object[]> capabilities = new HashSet<>();

    @Override
    public void cache(ClassLoader classLoader, Type clazz) {
        String name = clazz.getClassName();
        // check package and skip inner class
        if (name.startsWith("lq2007.mcmod.isaacformc.common.capability") && !name.contains("$")) {
            try {
                Class aClass = classLoader.loadClass(name);
                if (aClass.isInterface() && ICapabilityProvider.class.isAssignableFrom(aClass)) {
                    // remove 'I'
                    Class defaultInstance = classLoader.loadClass("lq2007.mcmod.isaacformc.common.capability." + aClass.getSimpleName().substring(1));
                    Capability.IStorage storage = INBTSerializable.class.isAssignableFrom(aClass) ? CompoundNBTStorage.get() : NoStorage.get();
                    capabilities.add(new Object[] { aClass, defaultInstance, storage });
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apply() {
        CapabilityManager manager = CapabilityManager.INSTANCE;
        for (Object[] objects : capabilities) {
            Class type = (Class) objects[0];
            Class instance = (Class) objects[1];
            Capability.IStorage storage = (Capability.IStorage) objects[2];
            try {
                ObjectConstructor factory = new ObjectConstructor(instance);
                manager.register(type, storage, factory);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
