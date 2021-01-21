package lq2007.mcmod.isaacformc.common.network;

import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketEntitySizeChange {

        private final int size;

        public PacketEntitySizeChange(LivingEntity entity) {
            this.size = IsaacCapabilities.getProperty(entity).bodySize();
        }

        public PacketEntitySizeChange(PacketBuffer buffer) {
            this.size = buffer.readVarInt();
        }

        public static void encode(PacketEntitySizeChange packet, PacketBuffer buffer) {
            buffer.writeVarInt(packet.size);
        }

        public static void apply(PacketEntitySizeChange packet, Supplier<NetworkEvent.Context> context) {
            ServerPlayerEntity player = context.get().getSender();
            if (player != null) {
                IsaacCapabilities.getProperty(player).bodySize(packet.size);
            }
        }
    }
