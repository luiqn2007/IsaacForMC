package lq2007.mcmod.isaacformc.common.util.serializer;

import lq2007.mcmod.isaacformc.common.util.serializer.packet.INBTSerializer;
import lq2007.mcmod.isaacformc.common.util.serializer.buffer.IPacketSerializer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class BlockPosSerializer implements IPacketSerializer<BlockPos>, INBTSerializer<BlockPos> {

    public static final BlockPosSerializer INSTANCE = new BlockPosSerializer();

    @Override
    public BlockPos read(PacketBuffer buffer) {
        return buffer.readBlockPos();
    }

    @Override
    public PacketBuffer write(BlockPos item, PacketBuffer buffer) {
        return buffer.writeBlockPos(item);
    }

    @Override
    public BlockPos read(CompoundNBT nbt, String key) {
        return NBTUtil.readBlockPos(nbt.getCompound(key));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, BlockPos item) {
        nbt.put(key, NBTUtil.writeBlockPos(item));
        return nbt;
    }
}
