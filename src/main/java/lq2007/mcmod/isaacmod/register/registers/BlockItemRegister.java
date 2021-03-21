package lq2007.mcmod.isaacmod.register.registers;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.BiFunction;

public class BlockItemRegister {

    private final DeferredRegister<Item> register;
    private final BiFunction<Block, String, BlockItem> asItem;

    public BlockItemRegister(DeferredRegister<Item> register, BiFunction<Block, String, BlockItem> asItem) {
        this.register = register;
        this.asItem = asItem;
    }

    public RegistryObject<Item> apply(RegistryObject<Block> block) {
        String name = block.getId().getPath();
        return register.register(name, () -> asItem.apply(block.get(), name));
    }
}
