package lq2007.mcmod.isaacformc.common.capability;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class IsaacCapabilities {

    @CapabilityInject(IIsaacPropData.class)
    public static Capability<IIsaacPropData> CAPABILITY_PROP;

    @CapabilityInject(IIsaacProperty.class)
    public static Capability<IIsaacProperty> CAPABILITY_PROPERTY;

    public static IIsaacPropData getPropData(LivingEntity entity) {
        return entity.getCapability(CAPABILITY_PROP).orElseGet(IIsaacPropData::dummy);
    }

    public static IIsaacProperty getProperty(LivingEntity entity) {
        return entity.getCapability(CAPABILITY_PROPERTY).orElseGet(IIsaacProperty::dummy);
    }
}
