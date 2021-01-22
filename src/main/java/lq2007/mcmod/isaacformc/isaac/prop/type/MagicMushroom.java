package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.common.util.EntityUtil;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public class MagicMushroom extends PropType {

    private static final UUID MODIFIER_HEALTH = UUID.fromString("57761f44-1b40-e862-2e72-07a43db0049a");

    public MagicMushroom() {
        super(new ResourceLocation(Isaac.ID, "magic_mushroom"), false, 12);
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item) {
        super.onPickup(entity, item);
        if (entity.isServerWorld()) {
            EntityUtil.healthUp(entity, MODIFIER_HEALTH, "magic_mushroom.health", 2);
            EntityUtil.fullHealth(entity);
            EntityUtil.bigger(entity);
        }
    }
}
