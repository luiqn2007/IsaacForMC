package lq2007.mcmod.isaacformc.register;

import lq2007.mcmod.isaacformc.common.util.ReflectionUtil;
import lq2007.mcmod.isaacformc.register.registers.IRegister;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        IModFileInfo iFileInfo = container.getModInfo().getOwningFile();
        if (!(iFileInfo instanceof ModFileInfo)) {
            System.out.println("Skip " + container.getModId() + " because no ModFileInfo");
            return;
        }
        ModFileInfo fileInfo = (ModFileInfo) iFileInfo;
        System.out.println(fileInfo);
        ModFile modFile = fileInfo.getFile();
        ModFileScanData scanResult = modFile.getScanResult();
        Class<ModFileScanData.ClassData> aClass = ModFileScanData.ClassData.class;
        Field fClazz = ReflectionUtil.getField(aClass, "clazz");
        for (ModFileScanData.ClassData classData : scanResult.getClasses()) {
            try {
                Type clazz = (Type) fClazz.get(classData);
                String className = clazz.getClassName();
                Class<?> rClass = classLoader.loadClass(className);
                registers.forEach(r -> r.cache(classLoader, clazz, className, , rClass));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException ignored) { }
        }

        if (!sorted) {
            autos.sort(Comparator.comparingInt(AutoApply::getPriority));
            sorted = true;
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
            this.priority = ((IAutoApply) register).getPriority();
        }

        public int getPriority() {
            return priority;
        }
    }
}
