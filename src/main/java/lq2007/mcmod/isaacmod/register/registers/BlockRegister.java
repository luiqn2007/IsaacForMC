package lq2007.mcmod.isaacmod.register.registers;

import lq2007.mcmod.isaacmod.register.Register;
import net.minecraft.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegister extends BaseDeferredRegister<Block, Block> {

    public BlockRegister(Register context, String packageName) {
        super(ForgeRegistries.BLOCKS, context, packageName);
    }

    public BlockRegister(Register context) {
        super(ForgeRegistries.BLOCKS, context);
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE;
    }
}
