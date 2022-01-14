package lq2007.mcmod.isaacmod.handler;

import lq2007.mcmod.isaacmod.util.EntityUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeWorldServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber
public class EntityEventHandler {

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        World world = event.getWorld();
        if (entity == null || world == null || world instanceof IForgeWorldServer || world instanceof ClientWorld) {
            return;
        }
        Map<UUID, Entity> map = EntityUtil.WORLD_ENTITY_MAP.computeIfAbsent(world, w -> new HashMap<>());
        map.put(entity.getUniqueID(), entity);
    }

    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveWorldEvent event) {
        Entity entity = event.getEntity();
        World world = event.getWorld();
        if (entity == null || world == null || world instanceof IForgeWorldServer || world instanceof ClientWorld) {
            return;
        }
        Map<UUID, Entity> map = EntityUtil.WORLD_ENTITY_MAP.get(world);
        if (map != null) {
            map.remove(entity.getUniqueID());
        }
    }
}
