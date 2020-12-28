package lq2007.mcmod.isaacformc.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class IsaacCapability {

    @CapabilityInject(IIsaacData.class)
    public static Capability<IIsaacData> CAPABILITY;
}
