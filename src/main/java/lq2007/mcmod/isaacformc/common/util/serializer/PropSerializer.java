package lq2007.mcmod.isaacformc.common.util.serializer;

import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacformc.common.prop.type.Props;
import lq2007.mcmod.isaacformc.common.util.serializer.ISerializer;
import lq2007.mcmod.isaacformc.common.util.serializer.Serializers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class PropSerializer implements ISerializer<Prop> {

    @Override
    public Prop read(PacketBuffer buffer) {
        AbstractPropType type = Props.get(buffer.readResourceLocation(), null);
        if (type != null) {
            return type.read(buffer);
        }
        return Prop.EMPTY;
    }

    @Override
    public PacketBuffer write(Prop item, PacketBuffer buffer) {
        AbstractPropType type = item.type;
        buffer.writeResourceLocation(type.key);
        type.write(item, buffer);
        return buffer;
    }

    @Override
    public Prop read(CompoundNBT nbt, String key) {
        CompoundNBT data = nbt.getCompound(key);
        AbstractPropType type = Serializers.getNBTReader(AbstractPropType.class).read(data, "type");
        if (type != Props.EMPTY) {
            Prop prop = new Prop(type);
            prop.deserializeNBT(nbt.getCompound("data"));
        }
        return Prop.EMPTY;
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, Prop item) {
        CompoundNBT data = new CompoundNBT();
        if (item != Prop.EMPTY) {
            Serializers.getNBTWriter(AbstractPropType.class).write(data, "type", item.type);
            data.put("data", item.serializeNBT());
            nbt.put(key, data);
        }
        return nbt;
    }
}
