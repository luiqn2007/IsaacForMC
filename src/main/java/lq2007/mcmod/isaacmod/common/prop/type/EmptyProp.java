package lq2007.mcmod.isaacmod.common.prop.type;

import lq2007.mcmod.isaacmod.Isaac;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public final class EmptyProp extends AbstractPropType {

    public static EmptyProp EMPTY;

    private EmptyProp() {
        super(new ResourceLocation(Isaac.ID, "empty"));
        EMPTY = this;
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
