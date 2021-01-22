package lq2007.mcmod.isaacformc.common.capability;

import lq2007.mcmod.isaacformc.common.network.IPacketReader;
import lq2007.mcmod.isaacformc.common.network.IPacketWriter;
import net.minecraft.network.PacketBuffer;

public abstract class VersionCapability implements IPacketReader, IPacketWriter, IDirtyData {

    private int lastVersion = 0, currentVersion = 0;

    @Override
    public boolean isDirty() {
        return currentVersion > lastVersion;
    }

    @Override
    public void markDirty() {
        if (!isDirty()) {
            currentVersion = Math.max(lastVersion + 1, 0);
        }
    }

    @Override
    public final void read(PacketBuffer buffer) {
        if (buffer.readBoolean()) {
            int version = buffer.readVarInt();
            if (version >= 0 && currentVersion < version) {
                lastVersion = currentVersion = version;
                read(buffer, version);
            }
        }
    }

    @Override
    public final void write(PacketBuffer buffer) {
        buffer.writeBoolean(true);
        buffer.writeVarInt(currentVersion);
        write(buffer, currentVersion);
        lastVersion = currentVersion;
    }

    abstract protected void read(PacketBuffer buffer, int version);

    abstract protected void write(PacketBuffer buffer, int version);
}
