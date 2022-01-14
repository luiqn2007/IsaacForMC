package lq2007.mcmod.isaacmod.provider;

import lq2007.mcmod.isaacmod.Isaac;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ProviderEventHandler {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        try {
            Field clazz = ModFileScanData.ClassData.class.getDeclaredField("clazz");
            clazz.setAccessible(true);
            for (ModFileScanData.ClassData data : ModList.get().getModFileById(Isaac.ID).getFile().getScanResult().getClasses()) {
                try {
                    Type o = (Type) clazz.get(data);
                    String className = o.getClassName();
                    Class<?> aClass = ProviderEventHandler.class.getClassLoader().loadClass(className);
                    if (IDataProvider.class.isAssignableFrom(aClass)) {
                        System.out.println("Add provider: " + className);
                        for (Constructor<?> constructor : aClass.getConstructors()) {
                            if (constructor.getParameterCount() == 0) {
                                generator.addProvider((IDataProvider) constructor.newInstance());
                            } else if (constructor.getParameterCount() == 1 && constructor.getParameterTypes()[0] == DataGenerator.class) {
                                generator.addProvider((IDataProvider) constructor.newInstance(generator));
                            } else if (constructor.getParameterCount() == 1 && constructor.getParameterTypes()[0] == ExistingFileHelper.class) {
                                generator.addProvider((IDataProvider) constructor.newInstance(fileHelper));
                            } else if (constructor.getParameterCount() == 2 && constructor.getParameterTypes()[0] == DataGenerator.class && constructor.getParameterTypes()[1] == ExistingFileHelper.class) {
                                generator.addProvider((IDataProvider) constructor.newInstance(generator, fileHelper));
                            } else if (constructor.getParameterCount() == 2 && constructor.getParameterTypes()[1] == DataGenerator.class && constructor.getParameterTypes()[0] == ExistingFileHelper.class) {
                                generator.addProvider((IDataProvider) constructor.newInstance(fileHelper, generator));
                            }
                        }
                    }
                } catch (IllegalAccessException | ClassNotFoundException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

//        generator.addProvider(new ProviderLanguage(generator));
//        generator.addProvider(new ProviderSource(generator));
    }
}
