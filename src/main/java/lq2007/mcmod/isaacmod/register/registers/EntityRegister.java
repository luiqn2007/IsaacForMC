package lq2007.mcmod.isaacmod.register.registers;

import lq2007.mcmod.isaacmod.register.Register;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class EntityRegister extends BaseDeferredRegister<EntityType<?>, Entity> {

    public EntityRegister(Register context, String packageName) {
        super(ForgeRegistries.ENTITIES, context, Entity.class, packageName);
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
}
