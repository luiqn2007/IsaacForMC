package lq2007.mcmod.isaacformc.common.util.serializer.packet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NBTData {

    /**
     * True if the field is an object and it might be null.
     * A packet must have at most 8 nullable objects.
     * @return if the object maybe null.
     */
    boolean nullable() default false;

    /**
     * <p>Appoint a type to create the object from a nbt.</p>
     * <p>It must implement {@link INBTReader} interface and have a constructor with zero parameter.</p>
     * @return Reader class
     */
    Class<? extends INBTReader> reader() default INBTReader.class;

    /**
     * <p>Appoint a type to write the object to a nbt.</p>
     * <p>It must implement {@link INBTWriter} interface and have a constructor with zero parameter.</p>
     * @return Writer class
     */
    Class<? extends INBTWriter> writer() default INBTWriter.class;

    /**
     * <p>Appoint a type to create the object from a nbt and write the object to a nbt.</p>
     * <p>It must implement {@link INBTSerializer} interface and have a constructor with zero parameter.</p>
     * @return Serializer class
     */
    Class<? extends INBTSerializer> serializer() default INBTSerializer.class;

    /**
     * If the data is a collection, specify the collection type. This type must have a constructor with zero parameter.
     * @return collection type
     */
    Class<? extends Collection> collection() default ArrayList.class;

    /**
     * If the data is a map, specify the map type. This type must have a constructor with zero parameter.
     * @return map type
     */
    Class<? extends Map> map() default HashMap.class;

    /**
     * If the field is a map, this is the key's class.
     * @return key type
     */
    Class<?> K() default Object.class;

    /**
     * If the field is a map, this is the value's class; if the field is a collection, this is the content's class.
     * @return value type
     */
    Class<?> V() default Object.class;
}
