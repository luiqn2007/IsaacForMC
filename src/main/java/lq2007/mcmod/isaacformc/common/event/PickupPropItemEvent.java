package lq2007.mcmod.isaacformc.common.event;

import lq2007.mcmod.isaacformc.common.prop.PropItem;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class PickupPropItemEvent extends Event {

    public final LivingEntity entity;

    private PropItem prop;

    public PickupPropItemEvent(LivingEntity entity, PropItem prop) {
        this.entity = entity;
        this.prop = prop;
    }

    public PropItem getProp() {
        return prop;
    }

    public void setProp(PropItem prop) {
        this.prop = prop;
    }
}
