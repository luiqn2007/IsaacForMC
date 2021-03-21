package lq2007.mcmod.isaacmod.register.registers;

import lq2007.mcmod.isaacmod.register.Register;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import org.objectweb.asm.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static lq2007.mcmod.isaacmod.Isaac.LOGGER;

public class EntityRegister extends BaseDeferredRegister<EntityType<?>, Entity> {

    protected IRegister register;

    public EntityRegister(Register context, String packageName) {
        super(ForgeRegistries.ENTITIES, context, Entity.class, packageName);
    }

    public EntityRegister(Register context) {
        super(ForgeRegistries.ENTITIES, context, Entity.class);
    }

    @Override
    protected Supplier<? extends EntityType<?>> build(String name, Class<? extends Entity> aClass) throws Exception {
        EntityInfo info = aClass.getAnnotation(EntityInfo.class);
        if (info != null) {
            return () -> {
                EntityType.IFactory<?> factory;
                if (info.factory() == EntityType.IFactory.class) {
                    factory = new EntityConstructor<>(aClass);
                } else {
                    try {
                        factory = info.factory().newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                EntityType.Builder<?> builder = EntityType.Builder.create(factory, info.classification());
                builder.size(info.width(), info.height());
                if (!info.summonable()) builder.disableSummoning();
                if (!info.serializable()) builder.disableSerialization();
                if (!info.immuneToFire()) builder.immuneToFire();
                if (info.tracking() != -1) builder.trackingRange(info.tracking());
                if (info.updateInterval() != -1) builder.setUpdateInterval(info.updateInterval());
                if (!info.clientFactory().isEmpty()) {
                    try {
                        Class<?> c = aClass.getClassLoader().loadClass(info.clientFactory());
                        Object o = c.newInstance();
                        builder.setCustomClientFactory((BiFunction) o);
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ignored) { }
                }
                return builder.build(name);
            };
        }
        return null;
    }

    public EntityRegister withRender(Function<Class<? extends Entity>, String> asRenderClass) {
        if (FMLEnvironment.dist.isClient()) {
            IRegister register = asRender(asRenderClass);
            context.bus.addListener((Consumer<FMLClientSetupEvent>) event -> register.apply());
        }
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public IRegister asRender(Function<Class<? extends Entity>, String> asRenderClass) {
        if (register == null) {
            register = new EntityRenderRegister(asRenderClass);
        }
        return register;
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface EntityInfo {

        float width() default 0.6f;
        float height() default 1.8f;
        Class<? extends EntityType.IFactory> factory() default EntityType.IFactory.class;
        EntityClassification classification() default EntityClassification.CREATURE;
        boolean summonable() default true;
        boolean serializable() default true;
        boolean immuneToFire() default false;
        int tracking() default -1;
        int updateInterval() default -1;
        String clientFactory() default "";
    }

    static class EntityConstructor<T extends Entity> implements EntityType.IFactory<T> {

        Constructor<?> c;
        int type;

        EntityConstructor(Class<T> aClass) {
            try {
                c = aClass.getDeclaredConstructor(World.class);
                c.setAccessible(true);
                type = 0;
            } catch (NoSuchMethodException e) {
                try {
                    c = aClass.getDeclaredConstructor(EntityType.class, World.class);
                    c.setAccessible(true);
                    type = 1;
                } catch (NoSuchMethodException e0) {
                    try {
                        c = aClass.getDeclaredConstructor(World.class, EntityType.class);
                        c.setAccessible(true);
                        type = 2;
                    } catch (NoSuchMethodException e1) {
                        throw new RuntimeException("We need current constructor");
                    }
                }
            }
        }

        @Override
        public T create(EntityType<T> entityType, World world) {
            try {
                switch (type) {
                    case 0: return (T) c.newInstance(world);
                    case 1: return (T) c.newInstance(entityType, world);
                    case 2: return (T) c.newInstance(world, entityType);
                }
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @OnlyIn(Dist.CLIENT)
    class EntityRenderRegister implements IRegister {

        Function<Class<? extends Entity>, String> asRenderClass;

        public EntityRenderRegister(Function<Class<? extends Entity>, String> asRenderClass) {
            this.asRenderClass = asRenderClass;
        }

        @Override
        public void cache(ClassLoader classLoader, Type clazz, String className, String packageName, Class<?> aClass) { }

        @Override
        public void apply() {
            for (Class<? extends Entity> aClass : classes) {
                String render = asRenderClass.apply(aClass);
                try {
                    Class<?> renderClass = aClass.getClassLoader().loadClass(render);
                    EntityType type = get(aClass);
                    net.minecraftforge.fml.client.registry.IRenderFactory factory;
                    if (net.minecraftforge.fml.client.registry.IRenderFactory.class.isAssignableFrom(renderClass)) {
                        factory = (net.minecraftforge.fml.client.registry.IRenderFactory) renderClass.newInstance();
                    } else if (net.minecraft.client.renderer.entity.EntityRenderer.class.isAssignableFrom(renderClass)) {
                        factory = new EntityRenderFactory(renderClass);
                    } else return;
                    net.minecraftforge.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler(type, factory);
                } catch (NullPointerException | ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
                    LOGGER.warn("\tSkip {} because {}: {}", aClass.getName(), e.getClass().getSimpleName(), e.getMessage());
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class EntityRenderFactory implements net.minecraftforge.fml.client.registry.IRenderFactory {

        Class aClass;
        Constructor c;

        EntityRenderFactory(Class aClass) throws NoSuchMethodException {
            this.aClass = aClass;
            this.c = aClass.getDeclaredConstructor(net.minecraft.client.renderer.entity.EntityRendererManager.class);

            c.setAccessible(true);
        }

        @Override
        public net.minecraft.client.renderer.entity.EntityRenderer createRenderFor(net.minecraft.client.renderer.entity.EntityRendererManager manager) {
            try {
                return (net.minecraft.client.renderer.entity.EntityRenderer) c.newInstance(manager);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                return null;
            }
        }
    }
}
