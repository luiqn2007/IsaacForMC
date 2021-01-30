package lq2007.mcmod.isaacformc.isaac.prop.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

import java.util.Collections;
import java.util.List;

public class DataBrotherBobby extends SimpleData implements IDataWithFriends {

    public FriendData bobby = new FriendData();
    private List<FriendData> friends = Collections.singletonList(bobby);

    @Override
    public void read(PacketBuffer buffer) {
        bobby.read(buffer);
    }

    @Override
    public void write(PacketBuffer buffer) {
        bobby.write(buffer);
    }

    @Override
    public List<FriendData> getFriends() {
        return friends;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return bobby.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        bobby.deserializeNBT(nbt);
    }
}
