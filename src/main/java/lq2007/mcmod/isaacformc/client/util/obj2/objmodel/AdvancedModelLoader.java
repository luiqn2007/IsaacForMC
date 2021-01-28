package lq2007.mcmod.isaacformc.client.util.obj2.objmodel;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;

import java.util.HashMap;
import java.util.Map;

public class AdvancedModelLoader {

    private final static Map<String, IModelCustomLoader> instances = new HashMap<>();

    public static void registerModelHandler(IModelCustomLoader modelHandler) {
        for (String suffix : modelHandler.getSuffixes()) {
            instances.put(suffix, modelHandler);
        }
    }

    public static IModelCustom loadModel(ResourceLocation resource) throws IllegalArgumentException, ModelFormatException {
        String name = resource.getResourcePath();
        int i = name.lastIndexOf('.');
        if (i == -1) {
            FMLLog.log.error("The resource name {} is not valid", resource);
            throw new IllegalArgumentException("The resource name is not valid");
        }
        String suffix = name.substring(i + 1);
        IModelCustomLoader loader = instances.get(suffix);
        if (loader == null) {
            FMLLog.log.error("The resource name {} is not supported", resource);
            throw new IllegalArgumentException("The resource name is not supported");
        } else {
            return loader.loadInstance(resource);
        }
    }
}
