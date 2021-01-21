package lq2007.mcmod.isaacformc.common.util;

import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.network.IsaacNetworks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;

import java.util.UUID;

public class EntityUtil {

    public static void healthUp(LivingEntity entity, UUID uuid, String name, int healthCount) {
        if (entity.isServerWorld()) {
            AttributeModifierManager manager = entity.getAttributeManager();
            if (manager.hasAttributeInstance(Attributes.MAX_HEALTH)) {
                ModifiableAttributeInstance attribute = manager.createInstanceIfAbsent(Attributes.MAX_HEALTH);
                AttributeModifier modifier = attribute.getModifier(uuid);
                double count = healthCount;
                if (modifier != null) {
                    count += modifier.getAmount();
                    attribute.removePersistentModifier(uuid);
                }
                AttributeModifier newModifier = new AttributeModifier(uuid, name, count, AttributeModifier.Operation.ADDITION);
                attribute.applyPersistentModifier(newModifier);
            }
        }
    }

    public static void healthDown(LivingEntity entity, UUID uuid, String name, double healthCount) {
        if (entity.isServerWorld()) {
            float maxHealth = entity.getMaxHealth();
            if (maxHealth <= healthCount) {
                healthCount = maxHealth - 1;
            }
            AttributeModifierManager manager = entity.getAttributeManager();
            if (manager.hasAttributeInstance(Attributes.MAX_HEALTH)) {
                ModifiableAttributeInstance attribute = manager.createInstanceIfAbsent(Attributes.MAX_HEALTH);
                AttributeModifier modifier = attribute.getModifier(uuid);
                double count = -healthCount;
                if (modifier != null) {
                    count += modifier.getAmount();
                    attribute.removePersistentModifier(uuid);
                }
                AttributeModifier newModifier = new AttributeModifier(uuid, name, count, AttributeModifier.Operation.ADDITION);
                attribute.applyPersistentModifier(newModifier);
            }
        }
    }

    public static void fullHealth(LivingEntity entity) {
        if (entity.isServerWorld()) {
            entity.setHealth(entity.getMaxHealth());
        }
    }

    public static void bigger(LivingEntity entity) {
        if (entity.isServerWorld()) {
            IsaacCapabilities.getProperty(entity).bigger();
            IsaacNetworks.notifyEntitySizeChanged(entity);
        }
    }

    public static void smaller(LivingEntity entity) {
        if (entity.isServerWorld()) {
            IsaacCapabilities.getProperty(entity).smaller();
            IsaacNetworks.notifyEntitySizeChanged(entity);
        }
    }
}
