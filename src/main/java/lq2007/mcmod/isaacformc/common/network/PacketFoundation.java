package lq2007.mcmod.isaacformc.common.network;

import lq2007.mcmod.isaacformc.common.block.TileFoundation;
import lq2007.mcmod.isaacformc.common.prop.Prop;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketFoundation {

    public final Prop prop;
    public final BlockPos pos;

    public PacketFoundation(Prop prop, BlockPos pos) {
        this.prop = prop;
        this.pos = pos;
    }

    public PacketFoundation(PacketBuffer buffer) {
        this.pos = buffer.readBlockPos();
        this.prop = Prop.fromPacket(buffer);
    }

    public static void encode(PacketFoundation packet, PacketBuffer buffer) {
        buffer.writeBlockPos(packet.pos);
        packet.prop.write(buffer);
    }

    public static void apply(PacketFoundation packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        if (player != null) {
            TileEntity te = player.world.getTileEntity(packet.pos);
            if (te instanceof TileFoundation) {
                ((TileFoundation) te).setProp(packet.prop);
            }
        }
    }
}
