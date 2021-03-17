package lq2007.mcmod.isaacmod.register.registers;

import lq2007.mcmod.isaacmod.register.Register;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class EntityRegister extends BaseDeferredRegister<EntityType<?>, Entity> {

    public EntityRegister(Register context, String packageName) {
        super(ForgeRegistries.ENTITIES, context, Entity.class, packageName);
    }

    @Override
    protected Optional<ImmutablePair<Class<? extends Entity>, Supplier<EntityType<?>>>> build(String className) {
        try {
            Class<?> aClass = context.classLoader.loadClass(className);
            if (!aClass.isInterface() && resultType.isAssignableFrom(aClass)) {
                Class<? extends Entity> eType = (Class<? extends Entity>) aClass;
                EntityInfo info = eType.getAnnotation(EntityInfo.class);
                if (info != null && !Modifier.isAbstract(eType.getModifiers())) {

                    return Optional.of(ImmutablePair.of(eType, () -> {
                        EntityType.IFactory<?> factory;
                        if (info.factory() == EntityType.IFactory.class) {
                            factory = new EntityType.IFactory<Entity>() {

                                Constructor<?> c = null;
                                int type = -1;

                                @Override
                                public Entity create(EntityType<Entity> entityType, World world) {
                                    try {
                                        if (c == null) {
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
                                                        return null;
                                                    }
                                                }
                                            }
                                        }
                                        if (c != null) {
                                            switch (type) {
                                                case 0: return (Entity) c.newInstance(world);
                                                case 1: return (Entity) c.newInstance(entityType, world);
                                                case 2: return (Entity) c.newInstance(world, entityType);
                                                default: return null;
                                            }
                                        }
                                    } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                                        throw new RuntimeException(e);
                                    }
                                    return null;
                                }
                            };
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
                                Class<?> c = eType.getClassLoader().loadClass(info.clientFactory());
                                Object o = c.newInstance();
                                builder.setCustomClientFactory((BiFunction) o);
                            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ignored) { }
                        }
                        return builder.build(eType.getSimpleName().toLowerCase(Locale.ROOT));
                    }));
                }
            }
            return Optional.empty();
        } catch (ClassNotFoundException e) {
            System.out.println("Skip " + className + " because of " + e.getMessage());
            return Optional.empty();
        }
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
}
