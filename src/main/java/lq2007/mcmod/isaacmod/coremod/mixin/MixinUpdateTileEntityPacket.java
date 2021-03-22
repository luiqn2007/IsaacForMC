package lq2007.mcmod.isaacmod.coremod.mixin;

import lq2007.mcmod.isaacmod.coremod.IUpdateTileEntityPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

/**
 * Expose the {@link net.minecraft.network.PacketBuffer}.
 */
@Mixin(SUpdateTileEntityPacket.class)
public abstract class MixinUpdateTileEntityPacket implements IUpdateTileEntityPacket {

    private PacketBuffer buffer = null;

    @Inject(method = "readPacketData", at = @At("TAIL"))
    private void injectReadPacketData(CallbackInfo ci, PacketBuffer buf) throws IOException {
        buffer = buf;
    }

    @Inject(method = "writePacketData", at = @At("TAIL"))
    public void injectWritePacketData(CallbackInfo ci, PacketBuffer buf) throws IOException {
        buffer = buf;
    }

    @Override
    public PacketBuffer getBuffer() {
        return buffer;
    }
}
