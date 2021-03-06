package lq2007.mcmod.isaacmod.common.handler;

import lq2007.mcmod.isaacmod.Isaac;
import lq2007.mcmod.isaacmod.common.capability.CapabilityRegister;
import lq2007.mcmod.isaacmod.common.capability.IsaacProps;
import lq2007.mcmod.isaacmod.common.capability.IsaacProperty;
import lq2007.mcmod.isaacmod.common.capability.PropEntity;
import lq2007.mcmod.isaacmod.common.prop.Prop;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapabilityEventHandler {

    public static final ResourceLocation CAPABILITY_PROPS = new ResourceLocation(Isaac.ID, "cap_props");
    public static final ResourceLocation CAPABILITY_PROPERTY = new ResourceLocation(Isaac.ID, "cap_property");
    public static final ResourceLocation CAPABILITY_PROP_ENTITY = new ResourceLocation(Isaac.ID, "cap_prop_entity");

    @SubscribeEvent
    public static void onBindToEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            event.addCapability(CAPABILITY_PROPS, new IsaacProps());
            event.addCapability(CAPABILITY_PROPERTY, new IsaacProperty());
        }
    }

    @SubscribeEvent
    public static void onBindToProp(AttachCapabilitiesEvent<Prop> event) {
        event.addCapability(CAPABILITY_PROP_ENTITY, new PropEntity());
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            CapabilityRegister capabilities = Isaac.CAPABILITIES;
            capabilities.getProps(event.getPlayer()).copy(capabilities.getProps(event.getOriginal()));
            capabilities.getProperty(event.getPlayer()).copy(capabilities.getProperty(event.getOriginal()));
        }
    }
}
