package lq2007.mcmod.isaacmod.block;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

public class ModTileEntities {

    public static final RegisterTileEntity REGISTER = new RegisterTileEntity();

    public static RegistryObject<TileEntityType<TileFoundation>> FOUNDATION = REGISTER.<TileFoundation>register(ModBlocks.FOUNDATION)
            .te(TileFoundation::new)
            .ter(() -> TerFoundation::new)
            .getObject();

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
    }
}
