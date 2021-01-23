package lq2007.mcmod.isaacformc.common.event;

import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class RemovePropItemEvent extends Event {

    public final LivingEntity entity;

    public final PropItem prop;

    public RemovePropItemEvent(LivingEntity entity, PropItem prop) {
        this.entity = entity;
        this.prop = prop;
    }

    public PropItem getProp() {
        return prop;
    }
}
