package lq2007.mcmod.isaacmod.block;

import lq2007.mcmod.isaacmod.item.ModGroups;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

public class ModBlocks {

    public static final RegisterBlock REGISTER = new RegisterBlock();

    public static RegistryObject<BlockFoundation> FOUNDATION = REGISTER.<BlockFoundation>register("foundation")
            .block(BlockFoundation::new)
            .item(ModGroups.MAIN)
            .lang("Foundation", "基座")
            .getObject();

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
    }
}
