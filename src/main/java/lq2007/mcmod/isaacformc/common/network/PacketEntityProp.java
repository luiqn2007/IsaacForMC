package lq2007.mcmod.isaacformc.common.network;

import lq2007.mcmod.isaacformc.common.capability.IIsaacPropData;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketEntityProp {

    private final CompoundNBT data;

    public PacketEntityProp(IIsaacPropData data) {
        this.data = data.createPacketData();
    }

    public PacketEntityProp(PacketBuffer buffer) {
        this.data = buffer.readCompoundTag();
    }

    public static void encode(PacketEntityProp packet, PacketBuffer buffer) {
        buffer.writeCompoundTag(packet.data);
    }

    public static void apply(PacketEntityProp packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        if (player != null) {
            IsaacCapabilities.fromEntity(player).readPacketData(packet.data);
        }
    }
}
