package lq2007.mcmod.isaacformc.common.network;

import lq2007.mcmod.isaacformc.common.capability.IIsaacProperty;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketEntityProperty {

    private PacketBuffer buffer;
    private final IIsaacProperty data;

    public PacketEntityProperty(PacketBuffer buffer) {
        this.buffer = buffer;
        this.data = null;
    }

    public PacketEntityProperty(IIsaacProperty data) {
        this.buffer = null;
        this.data = data;
    }

    public static void encode(PacketEntityProperty packet, PacketBuffer buffer) {
        packet.data.write(buffer);
        packet.buffer = buffer;
    }

    public static void apply(PacketEntityProperty packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        if (player != null) {
            IIsaacProperty property = IsaacCapabilities.getProperty(player);
            property.read(packet.buffer);
        }
    }
}
