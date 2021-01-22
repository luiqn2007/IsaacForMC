package lq2007.mcmod.isaacformc.common.network;

import lq2007.mcmod.isaacformc.common.block.TileFoundation;
import lq2007.mcmod.isaacformc.common.capability.IIsaacPropData;
import lq2007.mcmod.isaacformc.common.capability.IIsaacProperty;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
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
        channel.registerMessage(id++, PacketEntityProperty.class, PacketEntityProperty::encode, PacketEntityProperty::new, PacketEntityProperty::apply, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        System.out.println("Register Network Finished. Count: " + id);
    }

    public static void notifyPropDataChanged(IIsaacPropData data, LivingEntity entity) {
        if (data.isDirty() && entity.isServerWorld() && entity instanceof ServerPlayerEntity) {
            sendToClient((ServerPlayerEntity) entity, new PacketEntityProp(data));
        }
    }

    public static void notifyPropertyChanged(IIsaacProperty data, LivingEntity entity) {
        if (data.isDirty() && entity.isServerWorld() && entity instanceof ServerPlayerEntity) {
            sendToClient((ServerPlayerEntity) entity, new PacketEntityProperty(data));
        }
    }

    public static void notifyFoundationChanged(World world, TileFoundation foundation) {
        PacketFoundation packet = new PacketFoundation(foundation.getProp(), foundation.getPos());
        for (PlayerEntity player : world.getPlayers()) {
            ServerPlayerEntity sp = (ServerPlayerEntity) player;
            NET.sendTo(packet, sp.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    private static void sendToClient(ServerPlayerEntity entity, Object packet) {
        NET.sendTo(packet, entity.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }
}
