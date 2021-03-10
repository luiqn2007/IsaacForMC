package lq2007.mcmod.isaacformc.common.network;

import lq2007.mcmod.isaacformc.common.network.PacketEntityProp;
import lq2007.mcmod.isaacformc.common.network.PacketEntityProperty;
import lq2007.mcmod.isaacformc.common.network.PacketFoundation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class IsaacNetworks {

    public static SimpleChannel CHANNEL = null;

    public static void registerAll(SimpleChannel channel) {
        CHANNEL = channel;
        int id = 0;
        channel.registerMessage(id++, PacketFoundation.class, PacketFoundation::encode, PacketFoundation::new, PacketFoundation::apply, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(id++, PacketEntityProp.class, PacketEntityProp::encode, PacketEntityProp::new, PacketEntityProp::apply, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(id++, PacketEntityProperty.class, PacketEntityProperty::encode, PacketEntityProperty::new, PacketEntityProperty::apply, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        System.out.println("Register Network Finished. Count: " + id);
    }
}
