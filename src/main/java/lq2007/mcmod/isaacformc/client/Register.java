package lq2007.mcmod.isaacformc.client;

import lq2007.mcmod.isaacformc.client.ter.TerFoundation;
import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.common.block.TileFoundation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

// todo bus choose
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class Register {

    @SubscribeEvent
    public static void register(ModelRegistryEvent event) {
        ClientRegistry.bindTileEntityRenderer(Isaac.TILES.get(TileFoundation.class), TerFoundation::new);
    }
}

