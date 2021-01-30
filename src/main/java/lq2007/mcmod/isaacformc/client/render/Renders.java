package lq2007.mcmod.isaacformc.client.render;

import lq2007.mcmod.isaacformc.common.entity.Entities;
import lq2007.mcmod.isaacformc.common.entity.friend.EntityBobby;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class Renders {

    public static final IRenderFactory<EntityBobby> FACTORY_BOBBY = RenderBobby::new;

    public static void register() {
        RenderingRegistry.registerEntityRenderingHandler(Entities.TYPE_BOBBY.get(), FACTORY_BOBBY);
    }
}
