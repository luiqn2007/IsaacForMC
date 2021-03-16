package lq2007.mcmod.isaacformc.common.capability;

import lq2007.mcmod.isaacformc.common.util.EntityHolder;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IPropEntity extends ICapabilitySerializable<CompoundNBT> {

    static IPropEntity dummy() {
        return DummyData.INSTANCE;
    }

    boolean hasEntity();

    EntityHolder<?> getEntity();

    void bindEntity(Entity entity);

    void copy(IPropEntity source);

    @Override
    default <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return IsaacCapabilities.CAPABILITY_PROP_ENTITY.orEmpty(cap, LazyOptional.of(() -> this));
    }

    class DummyData implements IPropEntity {

        private static final DummyData INSTANCE = new DummyData();

        @Override
        public boolean hasEntity() {
            return false;
        }

        @Override
        public EntityHolder<?> getEntity() {
            return EntityHolder.EMPTY;
        }

        @Override
        public void bindEntity(Entity entity) { }

        @Override
        public void copy(IPropEntity source) { }

        @Override
        public CompoundNBT serializeNBT() {
            return new CompoundNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) { }
    }
}
