package lq2007.mcmod.isaacformc.common.handler;

import lq2007.mcmod.isaacformc.Isaac;
import lq2007.mcmod.isaacformc.common.capability.IIsaacPropData;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.isaac.prop.*;
import lq2007.mcmod.isaacformc.common.network.PacketEntityProp;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

@Mod.EventBusSubscriber
public class IsaacPropHandler {

    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        IIsaacPropData data = IsaacCapabilities.fromEntity(entity);
        for (PropItem propItem : data.getAllProps()) {
            if (propItem.type instanceof IUpdateType) {
                ((IUpdateType) propItem.type).onUpdate(propItem, entity);
            }
        }
        if (entity instanceof ServerPlayerEntity && data.isDirty()) {
            Isaac.MOD.network.sendTo(new PacketEntityProp(data), ((ServerPlayerEntity) entity).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
            data.clearDirty();
        }
    }

    @SubscribeEvent
    public static void onEntityAttack(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        IIsaacPropData data = IsaacCapabilities.fromEntity(entity);
        for (PropItem propItem : data.getAllPassiveProps()) {
            PropType type = propItem.type;
            if (type instanceof IAttackType) {
                IAttackType at = (IAttackType) type;
                float amount = event.getAmount();
                DamageSource source = event.getSource();
                if (at.cancelEntityAttack(propItem, entity, source, amount)) {
                    event.setCanceled(true);
                    break;
                } else {
                    float newAmount = at.onEntityAttack(propItem, entity, source, amount);
                    if (amount != newAmount) {
                        event.setAmount(newAmount);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        IIsaacPropData data = IsaacCapabilities.fromEntity(entity);
        for (PropItem propItem : data.getAllPassiveProps()) {
            PropType type = propItem.type;
            if (type instanceof IHurtType) {
                IHurtType ht = (IHurtType) type;
                float amount = event.getAmount();
                DamageSource source = event.getSource();
                if (ht.cancelEntityHurt(propItem, entity, source, amount)) {
                    event.setCanceled(true);
                    break;
                } else {
                    float newAmount = ht.onEntityHurt(propItem, entity, source, amount);
                    if (amount != newAmount) {
                        event.setAmount(newAmount);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntityLiving();
        IIsaacPropData data = IsaacCapabilities.fromEntity(entity);
        for (PropItem propItem : data.getAllPassiveProps()) {
            PropType type = propItem.type;
            if (type instanceof IDeathType) {
                DamageSource source = event.getSource();
                if (((IDeathType) type).onEntityDeath(propItem, entity, source)) {
                    event.setCanceled(true);
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityUseItem(LivingEntityUseItemEvent event) {
        IItemUseType.EnumItemUseStage stage = IItemUseType.EnumItemUseStage.fromEvent(event);
        LivingEntity entity = event.getEntityLiving();
        IIsaacPropData data = IsaacCapabilities.fromEntity(entity);
        for (PropItem propItem : data.getAllPassiveProps()) {
            PropType type = propItem.type;
            if (type instanceof IItemUseType) {
                IItemUseType iut = (IItemUseType) type;
                ItemStack stack = event.getItem();
                int duration = event.getDuration();
                if (iut.cancelEntityUseItem(stage, entity, stack, duration)) {
                    if (event.isCancelable()) {
                        event.setCanceled(true);
                        break;
                    }
                } else {
                    int newDuring = iut.onEntityItemUse(propItem, stage, entity, stack, duration);
                    if (newDuring != duration) {
                        event.setDuration(newDuring);
                    }
                }
            }
        }
    }
}
