package lq2007.mcmod.isaacformc.common.util.serializer;

import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacformc.common.prop.type.Props;
import lq2007.mcmod.isaacformc.common.util.serializer.ISerializer;
import lq2007.mcmod.isaacformc.common.util.serializer.ResourceLocationSerializer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class PropTypeSerializer implements ISerializer<AbstractPropType> {

    @Override
    public AbstractPropType read(PacketBuffer buffer) {
        ResourceLocation key = ResourceLocationSerializer.INSTANCE.read(buffer);
        return Props.get(key).orElseThrow(RuntimeException::new);
    }

    @Override
    public PacketBuffer write(AbstractPropType item, PacketBuffer buffer) {
        return ResourceLocationSerializer.INSTANCE.write(item.key, buffer);
    }

    @Override
    public AbstractPropType read(CompoundNBT nbt, String key) {
        ResourceLocation name = ResourceLocationSerializer.INSTANCE.read(nbt, key);
        return Props.get(name).orElseThrow(RuntimeException::new);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, AbstractPropType item) {
        return ResourceLocationSerializer.INSTANCE.write(nbt, key, item.key);
    }
}
