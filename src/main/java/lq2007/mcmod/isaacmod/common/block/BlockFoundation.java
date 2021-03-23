package lq2007.mcmod.isaacmod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFoundation extends Block {

    private static final Material MATERIAL = new Material(MaterialColor.BROWN, false, true, false, false, false, false, PushReaction.DESTROY);
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
    public void onLanded(IBlockReader worldIn, Entity entityIn) {
        super.onLanded(worldIn, entityIn);
        // todo add prop to player
        System.out.println("Land");
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        // todo add prop to foundation from nbt
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (SHAPE == null) {
            VoxelShape v0 = createVoxelShape(0.05, 0.11851, 0.2451, 0.41049, 0, 0.072952, 0.137966, 0.258096, 0.415055, 0.075958);
            VoxelShape v1 = createVoxelShape(0.072952, 0.137966, 0.258096, 0.415055, 0.075958, 0.12884, 0.185346, 0.289755, 0.426172, 0.134667);
            VoxelShape v2 = createVoxelShape(0.12884, 0.185346, 0.289755, 0.426172, 0.134667, 0.195706, 0.242032, 0.327631, 0.439472, 0.187524);
            VoxelShape v3 = createVoxelShape(0.195706, 0.242032, 0.327631, 0.439472, 0.187524, 0.279287, 0.312889, 0.374976, 0.456097, 0.231994);
            VoxelShape v4 = createVoxelShape(0.279287, 0.312889, 0.374976, 0.456097, 0.231994, 0.360689, 0.381898, 0.421087, 0.472289, 0.263455);
            VoxelShape v5 = createVoxelShape(0.360689, 0.381898, 0.421087, 0.472289, 0.263455, 0.412337, 0.425683, 0.450343, 0.482563, 0.280739);
            VoxelShape v6 = createVoxelShape(0.412337, 0.425683, 0.450343, 0.482563, 0.280739, 0.453596, 0.46066, 0.473714, 0.49077, 0.292407);
            VoxelShape v7 = createVoxelShape(0.453596, 0.46066, 0.473714, 0.49077, 0.292407, 0.5, 0.5, 0.5, 0.5, 0.29786);
            SHAPE = VoxelShapes.or(v0, v1, v2, v3, v4, v5, v6, v7).simplify();
        }
        return SHAPE;
    }

    private VoxelShape createVoxelShape(double b0, double b1, double b2, double b3, double bh,
                                        double t0, double t1, double t2, double t3, double th) {
        double d = Math.max(th - bh, t0 - b0);
        int count = MathHelper.ceil(d / 0.01);
        VoxelShape result = VoxelShapes.empty();
        double dh = (th - bh) / count;
        double d0 = (t0 - b0) / count;
        double d1 = (t1 - b1) / count;
        double d2 = (t2 - b2) / count;
        double d3 = (t3 - b3) / count;
        double ch = bh;
        double c0 = b0;
        double c1 = b1;
        double c2 = b2;
        double c3 = b3;
        for (int i = 0; i < count; i++) {
            result = VoxelShapes.or(result, createVoxelShape(c0, c1, c2, c3, ch, ch = ch + dh));
            c0 += d0;
            c1 += d1;
            c2 += d2;
            c3 += d3;
        }
        return result;
    }

    private VoxelShape createVoxelShape(double b0, double b1, double b2, double b3, double y0, double y1) {
        double b4 = 1 - b3;
        double b5 = 1 - b2;
        double b6 = 1 - b1;
        double b7 = 1 - b0;
        // center
        VoxelShape result = VoxelShapes.create(b3, y0, b0, b4, y1, b7);
        // block0
        double dx = (b1 - b0) / 3;
        double dz = (b3 - b2) / 3;
        double x0 = b0;
        double x1 = b0 + dx;
        double z0 = b3;
        double z1 = b4;
        for (int i = 0; i < 3; i++) {
            result = VoxelShapes.or(result, VoxelShapes.create(x0, y0, z0, x1, y1, z1));
            result = VoxelShapes.or(result, VoxelShapes.create(x0 + b6, y0, z0, x1 + b6, y1, z1));
            x0 = x1;
            x1 += dx;
            z0 -= dz;
            z1 += dz;
        }
        // block1
        dx = dz = (b2 - b1) / 5;
        x0 = b1;
        x1 = b1 + dx;
        z0 = b2;
        z1 = b5;
        for (int i = 0; i < 5; i++) {
            result = VoxelShapes.or(result, VoxelShapes.create(x0, y0, z0, x1, y1, z1));
            result = VoxelShapes.or(result, VoxelShapes.create(x0 + b5, y0, z0, x1 + b5, y1, z1));
            x0 = x1;
            x1 += dx;
            z0 -= dz;
            z1 += dz;
        }
        // block2
        dx = (b3 - b2) / 5;
        dz = (b1 - b0) / 3;
        x0 = b2;
        x1 = b2 + dx;
        z0 = b1;
        z1 = b6;
        for (int i = 0; i < 5; i++) {
            result = VoxelShapes.or(result, VoxelShapes.create(x0, y0, z0, x1, y1, z1));
            result = VoxelShapes.or(result, VoxelShapes.create(x0 + b4, y0, z0, x1 + b4, y1, z1));
            x0 = x1;
            x1 += dx;
            z0 -= dz;
            z1 += dz;
        }
        return result;
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
        TileEntity te = worldIn.getTileEntity(pos);
        return te instanceof TileFoundation && ((TileFoundation) te).hasProp() ? 1 : 0;
    }
}
