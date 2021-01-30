package lq2007.mcmod.isaacformc.isaac.prop.data;

import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import net.minecraft.entity.LivingEntity;

public abstract class SimpleData implements IPropData {

    @Override
    public void onBindTo(PropItem item) {

    }

    @Override
    public void update(LivingEntity entity, PropItem item) {

    }

    @Override
    public void active() {

    }

    @Override
    public boolean isActive() {
        return false;
    }
}
