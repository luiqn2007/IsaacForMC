package lq2007.mcmod.isaacmod.common.prop.type;

import lq2007.mcmod.isaacmod.common.item.PropItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;

public class PropItemRegister {

    private final static Map<ResourceLocation, PropItem> ITEMS_BY_TYPE = new HashMap<>();
    private final static Map<ResourceLocation, RegistryObject<PropItem>> ITEMS_BY_NAME = new HashMap<>();

    private PropRegister props;
    private DeferredRegister<Item> register;

    public PropItemRegister(PropRegister props, DeferredRegister<Item> register) {
        this.props = props;
        this.register = register;
    }

    public void apply() {
        PropRegister.PROPS.forEach((id, type) -> {
            PropItem item = new PropItem(type);
            RegistryObject<PropItem> object = this.register.register(id.getPath(), () -> item);
            ITEMS_BY_TYPE.put(id, item);
            ITEMS_BY_NAME.put(object.getId(), object);
        });
    }

    public void onModel(ModelRegistryEvent event) {
        Minecraft.getInstance().getItemRenderer().renderItem();
    }
}
