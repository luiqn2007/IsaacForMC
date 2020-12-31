package lq2007.mcmod.isaacformc.common.block;

import lq2007.mcmod.isaacformc.Isaac;
import lq2007.mcmod.isaacformc.common.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.common.network.PacketFoundation;
import lq2007.mcmod.isaacformc.register.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkDirection;

import javax.annotation.Nullable;

public class TileFoundation extends TileEntity {

    private PropItem prop = PropItem.EMPTY;

    public TileFoundation() {
        super(Blocks.TYPE_FOUNDATION);
    }

    public PropItem getProp() {
        return prop;
    }

    public void setProp(PropItem prop) {
        this.prop = prop;
        markDirty();
        if (world != null && !world.isRemote) {
            PacketFoundation packet = new PacketFoundation(prop, pos);
            for (PlayerEntity player : world.getPlayers()) {
                ServerPlayerEntity sp = (ServerPlayerEntity) player;
                Isaac.MOD.network.sendTo(packet, sp.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        if (prop.isEmpty()) {
            return new SUpdateTileEntityPacket(pos, 0, prop.serializeNBT());
        } else {
            return new SUpdateTileEntityPacket(pos, 0, new CompoundNBT());
        }
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return super.getUpdateTag();
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {

    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        if (pkt.getNbtCompound().isEmpty()) {
            prop = PropItem.EMPTY;
        } else {
            prop = new PropItem(pkt.getNbtCompound());
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        prop = nbt.contains("prop", Constants.NBT.TAG_COMPOUND)
                ? new PropItem(nbt.getCompound("prop"))
                : PropItem.EMPTY;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT nbt = super.write(compound);
        if (!prop.isEmpty()) {
            nbt.put("prop", prop.serializeNBT());
        }
        return nbt;
    }
}
