package lq2007.mcmod.isaacformc.common.block;

import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.util.BlockUtil;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class TileFoundation extends TileEntity {

    private Prop prop = Prop.EMPTY;

    public TileFoundation() {
        super(Blocks.TYPE_FOUNDATION.get());
    }

    public Prop getProp() {
        return prop;
    }

    public void setProp(Prop prop) {
        if (world != null && !world.isRemote && this.prop != prop) {
            this.prop = prop;
            BlockUtil.markDirtyAndUpdate(this);
        }
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 0, prop != Prop.EMPTY ? prop.serializeNBT() : new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        prop = Prop.fromNbt(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        prop = nbt.contains("_item", Constants.NBT.TAG_COMPOUND)
                ? Prop.fromNbt(nbt.getCompound("_item"))
                : Prop.EMPTY;
        markDirty();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT nbt = super.write(compound);
        if (prop != Prop.EMPTY) {
            nbt.put("_item", prop.serializeNBT());
        }
        return nbt;
    }

    public void renderPropOnFoundation(float partialTicks,
                                       com.mojang.blaze3d.matrix.MatrixStack matrixStackIn,
                                       net.minecraft.client.renderer.IRenderTypeBuffer bufferIn,
                                       int combinedLightIn, int combinedOverlayIn) {
        if (prop != Prop.EMPTY) {
            prop.renderOnFoundation(partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
    }
}
