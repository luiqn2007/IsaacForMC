package lq2007.mcmod.isaacformc.common.isaac.prop;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPropData extends INBTSerializable<CompoundNBT> {

    void onBindTo(PropItem item);
}
