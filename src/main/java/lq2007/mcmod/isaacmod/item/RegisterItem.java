package lq2007.mcmod.isaacmod.item;

import lq2007.mcmod.isaacmod.Isaac;
import lq2007.mcmod.isaacmod.util.StringHelper;
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
import java.util.function.Function;
import java.util.function.Supplier;

public class RegisterItem {

    public final DeferredRegister<Item> register = DeferredRegister.create(ForgeRegistries.ITEMS, Isaac.ID);
    public final Map<String, ItemEntry<?>> entries = new HashMap<>();

    public void register(IEventBus bus) {
        register.register(bus);
    }

    public <T extends Item> ItemEntry<T> register(String name) {
        ItemEntry<T> entry = new ItemEntry<>(name);
        entries.put(name, entry);
        return entry;
    }

    public <T extends Item> T get(String name) {
        return (T) entries.get(name).item;
    }

    public <T extends Item> RegistryObject<T> getObject(String name) {
        return (RegistryObject<T>) entries.get(name).object;
    }

    public class ItemEntry<T extends Item> implements Supplier<T> {
        String name, uName;
        RegistryObject<T> object;
        T item;
        String en, zh;

        boolean isItemCreated;

        public ItemEntry(String name) {
            this.name = name.toLowerCase(Locale.ROOT);
            this.uName = StringHelper.toUCamelCase(name);
            this.en = uName;
            this.zh = uName;
        }

        public ItemEntry<T> item(Supplier<T> sup) {
            object = register.register(name, () -> {
                isItemCreated = true;
                return item = sup.get();
            });
            return this;
        }

        public ItemEntry<T> item(Item.Properties properties, Function<Item.Properties, T> sup) {
            object = register.register(name, () -> {
                isItemCreated = true;
                return item = sup.apply(properties);
            });
            return this;
        }

        public ItemEntry<T> item(ItemGroup group, Function<ItemGroup, T> sup) {
            object = register.register(name, () -> {
                isItemCreated = true;
                return item = sup.apply(group);
            });
            return this;
        }

        public ItemEntry<T> lang(String en, String zh) {
            this.en = en;
            this.zh = zh;
            return this;
        }

        public RegistryObject<T> getObject() {
            return Objects.requireNonNull(object, "No item in item entry " + name);
        }

        public T getItem() {
            Objects.requireNonNull(object, "No item in item entry " + name);
            return Objects.requireNonNull(item, "Item is not created in item entry " + name);
        }

        @Override
        public T get() {
            return getItem();
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemEntry<?> that = (ItemEntry<?>) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
}
