package lq2007.mcmod.isaacmod.block;

import lq2007.mcmod.isaacmod.Isaac;
import lq2007.mcmod.isaacmod.util.StringHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class RegisterBlock {

    public final DeferredRegister<Item> itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, Isaac.ID);
    public final DeferredRegister<Block> blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, Isaac.ID);
    public final Map<String, BlockEntry<?>> entries = new HashMap<>();

    public void register(IEventBus bus) {
        blockRegister.register(bus);
        itemRegister.register(bus);
    }

    public <T extends Block> BlockEntry<T> register(String name) {
        return new BlockEntry<>(name);
    }

    public class BlockEntry<T extends Block> implements Supplier<T> {
        String name, uName;
        RegistryObject<T> object;
        RegistryObject<BlockItem> itemObject;
        T block;
        BlockItem item;
        String en, zh;

        boolean isBlockCreated, isItemCreated, hasItem;

        public BlockEntry(String name) {
            this.name = name.toLowerCase(Locale.ROOT);
            this.uName = StringHelper.toUCamelCase(name);
            this.en = uName;
            this.zh = uName;
        }

        public BlockEntry<T> block(Supplier<T> sup) {
            object = blockRegister.register(name, () -> {
                isBlockCreated = true;
                return block = sup.get();
            });
            return this;
        }

        public BlockEntry<T> block(AbstractBlock.Properties properties, Function<AbstractBlock.Properties, T> sup) {
            object = blockRegister.register(name, () -> {
                isBlockCreated = true;
                return block = sup.apply(properties);
            });
            return this;
        }

        public BlockEntry<T> item(ItemGroup group) {
            hasItem = true;
            itemRegister.register(name, () -> {
                isItemCreated = true;
                return item = new BlockItem(block, new Item.Properties().group(group));
            });
            return this;
        }

        public BlockEntry<T> item(ItemGroup group, String itemName) {
            hasItem = true;
            itemRegister.register(itemName, () -> {
                isItemCreated = true;
                return item = new BlockNamedItem(block, new Item.Properties().group(group));
            });
            return this;
        }

        public BlockEntry<T> item(Item.Properties properties) {
            hasItem = true;
            itemRegister.register(name, () -> {
                isItemCreated = true;
                return item = new BlockItem(block, properties);
            });
            return this;
        }

        public BlockEntry<T> item(Item.Properties properties, String itemName) {
            hasItem = true;
            itemRegister.register(itemName, () -> {
                isItemCreated = true;
                return item = new BlockNamedItem(block, properties);
            });
            return this;
        }

        public BlockEntry<T> item(Supplier<BlockItem> factory) {
            hasItem = true;
            itemRegister.register(name, () -> {
                isItemCreated = true;
                return item = factory.get();
            });
            return this;
        }

        public BlockEntry<T> item(Function<Block, BlockItem> factory) {
            hasItem = true;
            itemRegister.register(name, () -> {
                isItemCreated = true;
                return item = factory.apply(block);
            });
            return this;
        }

        public <P> BlockEntry<T> item(P property, Function<P, BlockItem> factory) {
            hasItem = true;
            itemRegister.register(name, () -> {
                isItemCreated = true;
                return item = factory.apply(property);
            });
            return this;
        }

        public <P> BlockEntry<T> item(P property, BiFunction<P, Block, BlockItem> factory) {
            hasItem = true;
            itemRegister.register(name, () -> {
                isItemCreated = true;
                return item = factory.apply(property, block);
            });
            return this;
        }

        public BlockEntry<T> lang(String en, String zh) {
            this.en = en;
            this.zh = zh;
            return this;
        }

        public RegistryObject<T> getObject() {
            return Objects.requireNonNull(object, "No block in block entry " + name);
        }

        public RegistryObject<BlockItem> getItemObject() {
            return Objects.requireNonNull(itemObject, "No item in block entry " + name);
        }

        public BlockItem getItem() {
            Objects.requireNonNull(itemObject, "No item in block entry " + name);
            return Objects.requireNonNull(item, "Item is not created in block entry " + name);
        }

        public T getBlock() {
            Objects.requireNonNull(object, "No block in block entry " + name);
            return Objects.requireNonNull(block, "Block is not created in block entry " + name);
        }

        public String getLanguageEn() {
            return en;
        }

        public String getLanguageZh() {
            return zh;
        }

        public boolean isItemCreated() {
            return isItemCreated;
        }

        public boolean isBlockCreated() {
            return isBlockCreated;
        }

        public boolean hasItem() {
            return hasItem;
        }

        @Override
        public T get() {
            return getBlock();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BlockEntry<?> that = (BlockEntry<?>) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
}
