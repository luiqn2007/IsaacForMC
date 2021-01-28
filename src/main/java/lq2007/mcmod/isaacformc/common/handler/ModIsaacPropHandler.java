package lq2007.mcmod.isaacformc.common.handler;

import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.event.PickupPropItemEvent;
import lq2007.mcmod.isaacformc.common.util.IsaacUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModIsaacPropHandler {

    @SubscribeEvent
    public static void onPropPickup(PickupPropItemEvent event) {
        LivingEntity entity = event.entity;
        IsaacUtil.checkAchievement(IsaacCapabilities.getPropData(entity));
        IsaacUtil.checkSuit();
    }
}
