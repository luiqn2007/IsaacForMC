package lq2007.mcmod.isaacmod.register.registers;

import lq2007.mcmod.isaacmod.register.Register;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import static lq2007.mcmod.isaacmod.Isaac.LOGGER;

public class BlockRegister extends BaseDeferredRegister<Block, Block> {

    private BlockItemRegister itemRegister = null;
    private IRegister register = null;

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

    @Override
    protected RegistryObject<Block> register(Class<? extends Block> aClass, String name, Supplier<? extends Block> build) {
        RegistryObject<Block> object = super.register(aClass, name, build);
        if (itemRegister != null) {
            RegistryObject<Item> item = itemRegister.apply(object);
            LOGGER.warn("\tRegistryItem {} for {}", item.getId(), object.getId());
        }
        return object;
    }

    public BlockItemRegister withItem(DeferredRegister<Item> register, BiFunction<Block, String, BlockItem> asItem) {
        itemRegister = new BlockItemRegister(register, asItem);
        return itemRegister;
    }
}
