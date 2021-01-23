package lq2007.mcmod.isaacformc.common.capability;

import com.google.common.collect.ImmutableSet;
import lq2007.mcmod.isaacformc.common.network.IPacketReader;
import lq2007.mcmod.isaacformc.common.network.IPacketWriter;
import lq2007.mcmod.isaacformc.isaac.tear.EnumTearEffects;
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

    float tearSpeedMultiple();

    void tearSpeedMultiple(float count);

    float tossUpSpeed();

    void tossUpSpeed(float speed);

    float shootDelay();

    void shootDelay(float delay);

    float shootDelayMultiple();

    void shootDelayMultiple(float delay);

    int knockBack();

    void knockBack(int knockBack);

    void addTearEffect(EnumTearEffects type);

    ImmutableSet<EnumTearEffects> getTearEffects();

    void removeTearEffect(EnumTearEffects type);

    float range();

    void range(float range);

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
        public float tearSpeedMultiple() {
            return 1;
        }

        @Override
        public void tearSpeedMultiple(float count) { }

        @Override
        public float tossUpSpeed() {
            return 1;
        }

        @Override
        public void tossUpSpeed(float speed) { }

        @Override
        public float shootDelay() {
            return 0;
        }

        @Override
        public void shootDelay(float delay) { }

        @Override
        public float shootDelayMultiple() {
            return 1;
        }

        @Override
        public void shootDelayMultiple(float delay) { }

        @Override
        public int knockBack() {
            return 0;
        }

        @Override
        public void knockBack(int knockBack) { }

        @Override
        public void addTearEffect(EnumTearEffects type) { }

        @Override
        public ImmutableSet<EnumTearEffects> getTearEffects() {
            return ImmutableSet.of();
        }

        @Override
        public void removeTearEffect(EnumTearEffects type) { }

        @Override
        public float range() {
            return 1;
        }

        @Override
        public void range(float range) {

        }

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
