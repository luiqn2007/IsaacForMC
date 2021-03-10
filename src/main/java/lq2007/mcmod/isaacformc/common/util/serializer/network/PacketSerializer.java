package lq2007.mcmod.isaacformc.common.util.serializer.network;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface PacketSerializer {

    Class<? extends IPacketReader> reader() default IPacketReader.class;

    Class<? extends IPacketWriter> writer() default IPacketWriter.class;

    Class<? extends IPacketSerializer> serializer() default IPacketSerializer.class;
}
