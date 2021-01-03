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
        return new SUpdateTileEntityPacket(pos, 0, prop != PropItem.EMPTY ? prop.serializeNBT() : new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        prop = PropItem.fromNbt(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        if (prop != PropItem.EMPTY) {
            nbt.put("_prop", prop.serializeNBT());
        }
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        super.handleUpdateTag(state, tag);
        if (tag.contains("_prop", Constants.NBT.TAG_COMPOUND)) {
            prop = PropItem.fromNbt(tag.getCompound("_prop"));
        } else {
            prop = PropItem.EMPTY;
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        prop = nbt.contains("prop", Constants.NBT.TAG_COMPOUND)
                ? PropItem.fromNbt(nbt.getCompound("prop"))
                : PropItem.EMPTY;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT nbt = super.write(compound);
        if (prop != PropItem.EMPTY) {
            nbt.put("prop", prop.serializeNBT());
        }
        return nbt;
    }
}
