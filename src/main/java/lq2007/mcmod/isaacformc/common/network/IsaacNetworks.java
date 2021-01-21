package lq2007.mcmod.isaacformc.common.network;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class IsaacNetworks {

    private static SimpleChannel NET = null;

    public static void registerAll(SimpleChannel channel) {
        NET = channel;
        int id = 0;
        channel.registerMessage(id++, PacketFoundation.class, PacketFoundation::encode, PacketFoundation::new, PacketFoundation::apply, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(id++, PacketEntityProp.class, PacketEntityProp::encode, PacketEntityProp::new, PacketEntityProp::apply, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(id++, PacketEntitySizeChange.class, PacketEntitySizeChange::encode, PacketEntitySizeChange::new, PacketEntitySizeChange::apply, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        System.out.println("Register Network Finished. Count: " + id);
    }

    public static void notifyEntitySizeChanged(LivingEntity entity) {
        if (entity.isServerWorld() && entity instanceof ServerPlayerEntity) {
            sendToClient((ServerPlayerEntity) entity, new PacketEntitySizeChange(entity));
        }
    }

    private static void sendToClient(ServerPlayerEntity entity, Object packet) {
        NET.sendTo(packet, entity.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }
}
