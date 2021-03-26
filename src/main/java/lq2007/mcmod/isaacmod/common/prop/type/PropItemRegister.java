package lq2007.mcmod.isaacmod.common.prop.type;

import lq2007.mcmod.isaacmod.common.item.PropItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;

public class PropItemRegister {

    private final Map<ResourceLocation, PropItem> itemsByType = new HashMap<>();
    private final Map<ResourceLocation, RegistryObject<PropItem>> itemsByName = new HashMap<>();

    private DeferredRegister<Item> register;

    public PropItemRegister(DeferredRegister<Item> register) {
        this.register = register;
    }

    public void apply() {
        PropRegister.PROPS.forEach((id, type) -> {
            PropItem item = new PropItem(type);
            RegistryObject<PropItem> object = this.register.register(id.getPath(), () -> item);
            itemsByType.put(id, item);
            itemsByName.put(object.getId(), object);
        });
    }

    public RegistryObject<PropItem> getItem(ResourceLocation name) {
        return itemsByName.get(name);
    }

    public PropItem getItem(AbstractPropType type) {
        return itemsByType.get(type.key);
    }

    public PropItem getItem(AbstractPropType type, PropItem def) {
        return itemsByType.getOrDefault(type.key, def);
    }
}
