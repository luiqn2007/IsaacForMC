package lq2007.mcmod.isaacmod.item;

import lq2007.mcmod.isaacmod.prop.ModProps;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {

    public static RegisterItem REGISTER = new RegisterItem();

    public static RegistryObject<Item> ICON = REGISTER.register("isaac_icon")
            .item(new Item.Properties().maxStackSize(1).group(ModGroups.MAIN), Item::new)
            .getObject();

    // source generate
// ===> generated source begin
    public static RegistryObject<Item> BROTHER_BOBBY = REGISTER.register("brother_bobby")
        .item(() -> new ItemProp(ModProps.BROTHER_BOBBY))
        .getObject();
    public static RegistryObject<Item> THE_SAD_ONION = REGISTER.register("the_sad_onion")
        .item(() -> new ItemProp(ModProps.THE_SAD_ONION))
        .getObject();
// ===> generated source end

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
    }
}
