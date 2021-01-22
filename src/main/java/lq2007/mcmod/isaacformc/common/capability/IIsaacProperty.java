package lq2007.mcmod.isaacformc.common.capability;

import lq2007.mcmod.isaacformc.common.network.IPacketReader;
import lq2007.mcmod.isaacformc.common.network.IPacketWriter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.INBTSerializable;

public interface IIsaacProperty extends INBTSerializable<CompoundNBT>,
        ICopyFromEntity<Void>, IDirtyData, IPacketReader, IPacketWriter {

    static IIsaacProperty dummy() {
        return DummyData.INSTANCE;
    }

    int bodySize();

    void bodySize(int size);

    void lockBodySize();

    boolean isBodySizeLocked();

    int tearCount();

    void tearCount(int count);

    void tearCount(int count, int maxCount);

    float tearSpeed();

    void tearSpeed(float count);

    float tearDelay();

    void tearDelay(float delay);

    class DummyData implements IIsaacProperty {

        private static final DummyData INSTANCE = new DummyData();

        @Override
        public int bodySize() {
            return 0;
        }

        @Override
        public void bodySize(int size) { }

        @Override
        public void lockBodySize() { }

        @Override
        public boolean isBodySizeLocked() {
            return false;
        }

        @Override
        public int tearCount() {
            return 1;
        }

        @Override
        public void tearCount(int count) { }

        @Override
        public void tearCount(int count, int maxCount) { }

        @Override
        public float tearSpeed() {
            return 0;
        }

        @Override
        public void tearSpeed(float count) { }

        @Override
        public float tearDelay() {
            return 0;
        }

        @Override
        public void tearDelay(float delay) { }

        @Override
        public CompoundNBT serializeNBT() {
            return new CompoundNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) { }

        @Override
        public Void copyFrom(LivingEntity entity) {
            return null;
        }

        @Override
        public void markDirty() { }

        @Override
        public boolean isDirty() {
            return false;
        }

        @Override
        public void read(PacketBuffer buffer) {
            buffer.readByte();
        }

        @Override
        public void write(PacketBuffer buffer) {
            buffer.writeByte(0);
        }
    }
}
