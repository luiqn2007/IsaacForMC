package lq2007.mcmod.isaacformc.common.prop.type;

import lq2007.mcmod.isaacformc.common.Isaac;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public final class EmptyProp extends AbstractPropType {

    public static final EmptyProp EMPTY = new EmptyProp();
    private static final ResourceLocation KEY = new ResourceLocation(Isaac.ID, "empty");

    private EmptyProp() {
        super(KEY);
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
