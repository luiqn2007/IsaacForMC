package lq2007.mcmod.isaacformc.common.capability;

import lq2007.mcmod.isaacformc.common.prop.Prop;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class IsaacCapabilities {

    @CapabilityInject(IIsaacProps.class)
    public static Capability<IIsaacProps> CAPABILITY_PROPS;

    @CapabilityInject(IIsaacProperty.class)
    public static Capability<IIsaacProperty> CAPABILITY_PROPERTY;

    @CapabilityInject(IIsaacRuntimeData.class)
    public static Capability<IIsaacRuntimeData> CAPABILITY_RUNTIME_DATA;

    @CapabilityInject(IPropEntity.class)
    public static Capability<IPropEntity> CAPABILITY_PROP_ENTITY;

    public static IIsaacProps getProps(LivingEntity entity) {
        return entity.getCapability(CAPABILITY_PROPS).orElseGet(IIsaacProps::dummy);
    }

    public static IIsaacProperty getProperty(LivingEntity entity) {
        return entity.getCapability(CAPABILITY_PROPERTY).orElseGet(IIsaacProperty::dummy);
    }

    public static IPropEntity getPropEntity(Prop prop) {
        return prop.getCapability(CAPABILITY_PROP_ENTITY).orElseGet(IPropEntity::dummy);
    }

    public static IIsaacRuntimeData getRuntimeData(LivingEntity entity) {
        return entity.getCapability(CAPABILITY_RUNTIME_DATA).orElseGet(IIsaacRuntimeData::dummy);
    }
}
