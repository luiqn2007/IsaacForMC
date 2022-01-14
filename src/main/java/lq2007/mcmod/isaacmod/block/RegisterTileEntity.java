package lq2007.mcmod.isaacmod.block;

import lq2007.mcmod.isaacmod.Isaac;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class RegisterTileEntity {

    public DeferredRegister<TileEntityType<?>> register = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Isaac.ID);
    public Map<String, TileEntityEntry<?>> entries = new HashMap<>();

    public <T extends TileEntity> TileEntityEntry<T> register(String name) {
        return new TileEntityEntry<>(name);
    }

    public <T extends TileEntity> TileEntityEntry<T> register(RegistryObject<? extends Block> block) {
        return new TileEntityEntry<T>(block.getId().getPath()).block(block);
    }

    public void register(IEventBus bus) {
        register.register(bus);
    }

    @OnlyIn(Dist.CLIENT)
    public void registerTers() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> {
            entries.values().stream()
                    .filter(TileEntityEntry::hasTer)
                    .forEach(entry -> {
                        net.minecraftforge.fml.client.registry.ClientRegistry.bindTileEntityRenderer(entry.type, m -> entry.ter.get().apply(m));
                    });
        });
    }

    public class TileEntityEntry<T extends TileEntity> {

        String name;
        RegistryObject<TileEntityType<T>> object;
        TileEntityType<T> type;
        final Set<Supplier<? extends Block>> blocks = new HashSet<>();
        final Set<Block> cachedBlocks = new HashSet<>();
        Supplier<Function<net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher, net.minecraft.client.renderer.tileentity.TileEntityRenderer>> ter = null;

        public TileEntityEntry(String name) {
            this.name = name;
        }

        public TileEntityEntry<T> block(Supplier<? extends Block> block) {
            blocks.add(block);
            return this;
        }

        public TileEntityEntry<T> blocks(Collection<? extends Supplier<Block>> blocks) {
            this.blocks.addAll(blocks);
            return this;
        }

        public TileEntityEntry<T> te(Supplier<T> factory) {
            object = register.register(name, () -> {
                if (cachedBlocks.isEmpty()) {
                    blocks.stream().map(Supplier::get).forEach(cachedBlocks::add);
                }
                return type = new TileEntityType<>(factory, cachedBlocks, null);
            });
            return this;
        }

        public TileEntityEntry<T> te(Function<TileEntityType<T>, T> factory) {
            object = register.register(name, () -> {
                if (cachedBlocks.isEmpty()) {
                    blocks.stream().map(Supplier::get).forEach(cachedBlocks::add);
                }
                return type = new TileEntityType<>(() -> factory.apply(type), cachedBlocks, null);
            });
            return this;
        }

        public TileEntityEntry<T> ter(Supplier<Function<net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher, net.minecraft.client.renderer.tileentity.TileEntityRenderer>> ter) {
            this.ter = ter;
            return this;
        }

        public String getName() {
            return name;
        }

        public TileEntityType<T> getType() {
            Objects.requireNonNull(object, "No typeObject in te entry " + name);
            return Objects.requireNonNull(type, "Type is not created in te entry " + name);
        }

        public RegistryObject<TileEntityType<T>> getObject() {
            return Objects.requireNonNull(object, "No typeObject in te entry " + name);
        }

        public boolean hasTer() {
            return ter != null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TileEntityEntry<?> that = (TileEntityEntry<?>) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
