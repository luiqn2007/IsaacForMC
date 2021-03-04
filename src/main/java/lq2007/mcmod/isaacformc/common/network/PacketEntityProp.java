package lq2007.mcmod.isaacformc.common.network;

import lq2007.mcmod.isaacformc.common.capability.IIsaacPropData;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.prop.PropItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketEntityProp {

    private PacketBuffer buffer;
    private final IIsaacPropData data;

    public PacketEntityProp(PacketBuffer buffer) {
        this.buffer = buffer;
        this.data = null;
    }

    public PacketEntityProp(IIsaacPropData data) {
        this.buffer = null;
        this.data = data;
    }

    public static void encode(PacketEntityProp packet, PacketBuffer buffer) {
        packet.data.write(buffer);
        packet.buffer = buffer;
    }

    public static void apply(PacketEntityProp packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        if (player != null) {
            IIsaacPropData propData = IsaacCapabilities.getPropData(player);
            propData.removeAllProps(true, true);
            propData.read(packet.buffer);
            for (PropItem prop : propData.getAllProps()) {
                prop.prop.onPickup(player, prop);
            }
        }
    }
}
