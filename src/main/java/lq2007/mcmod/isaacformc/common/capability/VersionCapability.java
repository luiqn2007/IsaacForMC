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

    /**
     * <li>Value 0b means the packet is empty.</li>
     * <li>Value 1b means the packet is a normal data packet.</li>
     * <li>Value 2b means the packet is a update packet.</li>
     * @return the head of packet
     */
    protected byte bufferHead() {
        return 1;
    }

    @Override
    public final void read(PacketBuffer buffer) {
        byte packetHead = buffer.readByte();
        if (packetHead == 1) {
            int version = buffer.readVarInt();
            if (version >= 0 && currentVersion < version) {
                lastVersion = currentVersion = version;
                read(buffer, version);
            }
        }
    }

    @Override
    public final void write(PacketBuffer buffer) {
        buffer.writeByte(bufferHead());
        buffer.writeVarInt(currentVersion);
        write(buffer, currentVersion);
        lastVersion = currentVersion;
    }

    abstract protected void read(PacketBuffer buffer, int version);

    abstract protected void write(PacketBuffer buffer, int version);
}
