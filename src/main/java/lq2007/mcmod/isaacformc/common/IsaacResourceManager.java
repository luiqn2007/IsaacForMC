package lq2007.mcmod.isaacformc.common;

import lq2007.mcmod.isaacformc.common.entity.EnumEntityType;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class IsaacResourceManager {

    private static final Map<String, ResourceLocation> MAP_RESOURCE_ENTITY = new HashMap<>();
    private static final Map<String, ResourceLocation> MAP_MODEL_ENTITY = new HashMap<>();

    public static ResourceLocation getEntityTexture(String name, EnumEntityType type) {
        if (MAP_RESOURCE_ENTITY.containsKey(name)) {
            return MAP_RESOURCE_ENTITY.get(name);
        } else {
            return newPath(name, "textures", type.name, MAP_RESOURCE_ENTITY);
        }
    }

    public static ResourceLocation getEntityModel(String name, EnumEntityType type) {
        if (MAP_MODEL_ENTITY.containsKey(name)) {
            return MAP_MODEL_ENTITY.get(name);
        } else {
            return newPath(name, "models", type.name, MAP_MODEL_ENTITY);
        }
    }

    private static ResourceLocation newPath(String name, String type, String path, Map<String, ResourceLocation> cache) {
        ResourceLocation resource = new ResourceLocation(Isaac.ID, type + "/" + path + "/" + name + ".png");
        cache.put(name, resource);
        return resource;
    }
}
