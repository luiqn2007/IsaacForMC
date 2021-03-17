package lq2007.mcmod.isaacmod.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class ResourceLocationSerializer implements ISerializer<ResourceLocation> {

    public static final ResourceLocationSerializer INSTANCE = new ResourceLocationSerializer();

    @Override
    public ResourceLocation read(PacketBuffer buffer) {
        return buffer.readResourceLocation();
    }

    @Override
    public PacketBuffer write(ResourceLocation item, PacketBuffer buffer) {
        return buffer.writeResourceLocation(item);
    }

    @Override
    public ResourceLocation read(CompoundNBT nbt, String key) {
        return new ResourceLocation(nbt.getString(key));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, ResourceLocation item) {
        nbt.putString(key, item.toString());
        return nbt;
    }
}
