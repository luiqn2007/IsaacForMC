package lq2007.mcmod.isaacformc.common.network;

import lq2007.mcmod.isaacformc.register.registers.IRegister;
import org.objectweb.asm.Type;

public class PacketRegister implements IRegister {

    @Override
    public void cache(ClassLoader classLoader, Type clazz) {
        String name = clazz.getClassName();
        // check package and skip inner class
        if (name.startsWith("lq2007.mcmod.isaacformc.common.network") && !name.contains("$")) {
            try {
                Class aClass = classLoader.loadClass(name);
                if (BasePacket.class.isAssignableFrom(aClass)) {

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
