package lq2007.mcmod.isaacmod.common.util.serializer.buffer;

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
public @interface BufferData {

    /**
     * True if the field is an object and it might be null.
     * A packet must have at most 8 nullable objects.
     * @return if the object maybe null.
     */
    boolean nullable() default false;

    /**
     * True if the field is integer or long, and use varint or varlong.
     * @return if the integer will compress.
     */
    boolean compress() default false;

    /**
     * <p>Appoint a type to create the object from a buffer.</p>
     * <p>It must implement {@link IPacketReader} interface and have a constructor with zero parameter.</p>
     * @return Reader class
     */
    Class<? extends IPacketReader> reader() default IPacketReader.class;

    /**
     * <p>Appoint a type to write the object to a buffer.</p>
     * <p>It must implement {@link IPacketWriter} interface and have a constructor with zero parameter.</p>
     * @return Writer class
     */
    Class<? extends IPacketWriter> writer() default IPacketWriter.class;

    /**
     * <p>Appoint a type to create the object from a buffer and write the object to a buffer.</p>
     * <p>It must implement {@link IPacketSerializer} interface and have a constructor with zero parameter.</p>
     * @return Serializer class
     */
    Class<? extends IPacketSerializer> serializer() default IPacketSerializer.class;

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
