package lq2007.mcmod.isaacformc.common.capability;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class IsaacCapabilities {

    @CapabilityInject(IIsaacPropData.class)
    public static Capability<IIsaacPropData> CAPABILITY;

    public static IIsaacPropData fromEntity(LivingEntity entity) {
        return entity.getCapability(CAPABILITY).orElseGet(IIsaacPropData::dummy);
    }
}
