package lq2007.mcmod.isaacmod.entity;

import lq2007.mcmod.isaacmod.Isaac;
import lq2007.mcmod.isaacmod.item.ItemSpawnEgg;
import lq2007.mcmod.isaacmod.util.StringHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class RegisterEntity {

    public static final Map<EntityType<?>, SpawnEggItem> EGGS;

    static {
        Field field = ObfuscationReflectionHelper.findField(SpawnEggItem.class, "field_195987_b");
        field.setAccessible(true);
        try {
            EGGS = (Map<EntityType<?>, SpawnEggItem>) field.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final DeferredRegister<Item> eggRegister = DeferredRegister.create(ForgeRegistries.ITEMS, Isaac.ID);
    public final DeferredRegister<EntityType<?>> entityRegister = DeferredRegister.create(ForgeRegistries.ENTITIES, Isaac.ID);
    public final Map<String, RegistryEntry<?>> entries = new HashMap<>();

    public void register(IEventBus bus) {
        eggRegister.register(bus);
        entityRegister.register(bus);
    }

    public <E extends Entity> RegistryEntry<E> register(String name) {
        return new RegistryEntry<>(name);
    }

    @OnlyIn(Dist.CLIENT)
    public void registerRenderers() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> entries.values().stream()
                .filter(RegistryEntry::hasRenderer)
                .forEach(entry -> net.minecraftforge.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler(entry.getType(), m -> entry.rendererFactory.get().apply(m))));
    }

    public class RegistryEntry<E extends Entity> {

        String name, uName;
        String eggName;
        SpawnEggItem egg;
        RegistryObject<SpawnEggItem> eggObject;
        EntityType<E> type;
        RegistryObject<EntityType<E>> typeObject;
        Supplier<Function<net.minecraft.client.renderer.entity.EntityRendererManager, net.minecraft.client.renderer.entity.EntityRenderer>> rendererFactory;
        boolean hasEgg, hasRenderer;

        public String en, zh;

        public RegistryEntry(String name) {
            this.name = name.toLowerCase(Locale.ROOT);
            this.uName = StringHelper.toUCamelCase(name);
            this.en = uName;
            this.zh = uName;
            this.eggName = "egg_" + name;
        }

        public RegistryEntry<E> entity(Supplier<EntityType.Builder<E>> sup) {
            typeObject = entityRegister.register(name, () -> {
                this.type = sup.get().build(name);
                if (hasEgg) {
                    EGGS.put(this.type, getEgg());
                }
                return this.type;
            });
            return this;
        }

        public RegistryEntry<E> lang(String en, String zh) {
            this.en = en;
            this.zh = zh;
            return this;
        }

        public RegistryEntry<E> egg(int primaryColor, int secondaryColor) {
            this.hasEgg = true;
            this.eggObject = eggRegister.register(eggName, () -> this.egg = new ItemSpawnEgg(this, primaryColor, secondaryColor));
            return this;
        }

        public RegistryEntry<E> egg(String eggName, int primaryColor, int secondaryColor) {
            this.eggName = eggName;
            this.hasEgg = true;
            return egg(primaryColor, secondaryColor);
        }

        public RegistryEntry<E> egg(Supplier<SpawnEggItem> sup) {
            this.eggObject = eggRegister.register(eggName, () -> this.egg = sup.get());
            this.hasEgg = true;
            return this;
        }

        public RegistryEntry<E> egg(String eggName, Supplier<SpawnEggItem> sup) {
            this.eggName = eggName;
            this.hasEgg = true;
            return egg(sup);
        }

        public RegistryEntry<E> renderer(Supplier<Function<net.minecraft.client.renderer.entity.EntityRendererManager, net.minecraft.client.renderer.entity.EntityRenderer>> rendererFactory) {
            this.rendererFactory = rendererFactory;
            this.hasRenderer = true;
            return this;
        }

        public RegistryObject<EntityType<E>> getTypeObj() {
            return Objects.requireNonNull(typeObject, "No entity type in entity entry " + name);
        }

        public EntityType<E> getType() {
            Objects.requireNonNull(typeObject, "No entity type in entity entry " + name);
            return Objects.requireNonNull(type, "EntityType is not created in entity entry " + name);
        }

        public RegistryObject<SpawnEggItem> getEggObj() {
            if (hasEgg) {
                return Objects.requireNonNull(eggObject, "No egg in entity entry " + name);
            }
            throw new IllegalStateException("Entity " + name + " not has any egg");
        }

        public SpawnEggItem getEgg() {
            if (hasEgg) {
                Objects.requireNonNull(eggObject, "No egg in entity entry " + name);
                return Objects.requireNonNull(egg, "Egg is not created in entity entry " + name);
            }
            throw new IllegalStateException("Entity " + name + " not has any egg");
        }

        public String getEggName() {
            return eggName;
        }

        public String getLanguageZh() {
            return zh;
        }

        public String getLanguageEn() {
            return en;
        }

        public boolean hasEgg() {
            return hasEgg;
        }

        public boolean hasRenderer() {
            return hasRenderer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RegistryEntry<?> entry = (RegistryEntry<?>) o;
            return Objects.equals(name, entry.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
