package lq2007.mcmod.isaacformc.common.util;

import lq2007.mcmod.isaacformc.common.entity.EntityFriend;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;

import java.util.UUID;

public class EntityUtil {

    public static void healthUp(LivingEntity entity, UUID uuid, String name, int healthCount) {
        if (!entity.world.isRemote) {
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
        if (!entity.world.isRemote) {
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

    public static void damageUp(LivingEntity entity, UUID uuid, String name, double damageCount) {
        if (!entity.world.isRemote) {
            AttributeModifierManager manager = entity.getAttributeManager();
            if (manager.hasAttributeInstance(Attributes.ATTACK_DAMAGE)) {
                ModifiableAttributeInstance attribute = manager.createInstanceIfAbsent(Attributes.ATTACK_DAMAGE);
                AttributeModifier modifier = attribute.getModifier(uuid);
                double count = damageCount;
                if (modifier != null) {
                    count += modifier.getAmount();
                    attribute.removePersistentModifier(uuid);
                }
                AttributeModifier newModifier = new AttributeModifier(uuid, name, count, AttributeModifier.Operation.ADDITION);
                attribute.applyPersistentModifier(newModifier);
            }
        }
    }

    public static void damageFix(LivingEntity entity, UUID uuid, String name, double fixCount) {
        if (!entity.world.isRemote) {
            AttributeModifierManager manager = entity.getAttributeManager();
            if (manager.hasAttributeInstance(Attributes.ATTACK_DAMAGE)) {
                ModifiableAttributeInstance attribute = manager.createInstanceIfAbsent(Attributes.ATTACK_DAMAGE);
                AttributeModifier modifier = attribute.getModifier(uuid);
                double count = fixCount;
                if (modifier != null) {
                    count *= modifier.getAmount();
                    attribute.removePersistentModifier(uuid);
                }
                AttributeModifier newModifier = new AttributeModifier(uuid, name, count, AttributeModifier.Operation.MULTIPLY_TOTAL);
                attribute.applyPersistentModifier(newModifier);
            }
        }
    }

    public static boolean damageFixConflict(LivingEntity entity, UUID uuid, double fixCount) {
        if (!entity.world.isRemote) {
            AttributeModifierManager manager = entity.getAttributeManager();
            if (manager.hasAttributeInstance(Attributes.ATTACK_DAMAGE)) {
                ModifiableAttributeInstance attribute = manager.createInstanceIfAbsent(Attributes.ATTACK_DAMAGE);
                AttributeModifier modifier = attribute.getModifier(uuid);
                if (modifier == null) {
                    String name = "isaac.damage_fix." + uuid.toString().substring(0, 5);
                    AttributeModifier newModifier = new AttributeModifier(uuid, name, fixCount, AttributeModifier.Operation.MULTIPLY_TOTAL);
                    attribute.applyPersistentModifier(newModifier);
                    return true;
                }
            }
        }
        return false;
    }

    public static void fullHealth(LivingEntity entity) {
        if (!entity.world.isRemote) {
            entity.setHealth(entity.getMaxHealth());
        }
    }

    public static void spawnFollowing(LivingEntity entity, EntityType<? extends EntityFriend> type) {
        if (!entity.world.isRemote) {
            EntityFriend e = type.create(entity.world);
            if (e != null) {
                e.setOwner(entity);
                entity.world.addEntity(e);
            }
        }
    }
}
