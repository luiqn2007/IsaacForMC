package lq2007.mcmod.isaacmod.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;

import java.util.Objects;
import java.util.UUID;

public class UUIDSerializer implements ISerializer<UUID> {

    public static final UUIDSerializer INSTANCE = new UUIDSerializer();

    @Override
    public UUID read(PacketBuffer buffer) {
        return buffer.readUniqueId();
    }

    @Override
    public PacketBuffer write(UUID item, PacketBuffer buffer) {
        buffer.writeUniqueId(item);
        return buffer;
    }

    @Override
    public UUID read(CompoundNBT nbt, String key) {
        return NBTUtil.readUniqueId(Objects.requireNonNull(nbt.get(key)));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, UUID item) {
        nbt.put(key, NBTUtil.func_240626_a_(item));
        return nbt;
    }
}
