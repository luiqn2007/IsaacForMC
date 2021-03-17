package lq2007.mcmod.isaacmod.isaac.suit;

import lq2007.mcmod.isaacmod.common.prop.type.AbstractPropType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class Normal extends SuitType {

    public Normal(String key, int id, AbstractPropType... contains) {
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
}
