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
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.util.*;

import static lq2007.mcmod.isaacmod.Isaac.LOGGER;

public class Register {

    public final List<IRegister> registers;
    public Map<Class<? extends IRegister>, List<IRegister>> registersByClass;
    public final List<AutoApply> autos;
    public final ModContainer container;
    public final String modId;
    public final IEventBus bus;
    public final ClassLoader classLoader;

    private boolean init = false;

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
            init = false;
        }
        return register;
    }

    public void execute() {
        if (!init) {
            autos.sort(Comparator.comparingInt(AutoApply::getPriority));
            registers.sort(Comparator.comparingInt(IRegister::getPriority));
            registersByClass = new HashMap<>();
            for (IRegister register : registers) {
                registersByClass.computeIfAbsent(register.getClass(), c -> new ArrayList<>()).add(register);
            }
            init = true;
        }

        IModFileInfo iFileInfo = container.getModInfo().getOwningFile();
        if (!(iFileInfo instanceof ModFileInfo)) {
            LOGGER.warn("Skip register {} because no ModFileInfo", container.getModId());
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
                Class<?> rClass = ReflectionUtil.loadClass(className, classLoader);
                if (rClass == null) {
                    LOGGER.warn("Skip register {} because class can't found", className);
                    continue;
                }
                Appoint appoint = rClass.getAnnotation(Appoint.class);
                String packageName = rClass.getPackage().getName();
                LOGGER.warn("Search {}", className);
                List<IRegister> registers;
                if (appoint != null) {
                    registers = new ArrayList<>();
                    if (appoint.value().length == 0) continue;
                    for (Class<? extends IRegister> registerType : appoint.value()) {
                        registers.addAll(registersByClass.getOrDefault(registerType, Collections.emptyList()));
                    }
                    registers.sort(Comparator.comparingInt(IRegister::getPriority));
                } else {
                    registers = this.registers;
                }
                registers.forEach(r -> r.cache(classLoader, clazz, className, packageName, rClass));
            } catch (IllegalAccessException e) {
                LOGGER.warn("Skip register object {}.clazz because can't access.", classData);
            }
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
