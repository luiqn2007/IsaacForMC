package lq2007.mcmod.isaacformc.register;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Items {

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Item> event) { }
}
