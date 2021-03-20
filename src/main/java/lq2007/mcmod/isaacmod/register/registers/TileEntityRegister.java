package lq2007.mcmod.isaacmod.register.registers;

import lq2007.mcmod.isaacmod.register.ObjectConstructor;
import lq2007.mcmod.isaacmod.register.Register;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class TileEntityRegister extends BaseDeferredRegister<TileEntityType<?>, TileEntity> {

    protected final Function<Class<? extends TileEntity>, Block[]> blockSupplier;
    protected final BiFunction<Class<? extends TileEntity>, TileEntityType<?>, String> asTerClass;
    protected IRegister register = null;

    public TileEntityRegister(Register context, String packageName,
                              Function<Class<? extends TileEntity>, Block[]> blockSupplier,
                              @Nullable BiFunction<Class<? extends TileEntity>, TileEntityType<?>, String> asTerClass) {
        super(ForgeRegistries.TILE_ENTITIES, context, TileEntity.class, packageName);
        this.blockSupplier = blockSupplier;
        this.asTerClass = asTerClass;
    }

    public TileEntityRegister(Register context,
                              Function<Class<? extends TileEntity>, Block[]> blockSupplier,
                              @Nullable BiFunction<Class<? extends TileEntity>, TileEntityType<?>, String> asTerClass) {
        super(ForgeRegistries.TILE_ENTITIES, context, TileEntity.class);
        this.blockSupplier = blockSupplier;
        this.asTerClass = asTerClass;
    }

    @Nullable
    @Override
    protected Supplier<? extends TileEntityType<?>> build(String name, Class<? extends TileEntity> aClass) throws Exception {
        Supplier<? extends TileEntity> supplier = new ObjectConstructor<>(aClass);
        return () -> TileEntityType.Builder.create(supplier, blockSupplier.apply(aClass)).build(null);
    }

    @Override
    public void apply() {
        super.apply();

        if (register != null && !classes.isEmpty() && FMLEnvironment.dist.isClient()) {
            FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegister());
        }
    }

    @OnlyIn(Dist.CLIENT)
    class ModelRegister {

        @SubscribeEvent
        public void onRegister(net.minecraftforge.client.event.ModelRegistryEvent event) {
            for (Map.Entry<Class<? extends TileEntity>, RegistryObject<TileEntityType<?>>> entry : objMap.entrySet()) {
                Class te = entry.getClass();
                TileEntityType type = entry.getValue().get();
                String terTypeName = asTerClass.apply(te, type);
                try {
                    Class<?> terClass = te.getClassLoader().loadClass(terTypeName);
                    if (net.minecraft.client.renderer.tileentity.TileEntityRenderer.class.isAssignableFrom(terClass)) {
                        net.minecraftforge.fml.client.registry.ClientRegistry.bindTileEntityRenderer(type, new TerFactory(terClass));
                    }
                } catch (ClassNotFoundException ignored) {
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class TerFactory implements Function {

        Class aClass;
        Constructor c;

        TerFactory(Class aClass) throws NoSuchMethodException {
            this.aClass = aClass;
            this.c = aClass.getConstructor(net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher.class);
            this.c.setAccessible(true);
        }

        @Override
        public Object apply(Object o) {
            try {
                return c.newInstance(o);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
