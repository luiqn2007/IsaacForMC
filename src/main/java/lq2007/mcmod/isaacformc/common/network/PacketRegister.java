package lq2007.mcmod.isaacformc.common.network;

import lq2007.mcmod.isaacformc.common.capability.CompoundNBTStorage;
import lq2007.mcmod.isaacformc.common.capability.NoStorage;
import lq2007.mcmod.isaacformc.register.registers.IRegister;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import org.objectweb.asm.Type;

public class PacketRegister implements IRegister {

    @Override
    public void cache(ClassLoader classLoader, Type clazz) {
        String name = clazz.getClassName();
        // check package and skip inner class
        if (name.startsWith("lq2007.mcmod.isaacformc.common.network") && !name.contains("$")) {
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

    }
}
