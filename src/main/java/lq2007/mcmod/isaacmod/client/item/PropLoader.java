package lq2007.mcmod.isaacmod.client.item;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lq2007.mcmod.isaacmod.Isaac;
import lq2007.mcmod.isaacmod.common.prop.Prop;
import lq2007.mcmod.isaacmod.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacmod.common.prop.type.EmptyProp;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public enum PropLoader implements IModelLoader<PropModel> {

    INSTANCE;

    public static final ResourceLocation ID = new ResourceLocation(Isaac.ID, "prop");

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) { }

    @Override
    public PropModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        if (modelContents.has("type")) {
            JsonElement typeElem = modelContents.get("type");
            if (typeElem.isJsonPrimitive()) {
                JsonPrimitive elem = typeElem.getAsJsonPrimitive();
                if (elem.isNumber()) {
                    return PropModel.getOrCreate(elem.getAsInt());
                } else if (elem.isString()) {
                    return PropModel.getOrCreate(elem.getAsString());
                }
            }
        }
        return PropModel.getOrCreate(EmptyProp.EMPTY);
    }

    @SubscribeEvent
    public static void register(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(ID, INSTANCE);
    }
}
