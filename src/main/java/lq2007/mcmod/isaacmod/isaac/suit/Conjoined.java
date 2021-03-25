package lq2007.mcmod.isaacmod.isaac.suit;

import lq2007.mcmod.isaacmod.common.prop.type.AbstractPropType;
import net.minecraft.entity.LivingEntity;

public class Conjoined extends SuitType {

    public Conjoined(String key, int id, AbstractPropType... contains) {
        super(key, id, contains);
    }

    @Override
    public void onRealized(LivingEntity entity) {
        super.onRealized(entity);
    }
}
