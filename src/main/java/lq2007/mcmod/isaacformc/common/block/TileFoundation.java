package lq2007.mcmod.isaacformc.common.block;

import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.util.BlockUtil;
import lq2007.mcmod.isaacformc.common.util.serializer.Serializers;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class TileFoundation extends TileEntity {

    private Prop prop = Prop.EMPTY;

    public TileFoundation() {
        super(Isaac.TILES.get(TileFoundation.class));
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
        CompoundNBT nbt = Serializers.getNBTWriter(Prop.class).write(new CompoundNBT(), "prop", prop);
        return new SUpdateTileEntityPacket(pos, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        prop = Serializers.getNBTReader(Prop.class).read(pkt.getNbtCompound(), "prop");
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        prop = Serializers.getNBTReader(Prop.class).read(nbt, "prop");
        markDirty();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT nbt = super.write(compound);
        if (prop != Prop.EMPTY) {
            Serializers.getNBTWriter(Prop.class).write(compound, "prop", prop);
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
