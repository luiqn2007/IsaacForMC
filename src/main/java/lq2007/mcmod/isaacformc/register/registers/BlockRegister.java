package lq2007.mcmod.isaacformc.register.registers;

import lq2007.mcmod.isaacformc.register.Register;
import net.minecraft.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegister extends BaseDeferredRegister<Block, Block> {

    public BlockRegister(Register context, String packageName) {
        super(ForgeRegistries.BLOCKS, context, Block.class, packageName);
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE;
    }
}
