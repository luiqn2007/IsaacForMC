package lq2007.mcmod.isaacformc.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IIsaacProperty extends INBTSerializable<CompoundNBT> {

    static IIsaacProperty dummy() {
        return DummyData.INSTANCE;
    }

    int bodySize();

    void bodySize(int size);

    void lockBodySize();

    int tearCount();

    void tearCount(int count);

    void tearCount(int count, int maxCount);

    class DummyData implements IIsaacProperty {

        private static DummyData INSTANCE = new DummyData();

        @Override
        public int bodySize() {
            return 0;
        }

        @Override
        public void bodySize(int size) { }

        @Override
        public void lockBodySize() { }

        @Override
        public int tearCount() {
            return 1;
        }

        @Override
        public void tearCount(int count) { }

        @Override
        public void tearCount(int count, int maxCount) { }

        @Override
        public CompoundNBT serializeNBT() {
            return new CompoundNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) { }
    }
}
