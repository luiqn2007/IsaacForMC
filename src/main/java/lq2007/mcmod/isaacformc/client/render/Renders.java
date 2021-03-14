package lq2007.mcmod.isaacformc.client.render;

import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.common.entity.friend.EntityBobby;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class Renders {

    public static final IRenderFactory<EntityBobby> FACTORY_BOBBY = RenderBobby::new;

    public static void register() {
        RenderingRegistry.registerEntityRenderingHandler(Isaac.ENTITIES.get(EntityBobby.class), FACTORY_BOBBY);
    }
}
