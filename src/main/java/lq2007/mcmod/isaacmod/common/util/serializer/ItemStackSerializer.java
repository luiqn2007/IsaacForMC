package lq2007.mcmod.isaacmod.common.util.serializer;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class ItemStackSerializer implements ISerializer<ItemStack> {

    public static final ItemStackSerializer INSTANCE = new ItemStackSerializer();

    @Override
    public ItemStack read(PacketBuffer buffer) {
        return buffer.readItemStack();
    }

    @Override
    public PacketBuffer write(ItemStack item, PacketBuffer buffer) {
        return buffer.writeItemStack(item);
    }

    @Override
    public ItemStack read(CompoundNBT nbt, String key) {
        return ItemStack.read(nbt.getCompound(key));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, ItemStack item) {
        nbt.put(key, item.serializeNBT());
        return nbt;
    }
}
