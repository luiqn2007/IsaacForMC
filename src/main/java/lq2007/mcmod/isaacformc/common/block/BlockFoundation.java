package lq2007.mcmod.isaacformc.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockFoundation extends Block {

    private static final Material MATERIAL = new Material(MaterialColor.BROWN, false, true, false, false, false, false, PushReaction.DESTROY);

    public BlockFoundation() {
        super(Properties.create(MATERIAL));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileFoundation();
    }
}
