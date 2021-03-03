package lq2007.mcmod.isaacformc.common.util;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class NBTUtils {

    public static ListNBT convert(List<? extends INBTSerializable<?>> list) {
        ListNBT nbt = new ListNBT();
        for (INBTSerializable<?> item : list) {
            nbt.add(item.serializeNBT());
        }
        return nbt;
    }

    public static <ITEM, NBT extends INBT> ListNBT convert(List<ITEM> list, Function<ITEM, NBT> map) {
        ListNBT nbt = new ListNBT();
        for (ITEM item : list) {
            nbt.add(map.apply(item));
        }
        return nbt;
    }

    public static <NBT extends INBT, RESULT> List<RESULT> convert(ListNBT list, Function<NBT, RESULT> converter) {
        List<RESULT> result = new ArrayList<>();
        for (INBT nbt : list) {
            RESULT e = converter.apply((NBT) nbt);
            if (e == null) {
                throw new RuntimeException("Can't convert nbt to object: " + nbt);
            }
            result.add(e);
        }
        return result;
    }
}
