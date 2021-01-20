package lq2007.mcmod.isaacformc.common.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.isaac.prop.PropItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import static lq2007.mcmod.isaacformc.Isaac.ID;

// id 1 伤心洋葱 The Sad Onion 宝箱房
// 射速 + 0.7
public class PropTheSadOnion extends PropType {

    public PropTheSadOnion() {
        super(new ResourceLocation(ID, "thesadonion"), false);
    }

    @Override
    public void onPickUp(LivingEntity entity, PropItem prop) {
        super.onPickUp(entity, prop);
    }
}
