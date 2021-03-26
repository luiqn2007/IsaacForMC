package lq2007.mcmod.isaacmod.client.ister;

import lq2007.mcmod.isaacmod.Isaac;
import lq2007.mcmod.isaacmod.common.item.PropItem;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Register {

    @SubscribeEvent
    public static void onModelBaked(ModelBakeEvent event) {
        RegistryObject<Item> propObj = Isaac.ITEMS.getObj(PropItem.class);
        ModelResourceLocation location = new ModelResourceLocation(propObj.getId(), "inventory");
        IBakedModel existedModel = event.getModelRegistry().get(location);

        if (existedModel == null) {
            throw new RuntimeException("can't found prop model");
        } else if (existedModel instanceof ISTERItemModel) {
            throw new RuntimeException("try to set prop model twice");
        } else {
            event.getModelRegistry().put(location, new ISTERItemModel(existedModel, true));
        }
    }
}
