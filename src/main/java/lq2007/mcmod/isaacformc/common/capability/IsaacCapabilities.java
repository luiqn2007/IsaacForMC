package lq2007.mcmod.isaacformc.common.capability;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class IsaacCapabilities {

    @CapabilityInject(IIsaacPropData.class)
    public static Capability<IIsaacPropData> CAPABILITY_PROP;

    @CapabilityInject(IIsaacProperty.class)
    public static Capability<IIsaacProperty> CAPABILITY_PROPERTY;

    @CapabilityInject(IIsaacRuntimeData.class)
    public static Capability<IIsaacRuntimeData> CAPABILITY_RUNTIME_DATA;

    public static void registerAll() {
        CapabilityManager manager = CapabilityManager.INSTANCE;
        manager.register(IIsaacPropData.class, IsaacPropDataStorage.INSTANCE, IsaacPropData::new);
        manager.register(IIsaacProperty.class, IsaacPropertyStorage.INSTANCE, IsaacProperty::new);
    }

    public static IIsaacPropData getPropData(LivingEntity entity) {
        return entity.getCapability(CAPABILITY_PROP).orElseGet(IIsaacPropData::dummy);
    }

    public static IIsaacProperty getProperty(LivingEntity entity) {
        return entity.getCapability(CAPABILITY_PROPERTY).orElseGet(IIsaacProperty::dummy);
    }

    public static IIsaacRuntimeData getRuntimeData(LivingEntity entity) {
        return entity.getCapability(CAPABILITY_RUNTIME_DATA).orElseGet(IIsaacRuntimeData::dummy);
    }
}
