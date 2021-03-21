package lq2007.mcmod.isaacmod.register;

import lq2007.mcmod.isaacmod.common.util.ReflectionUtil;
import lq2007.mcmod.isaacmod.register.registers.IRegister;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static lq2007.mcmod.isaacmod.Isaac.LOGGER;

public class Register {

    public final List<IRegister> registers;
    public final List<AutoApply> autos;
    public final ModContainer container;
    public final String modId;
    public final IEventBus bus;
    public final ClassLoader classLoader;

    private boolean sorted = false;

    public Register() {
        this.registers = new ArrayList<>();
        this.autos = new ArrayList<>();
        this.container = ModLoadingContext.get().getActiveContainer();
        this.modId = container.getModId();
        this.bus = FMLJavaModLoadingContext.get().getModEventBus();
        this.classLoader = Register.class.getClassLoader();
    }

    public <T extends IRegister> T add(T register) {
        registers.add(register);
        if (register instanceof IAutoApply) {
            autos.add(new AutoApply(register));
            sorted = false;
        }
        return register;
    }

    public void execute() {
        if (!sorted) {
            autos.sort(Comparator.comparingInt(AutoApply::getPriority));
            registers.sort(Comparator.comparingInt(IRegister::getPriority));
            sorted = true;
        }

        IModFileInfo iFileInfo = container.getModInfo().getOwningFile();
        if (!(iFileInfo instanceof ModFileInfo)) {
            LOGGER.warn("Skip {} because no ModFileInfo", container.getModId());
            return;
        }
        ModFileInfo fileInfo = (ModFileInfo) iFileInfo;
        ModFile modFile = fileInfo.getFile();
        ModFileScanData scanResult = modFile.getScanResult();
        Class<ModFileScanData.ClassData> aClass = ModFileScanData.ClassData.class;
        Field fClazz = ReflectionUtil.getField(aClass, "clazz");
        for (ModFileScanData.ClassData classData : scanResult.getClasses()) {
            try {
                Type clazz = (Type) fClazz.get(classData);
                String className = clazz.getClassName();
                Class<?> rClass = classLoader.loadClass(className);
                String packageName = rClass.getPackage().getName();
                LOGGER.warn("Search {}", className);
                registers.forEach(r -> r.cache(classLoader, clazz, className, packageName, rClass));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException ignored) { }
        }

        for (AutoApply auto : autos) {
            auto.register.apply();
        }
    }

    private static class AutoApply {

        final IRegister register;
        final int priority;

        public AutoApply(IRegister register) {
            this.register = register;
            this.priority = register.getPriority();
        }

        public int getPriority() {
            return priority;
        }
    }
}
