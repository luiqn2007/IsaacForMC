package lq2007.mcmod.isaacformc.register;

import lq2007.mcmod.isaacformc.common.block.BlockFoundation;
import lq2007.mcmod.isaacformc.common.block.TileFoundation;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Blocks {

    public static final BlockFoundation FOUNDATION = new BlockFoundation();

    public static final TileEntityType<TileFoundation> TYPE_FOUNDATION = TileEntityType.Builder
            .create(TileFoundation::new, Blocks.FOUNDATION)
            .build(null);

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(FOUNDATION);
    }

    @SubscribeEvent
    public static void registerTile(RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(TYPE_FOUNDATION);
    }
}
