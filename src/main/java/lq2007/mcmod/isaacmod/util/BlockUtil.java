package lq2007.mcmod.isaacmod.util;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class BlockUtil {

    public static void markDirtyAndUpdate(TileEntity te) {
        World world = te.getWorld();
        if (world != null) {
            te.markDirty();
            if (!world.isRemote) {
                BlockState state = te.getBlockState();
                world.notifyBlockUpdate(te.getPos(), state, state, Constants.BlockFlags.BLOCK_UPDATE);
            }
        }
    }

    public static void notifyUpdate(@Nullable World world, BlockPos pos) {
        if (world != null) {
            BlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
        }
    }
}
