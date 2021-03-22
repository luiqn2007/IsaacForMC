package lq2007.mcmod.isaacmod.common.util;

import com.google.common.collect.Table;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.function.Supplier;

public class CollectionUtils {

    public static <R, C, V> V computeIfAbsent(Table<R, C, V> table, R row, C column, Supplier<V> value) {
        if (table.contains(row, column)) {
            return table.get(row, column);
        } else {
            V v = value.get();
            table.put(row, column, v);
            return v;
        }
    }

    public static <T> T[] asArray(Class<T> type, @Nullable T object) {
        if (object == null) {
            return (T[]) Array.newInstance(type, 0);
        } else {
            T[] array = (T[]) Array.newInstance(type, 1);
            array[0] = object;
            return array;
        }
    }
}
