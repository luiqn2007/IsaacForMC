package lq2007.mcmod.isaacmod.common.block;

import lq2007.mcmod.isaacmod.Isaac;
import lq2007.mcmod.isaacmod.common.prop.Prop;
import lq2007.mcmod.isaacmod.common.util.BlockUtil;
import lq2007.mcmod.isaacmod.common.util.serializer.Serializers;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class TileFoundation extends TileEntity {

    private Prop prop = new Prop();

    public TileFoundation() {
        super(Isaac.TILES.get(TileFoundation.class));
    }

    public Prop getProp() {
        return prop;
    }

    public boolean hasProp() {
        return !prop.isEmpty;
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
        CompoundNBT nbt = new CompoundNBT();
        Serializers.getNBTWriter(Prop.class).write(nbt, "prop", prop);
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
        if (!prop.isEmpty) {
            Serializers.getNBTWriter(Prop.class).write(compound, "prop", prop);
        }
        return nbt;
    }

    @OnlyIn(Dist.CLIENT)
    public void renderPropOnFoundation(float partialTicks,
                                       com.mojang.blaze3d.matrix.MatrixStack matrixStackIn,
                                       net.minecraft.client.renderer.IRenderTypeBuffer bufferIn,
                                       int combinedLightIn, int combinedOverlayIn) {
        if (!prop.isEmpty) {
            prop.renderOnFoundation(partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
    }
}
