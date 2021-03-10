package lq2007.mcmod.isaacformc.common.util.serializer;

import net.minecraft.network.PacketBuffer;

public class VarLongSerializer extends LongSerializer {

    public static final VarLongSerializer INSTANCE = new VarLongSerializer();

    @Override
    public long read0(PacketBuffer buffer) {
        return buffer.readVarLong();
    }

    @Override
    public void write0(long value, PacketBuffer buffer) {
        buffer.writeVarLong(value);
    }
}
