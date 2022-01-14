package lq2007.mcmod.isaacmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFoundation extends Block {

    private static final Material MATERIAL = new Material(MaterialColor.BROWN, false, true, true, true, false, false, PushReaction.DESTROY);
    private static VoxelShape SHAPE = null;

    public BlockFoundation() {
        super(Properties.create(MATERIAL)
                .hardnessAndResistance(-1, 3600000.0F) // BEDROCK
                .noDrops()
                .setOpaque((state, world, pos) -> false)
                .doesNotBlockMovement());
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

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        // todo add prop to foundation from nbt
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (SHAPE == null) {
            double v1a = (0.258096 + 0.137966) / 2, v1b = 1 - v1a;
            double v2a = (0.289755 + 0.185346) / 2, v2b = 1 - v2a;
            double v3a = (0.327631 + 0.242032) / 2, v3b = 1 - v3a;
            double v4a = (0.374976 + 0.312889) / 2, v4b = 1 - v4a;
            double v5a = (0.414806 + 0.372498) / 2, v5b = 1 - v5a;
            double v6a = (0.440853 + 0.411481) / 2, v6b = 1 - v6a;
            double v7a = (0.461798 + 0.442827) / 2, v7b = 1 - v7a;
            double v8a = (0.476440 + 0.464740) / 2, v8b = 1 - v8a;
            double v9a = (0.486527 + 0.479837) / 2, v9b = 1 - v9a;
            VoxelShape v0 = VoxelShapes.create(v1a, 0.000000, v1a, v1b, 0.075958, v1b);
            VoxelShape v1 = VoxelShapes.create(v2a, 0.075958, v2a, v2b, 0.153311, v2b);
            VoxelShape v2 = VoxelShapes.create(v3a, 0.153311, v3a, v3b, 0.221885, v3b);
            VoxelShape v3 = VoxelShapes.create(v4a, 0.221885, v4a, v4b, 0.294648, v4b);
            VoxelShape v4 = VoxelShapes.create(v5a, 0.294648, v5a, v5b, 0.344968, v5b);
            VoxelShape v5 = VoxelShapes.create(v6a, 0.344968, v6a, v6b, 0.372857, v6b);
            VoxelShape v6 = VoxelShapes.create(v7a, 0.372857, v7a, v7b, 0.394969, v7b);
            VoxelShape v7 = VoxelShapes.create(v8a, 0.394969, v8a, v8b, 0.405236, v8b);
            VoxelShape v8 = VoxelShapes.create(v9a, 0.405236, v9a, v9b, 0.414732, v9b); // skip 0.410622
            SHAPE = VoxelShapes.or(v0, v1, v2, v3, v4, v5, v6, v7, v8);
        }
        return SHAPE;
    }

    @Override
    public boolean canProvidePower(BlockState state) {
        return true;
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        TileEntity te = blockAccess.getTileEntity(pos);
        return te instanceof TileFoundation && ((TileFoundation) te).hasProp() ? 1 : 0;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return getWeakPower(blockState, worldIn, pos, Direction.DOWN);
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}
