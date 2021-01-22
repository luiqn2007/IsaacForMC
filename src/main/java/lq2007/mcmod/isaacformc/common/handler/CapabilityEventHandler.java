package lq2007.mcmod.isaacformc.common.handler;

import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.capability.IsaacPropData;
import lq2007.mcmod.isaacformc.common.capability.IsaacProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapabilityEventHandler {

    public static final ResourceLocation CAPABILITY_PROP_ITEMS = new ResourceLocation(Isaac.ID, "isaac_capability_item");
    public static final ResourceLocation CAPABILITY_PROPERTY = new ResourceLocation(Isaac.ID, "isaac_capability_prop");

    @SubscribeEvent
    public static void onBindToEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            event.addCapability(CAPABILITY_PROP_ITEMS, new IsaacPropData());
            event.addCapability(CAPABILITY_PROPERTY, new IsaacProperty());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            IsaacCapabilities.getPropData(event.getPlayer()).copyFrom(event.getOriginal());
            IsaacCapabilities.getProperty(event.getPlayer()).copyFrom(event.getOriginal());
        }
    }
}
