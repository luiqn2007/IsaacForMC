package lq2007.mcmod.isaacmod.common;

import lq2007.mcmod.isaacmod.Isaac;
import lq2007.mcmod.isaacmod.common.entity.EnumEntityType;
import lq2007.mcmod.isaacmod.common.prop.type.AbstractPropType;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

import static lq2007.mcmod.isaacmod.common.EnumResourceType.MODEL;
import static lq2007.mcmod.isaacmod.common.EnumResourceType.TEXTURE;

public class IsaacResourceManager {

    private static final Map<String, ResourceLocation> MAP_RESOURCE_ENTITY = new HashMap<>();
    private static final Map<String, ResourceLocation> MAP_MODEL_ENTITY = new HashMap<>();
    private static final Map<ResourceLocation, ResourceLocation> MAP_PROP_ICON = new HashMap<>();
    private static final Map<ResourceLocation, ResourceLocation> MAP_PROP_MODEL = new HashMap<>();

    public static ResourceLocation getEntityTexture(String name, EnumEntityType type) {
        return MAP_RESOURCE_ENTITY.computeIfAbsent(name, n -> newPath(TEXTURE, type.name, name));
    }

    public static ResourceLocation getEntityModel(String name, EnumEntityType type) {
        return MAP_MODEL_ENTITY.computeIfAbsent(name, n -> newPath(MODEL, type.name, name));
    }

    public static ResourceLocation getPropTexture(AbstractPropType type) {
        return MAP_PROP_ICON.computeIfAbsent(type.key, key -> newPath(TEXTURE, key.getNamespace(), "prop", key.getPath()));
    }

    public static ResourceLocation getPropModel(AbstractPropType type) {
        return MAP_PROP_MODEL.computeIfAbsent(type.key, key -> newPath(MODEL, key.getNamespace(), "prop", key.getPath()));
    }

    private static ResourceLocation newPath(EnumResourceType type, String path, String name) {
        return newPath(type, Isaac.ID, path, name);
    }

    private static ResourceLocation newPath(EnumResourceType type, String namespace, String path, String name) {
        return new ResourceLocation(namespace, type.type + "/" + path + "/" + name + "." + type.ext);
    }
}
