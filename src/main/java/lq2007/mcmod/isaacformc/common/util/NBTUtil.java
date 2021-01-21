package lq2007.mcmod.isaacformc.common.util;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class NBTUtil {

    public static ListNBT convert(List<? extends INBTSerializable<?>> list) {
        ListNBT nbt = new ListNBT();
        for (INBTSerializable<?> item : list) {
            nbt.add(item.serializeNBT());
        }
        return nbt;
    }

    public static <NBT extends INBT, RESULT> List<RESULT> convert(ListNBT list, Function<NBT, RESULT> converter) {
        List<RESULT> result = new ArrayList<>();
        for (INBT nbt : list) {
            result.add(converter.apply((NBT) nbt));
        }
        return result;
    }
}
