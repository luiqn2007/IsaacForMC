package lq2007.mcmod.isaacformc.register;

import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Entities {

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) { }
}
