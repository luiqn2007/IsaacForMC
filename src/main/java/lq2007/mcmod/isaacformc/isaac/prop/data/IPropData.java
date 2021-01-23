package lq2007.mcmod.isaacformc.isaac.prop.data;

import lq2007.mcmod.isaacformc.common.network.IPacketReader;
import lq2007.mcmod.isaacformc.common.network.IPacketWriter;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPropData extends INBTSerializable<CompoundNBT>, IPacketReader, IPacketWriter {

    void onBindTo(PropItem item);

    void update(LivingEntity entity, PropItem item);

    void active();

    boolean isActive();
}
