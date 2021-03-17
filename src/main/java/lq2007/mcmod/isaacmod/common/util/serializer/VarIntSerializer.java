package lq2007.mcmod.isaacmod.common.util.serializer;

import net.minecraft.network.PacketBuffer;

public class VarIntSerializer extends IntegerSerializer {

    public static final VarIntSerializer INSTANCE = new VarIntSerializer();

    @Override
    public int read0(PacketBuffer buffer) {
        return buffer.readVarInt();
    }

    @Override
    public void write0(int value, PacketBuffer buffer) {
        buffer.writeVarInt(value);
    }
}
