package lq2007.mcmod.isaacformc.common.util.serializer.nbt;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NBTSerializer {

    Class<? extends INBTReader> reader() default INBTReader.class;

    Class<? extends INBTWriter> writer() default INBTWriter.class;

    Class<? extends INBTSerializer> serializer() default INBTSerializer.class;
}
