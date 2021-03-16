package lq2007.mcmod.isaacformc.common.util.serializer.buffer;

import lq2007.mcmod.isaacformc.common.util.FieldWrapper;
import lq2007.mcmod.isaacformc.common.util.serializer.*;
import net.minecraft.network.PacketBuffer;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class FieldPacketWrapper extends FieldWrapper {

    IPacketReader reader = null, readerK = null, readerV = null;
    IPacketWriter writer = null, writerK = null, writerV = null;
    Class<?> parent = null;
    boolean isCollection = false, isMap = false;

    public FieldPacketWrapper(Field field) {
        super(field);
    }

    void read(Object obj, PacketBuffer buffer) {
        if (reader != null) readNormal(obj, buffer);
        else if (isCollection) readCollection(obj, buffer);
        else if (isMap) readMap(obj, buffer);
    }

    protected void readNormal(Object obj, PacketBuffer buffer) {
        try {
            Class<?> type = field.getType();
            if (type == boolean.class && reader instanceof BooleanSerializer) {
                field.setBoolean(obj, ((BooleanSerializer) reader).read0(buffer));
            } else if (type == byte.class && reader instanceof ByteSerializer) {
                field.setByte(obj, ((ByteSerializer) reader).read0(buffer));
            } else if (type == char.class && reader instanceof CharSerializer) {
                field.setChar(obj, ((CharSerializer) reader).read0(buffer));
            } else if (type == short.class && reader instanceof ShortSerializer) {
                field.setShort(obj, ((ShortSerializer) reader).read0(buffer));
            } else if (type == int.class && reader instanceof IntegerSerializer) {
                field.setInt(obj, ((IntegerSerializer) reader).read0(buffer));
            } else if (type == long.class && reader instanceof LongSerializer) {
                field.setLong(obj, ((LongSerializer) reader).read0(buffer));
            } else if (type == float.class && reader instanceof FloatSerializer) {
                field.setFloat(obj, ((FloatSerializer) reader).read0(buffer));
            } else if (type == double.class && reader instanceof DoubleSerializer) {
                field.setDouble(obj, ((DoubleSerializer) reader).read0(buffer));
            } else {
                field.set(obj, reader.read(buffer));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void readCollection(Object obj, PacketBuffer buffer) {
        try {
            Collection c = (Collection) parent.newInstance();
            int count = buffer.readVarInt();
            for (int i = 0; i < count; i++) {
                c.add(readerV.read(buffer));
            }
            field.set(obj, c);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void readMap(Object obj, PacketBuffer buffer) {
        try {
            Map map = (Map) parent.newInstance();
            int count = buffer.readVarInt();
            for (int i = 0; i < count; i++) {
                Object key = readerK.read(buffer);
                Object value = readerV.read(buffer);
                map.put(key, value);
            }
            field.set(obj, map);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    void write(Object obj, PacketBuffer buffer) {
        if (writer != null) writeNormal(obj, buffer);
        else if (isCollection) writeCollection(obj, buffer);
        else if (isMap) writeMap(obj, buffer);
    }

    protected void writeNormal(Object obj, PacketBuffer buffer) {
        try {
            Class<?> type = field.getType();

            if (type == boolean.class && writer instanceof BooleanSerializer) {
                ((BooleanSerializer) writer).write0(field.getBoolean(obj), buffer);
            } else if (type == byte.class && writer instanceof ByteSerializer) {
                ((ByteSerializer) writer).write0(field.getByte(obj), buffer);
            } else if (type == char.class && writer instanceof CharSerializer) {
                ((CharSerializer) writer).write0(field.getChar(obj), buffer);
            } else if (type == short.class && writer instanceof ShortSerializer) {
                ((ShortSerializer) writer).write0(field.getShort(obj), buffer);
            } else if (type == int.class && writer instanceof IntegerSerializer) {
                ((IntegerSerializer) writer).write0(field.getInt(obj), buffer);
            } else if (type == long.class && writer instanceof LongSerializer) {
                ((LongSerializer) writer).write0(field.getLong(obj), buffer);
            } else if (type == float.class && writer instanceof FloatSerializer) {
                ((FloatSerializer) writer).write0(field.getFloat(obj), buffer);
            } else if (type == double.class && writer instanceof DoubleSerializer) {
                ((DoubleSerializer) writer).write0(field.getDouble(obj), buffer);
            } else {
                writer.write(field.get(obj), buffer);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void writeCollection(Object obj, PacketBuffer buffer) {
        try {
            Collection c = (Collection) field.get(obj);
            buffer.writeVarInt(c.size());
            for (Object o : c) {
                writerV.write(o, buffer);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void writeMap(Object obj, PacketBuffer buffer) {
        try {
            Map map = (Map) field.get(obj);
            buffer.writeVarInt(map.size());
            map.forEach((key, value) -> {
                writerK.write(key, buffer);
                writerV.write(key, buffer);
            });
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
