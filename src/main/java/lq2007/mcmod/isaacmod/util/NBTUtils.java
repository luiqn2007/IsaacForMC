package lq2007.mcmod.isaacmod.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class NBTUtils {

    public static <ITEM, NBT extends INBT> ListNBT write(Iterable<ITEM> list, Function<ITEM, NBT> map) {
        ListNBT nbt = new ListNBT();
        for (ITEM item : list) {
            nbt.add(map.apply(item));
        }
        return nbt;
    }

    public static ListNBT write(Iterable<? extends INBTSerializable<?>> list) {
        return write(list, INBTSerializable::serializeNBT);
    }

    public static <K, V extends INBTSerializable<? extends INBT>> CompoundNBT write(Map<K, V> map, Function<K, String> keyFunction) {
        CompoundNBT nbt = new CompoundNBT();
        map.forEach((key, value) -> nbt.put(keyFunction.apply(key), value.serializeNBT()));
        return nbt;
    }

    public static <K, V extends INBTSerializable<? extends INBT>> CompoundNBT write(Map<K, V> map) {
        return write(map, Objects::toString);
    }

    public static <K, V, NBT extends INBT> void read(CompoundNBT nbt, Map<K, V> map, Function<String, K> keyFunction, Function<NBT, V> valueFunction) {
        map.clear();
        for (String kStr : nbt.keySet()) {
            K key = keyFunction.apply(kStr);
            NBT vNbt = (NBT) nbt.get(kStr);
            V value = valueFunction.apply(vNbt);
            map.put(key, value);
        }
    }

    public static <NBT extends INBT, RESULT> void read(ListNBT list, Collection<RESULT> result, Function<NBT, RESULT> converter) {
        result.clear();
        for (INBT nbt : list) {
            RESULT e = converter.apply((NBT) nbt);
            if (e == null) {
                throw new RuntimeException("Can't convert nbt to object: " + nbt);
            }
            result.add(e);
        }
    }

    public static <RESULT extends Enum<RESULT>, LIST extends Collection<RESULT>> void read(ListNBT list, LIST result, Class<RESULT> enumType) {
        read(list, result, nbt -> Enum.valueOf(enumType, nbt.getString()));
    }
}
