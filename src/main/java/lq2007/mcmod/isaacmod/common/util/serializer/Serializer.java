package lq2007.mcmod.isaacmod.common.util.serializer;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Serializer {
    Class<? extends ISerializer<?>> value();
}
