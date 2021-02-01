package lq2007.mcmod.isaacformc.common.event;

import lq2007.mcmod.isaacformc.common.util.EntityUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeWorldServer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WorldEventHandler {

    @SubscribeEvent
    public static void onWorldEnter(WorldEvent.Unload event) {
        IWorld world = event.getWorld();
        if (world == null || world instanceof IForgeWorldServer || world instanceof ClientWorld) {
            return;
        }
        if (world instanceof World) {
            EntityUtil.WORLD_ENTITY_MAP.remove(world);
        }
    }
}
