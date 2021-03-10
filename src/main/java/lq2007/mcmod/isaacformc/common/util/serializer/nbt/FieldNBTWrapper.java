package lq2007.mcmod.isaacformc.common.util.serializer.nbt;

import lq2007.mcmod.isaacformc.common.util.FieldWrapper;
import lq2007.mcmod.isaacformc.common.util.serializer.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class FieldNBTWrapper extends FieldWrapper {

    INBTReader reader = null, readerK = null, readerV = null;
    INBTWriter writer = null, writerK = null, writerV = null;
    Class<?> parent = null;
    boolean isCollection = false, isMap = false;

    public FieldNBTWrapper(Field field) {
        super(field);
    }

    void read(Object obj, CompoundNBT nbt) {
        if (reader != null) readNormal(obj, nbt);
        else if (isCollection) readCollection(obj, nbt);
        else if (isMap) readMap(obj, nbt);
    }

    protected void readNormal(Object obj, CompoundNBT nbt) {
        try {
            Class<?> type = field.getType();
            if (type == boolean.class && reader instanceof BooleanSerializer) {
                field.setBoolean(obj, ((BooleanSerializer) reader).read0(nbt, name));
            } else if (type == byte.class && reader instanceof ByteSerializer) {
                field.setByte(obj, ((ByteSerializer) reader).read0(nbt, name));
            } else if (type == char.class && reader instanceof CharSerializer) {
                field.setChar(obj, ((CharSerializer) reader).read0(nbt, name));
            } else if (type == short.class && reader instanceof ShortSerializer) {
                field.setShort(obj, ((ShortSerializer) reader).read0(nbt, name));
            } else if (type == int.class && reader instanceof IntegerSerializer) {
                field.setInt(obj, ((IntegerSerializer) reader).read0(nbt, name));
            } else if (type == long.class && reader instanceof LongSerializer) {
                field.setLong(obj, ((LongSerializer) reader).read0(nbt, name));
            } else if (type == float.class && reader instanceof FloatSerializer) {
                field.setFloat(obj, ((FloatSerializer) reader).read0(nbt, name));
            } else if (type == double.class && reader instanceof DoubleSerializer) {
                field.setDouble(obj, ((DoubleSerializer) reader).read0(nbt, name));
            } else {
                field.set(obj, reader.read(nbt, name));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void readCollection(Object obj, CompoundNBT nbt) {
        try {
            Collection c = (Collection) parent.newInstance();
            ListNBT list = nbt.getList(name, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                c.add(readerV.read(list.getCompound(i), "value"));
            }
            field.set(obj, c);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void readMap(Object obj, CompoundNBT nbt) {
        try {
            Map m = (Map) parent.newInstance();
            ListNBT map = nbt.getList(name, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < map.size(); i++) {
                CompoundNBT data = map.getCompound(i);
                m.put(readerK.read(data, "key"), readerV.read(data, "value"));
            }
            field.set(obj, m);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    void write(Object obj, CompoundNBT nbt) {
        if (writer != null) writeNormal(obj, nbt);
        if (isCollection) writeCollection(obj, nbt);
        if (isMap) writeMap(obj, nbt);
    }

    protected void writeNormal(Object obj, CompoundNBT nbt) {
        try {
            Class<?> type = field.getType();
            if (type == boolean.class && writer instanceof BooleanSerializer) {
                ((BooleanSerializer) writer).write0(nbt, name, field.getBoolean(obj));
            } else if (type == byte.class && writer instanceof ByteSerializer) {
                ((ByteSerializer) writer).write0(nbt, name, field.getByte(obj));
            } else if (type == char.class && writer instanceof CharSerializer) {
                ((CharSerializer) writer).write0(nbt, name, field.getChar(obj));
            } else if (type == short.class && writer instanceof ShortSerializer) {
                ((ShortSerializer) writer).write0(nbt, name, field.getShort(obj));
            } else if (type == int.class && writer instanceof IntegerSerializer) {
                ((IntegerSerializer) writer).write0(nbt, name, field.getInt(obj));
            } else if (type == long.class && writer instanceof LongSerializer) {
                ((LongSerializer) writer).write0(nbt, name, field.getLong(obj));
            } else if (type == float.class && writer instanceof FloatSerializer) {
                ((FloatSerializer) writer).write0(nbt, name, field.getFloat(obj));
            } else if (type == double.class && writer instanceof DoubleSerializer) {
                ((DoubleSerializer) writer).write0(nbt, name, field.getDouble(obj));
            } else {
                writer.write(nbt, name, field.get(obj));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void writeCollection(Object obj, CompoundNBT nbt) {
        try {
            Collection c = (Collection) field.get(obj);
            ListNBT list = new ListNBT();
            for (Object o : c) {
                list.add(writerV.write(new CompoundNBT(), "value", o));
            }
            nbt.put(name, list);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void writeMap(Object obj, CompoundNBT nbt) {
        try {
            Map m = (Map) field.get(obj);
            ListNBT map = new ListNBT();
            m.forEach((key, value) -> {
                CompoundNBT data = new CompoundNBT();
                writerK.write(data, "key", key);
                writerV.write(data, "value", value);
                map.add(data);
            });
            nbt.put(name, map);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
