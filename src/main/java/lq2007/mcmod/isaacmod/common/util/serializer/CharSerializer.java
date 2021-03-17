package lq2007.mcmod.isaacmod.common.util.serializer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class CharSerializer implements ISerializer<Character> {

    public static final CharSerializer INSTANCE = new CharSerializer();

    @Override
    public Character read(PacketBuffer buffer) {
        return read0(buffer);
    }

    @Override
    public PacketBuffer write(Character item, PacketBuffer buffer) {
        write0(item, buffer);
        return buffer;
    }

    @Override
    public Character read(CompoundNBT nbt, String key) {
        return read0(nbt, key);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, String key, Character item) {
        return null;
    }

    public char read0(PacketBuffer buffer) {
        return buffer.readChar();
    }

    public void write0(char value, PacketBuffer buffer) {
        buffer.writeChar(value);
    }

    public char read0(CompoundNBT nbt, String key) {
        return Character.forDigit(nbt.getInt(key), 10);
    }

    public void write0(CompoundNBT nbt, String key, char item) {
        nbt.putInt(key, Character.digit(item, 10));
    }
}
