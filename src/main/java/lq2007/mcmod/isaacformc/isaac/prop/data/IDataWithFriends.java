package lq2007.mcmod.isaacformc.isaac.prop.data;

import lq2007.mcmod.isaacformc.common.entity.friend.EnumFriendTypes;
import lq2007.mcmod.isaacformc.common.network.IPacketReader;
import lq2007.mcmod.isaacformc.common.network.IPacketWriter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;
import java.util.UUID;

public interface IDataWithFriends {

    List<FriendData> getFriends();

    class FriendData implements INBTSerializable<CompoundNBT>, IPacketReader, IPacketWriter {
        public EnumFriendTypes type = EnumFriendTypes.NONE;
        public UUID id = null;
        public int index = -1;

        public boolean hasEntity() {
            return id != null && type != EnumFriendTypes.NONE;
        }

        @Override
        public void read(PacketBuffer buffer) {
            type = EnumFriendTypes.get(buffer.readByte());
            if (type != EnumFriendTypes.NONE) {
                id = buffer.readUniqueId();
                index = buffer.readVarInt();
            }
        }

        @Override
        public void write(PacketBuffer buffer) {
            if (hasEntity()) {
                buffer.writeByte(type.index);
                buffer.writeUniqueId(id);
                buffer.writeVarInt(index);
            } else {
                buffer.writeByte(EnumFriendTypes.NONE.index);
            }
        }

        @Override
        public CompoundNBT serializeNBT() {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putByte("_friend_type", type.index);
            if (id != null) {
                nbt.putInt("_friend_index", index);
                nbt.putUniqueId("_friend_id", id);
            }
            return null;
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            type = EnumFriendTypes.get(nbt.getByte("_friend_type"));
            index = nbt.getInt("_friend_index");
            if (nbt.hasUniqueId("_friend_id")) {
                id = nbt.getUniqueId("_friend_id");
            }
        }
    }
}
