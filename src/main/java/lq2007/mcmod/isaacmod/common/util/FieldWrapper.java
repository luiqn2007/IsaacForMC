package lq2007.mcmod.isaacmod.common.util;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class FieldWrapper implements Comparable<FieldWrapper> {

    public final Field field;
    public final String name;

    public FieldWrapper(Field field) {
        this.field = field;
        this.name = field.getName();
    }

    @Nullable
    public Object get(Object obj) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setNull(Object obj) {
        try {
            field.set(obj, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(FieldWrapper o) {
        return name.compareTo(o.name);
    }
}
