package lq2007.mcmod.isaacmod.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;

public class TextSerializer implements ISerializer<ITextComponent> {

    public static final TextSerializer INSTANCE = new TextSerializer();

    @Override
    public ITextComponent read(PacketBuffer buffer) {
        return buffer.readTextComponent();
    }

    @Override
    public PacketBuffer write(ITextComponent item, PacketBuffer buffer) {
        return buffer.writeTextComponent(item);
    }

    @Override
    public ITextComponent read(CompoundNBT nbt, String key) {
        return ITextComponent.Serializer.getComponentFromJson(nbt.getString(key));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, ITextComponent item) {
        nbt.putString(key, ITextComponent.Serializer.toJson(item));
        return nbt;
    }
}
