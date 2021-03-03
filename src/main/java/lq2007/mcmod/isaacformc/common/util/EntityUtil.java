package lq2007.mcmod.isaacformc.common.util;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeWorldServer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityUtil {

    public static final Map<World, Map<UUID, Entity>> WORLD_ENTITY_MAP = new HashMap<>();

    @Nullable
    public static Entity findEntityByUuid(@Nullable World world, @Nullable UUID uuid, boolean playerFirst) {
        if (world == null || uuid == null) return null;

        // player
        if (playerFirst) {
            PlayerEntity player = world.getPlayerByUuid(uuid);
            if (player != null) {
                return player;
            }
        }

        // server
        if (world instanceof IForgeWorldServer) {
            return ((IForgeWorldServer) world).getWorldServer().getEntityByUuid(uuid);
        }

        // client
        if (world instanceof ClientWorld) {
            Iterable<Entity> entities = ((ClientWorld) world).getAllEntities();
            for (Entity entity : entities) {
                if (entity.getUniqueID().equals(uuid)) {
                    return entity;
                }
            }
            return null;
        }

        // other
        Map<UUID, Entity> entityMap = WORLD_ENTITY_MAP.get(world);
        return entityMap != null ? entityMap.get(uuid) : null;

    }

    @Nullable
    public static <T extends Entity> T findEntityByUuid(@Nullable World world, @Nullable UUID uuid, Class<?> type) {
        if (world == null || uuid == null) return null;

        // player
        if (PlayerEntity.class.isAssignableFrom(type)) {
            PlayerEntity player = world.getPlayerByUuid(uuid);
            return type.isInstance(player) ? (T) player : null;
        }

        // server
        if (world instanceof IForgeWorldServer) {
            Entity entity = ((IForgeWorldServer) world).getWorldServer().getEntityByUuid(uuid);
            return type.isInstance(entity) ? (T) entity : null;
        }

        // client
        if (world instanceof ClientWorld) {
            Iterable<Entity> entities = ((ClientWorld) world).getAllEntities();
            for (Entity entity : entities) {
                if (entity.getUniqueID().equals(uuid)) {
                    return type.isInstance(entity) ? (T) entity : null;
                }
            }
            return null;
        }

        // other
        Map<UUID, Entity> entityMap = WORLD_ENTITY_MAP.get(world);
        if (entityMap != null) {
            Entity entity = entityMap.getOrDefault(uuid, null);
            return type.isInstance(entity) ? (T) entity : null;
        }
        return null;
    }

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
}
