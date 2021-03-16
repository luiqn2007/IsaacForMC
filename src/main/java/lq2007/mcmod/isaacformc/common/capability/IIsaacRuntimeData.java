package lq2007.mcmod.isaacformc.common.capability;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IIsaacRuntimeData extends ICapabilityProvider {

    static IIsaacRuntimeData dummy() {
        return DummyData.INSTANCE;
    }

    @Nonnull
    @Override
    default <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction direction) {
        return IsaacCapabilities.CAPABILITY_RUNTIME_DATA.orEmpty(capability, LazyOptional.of(() -> this));
    }

    class DummyData implements IIsaacRuntimeData {

        private static final DummyData INSTANCE = new DummyData();
    }
}
