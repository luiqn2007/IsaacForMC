package lq2007.mcmod.isaacformc.register.registers;

import lq2007.mcmod.isaacformc.register.ICustomItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import org.objectweb.asm.Type;

import java.util.function.Function;

public class BlockItemRegister implements IRegister {

    private final DeferredRegister<Item> register;
    private final Iterable<RegistryObject<Block>> blocks;
    private final Function<Block, Item.Properties> properties;

    public BlockItemRegister(DeferredRegister<Item> register, Iterable<RegistryObject<Block>> blocks, Function<Block, Item.Properties> properties) {
        this.register = register;
        this.blocks = blocks;
        this.properties = properties;
    }

    @Override
    public void cache(ClassLoader classLoader, Type clazz, String className, String packageName, Class<?> aClass) { }

    @Override
    public void apply() {
        for (RegistryObject<Block> blockObj : blocks) {
            Block block = blockObj.get();
            String name = blockObj.getId().getPath();
            register.register(name, () -> {
                if (block instanceof ICustomItem) {
                    return ((ICustomItem) block).newBlockItem();
                } else {
                    return new BlockItem(block, properties.apply(block));
                }
            });
        }
    }
}
