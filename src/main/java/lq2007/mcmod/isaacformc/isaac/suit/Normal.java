package lq2007.mcmod.isaacformc.isaac.suit;

import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class Normal extends SuitType {

    public Normal(String key, int id, PropType<?>... contains) {
        super(key, id, contains);
    }

    @Override
    public ITextComponent getName() {
        return StringTextComponent.EMPTY;
    }

    @Override
    public ITextComponent getDescription() {
        return StringTextComponent.EMPTY;
    }

    @Override
    protected void onRealized(LivingEntity entity) {

    }
}
