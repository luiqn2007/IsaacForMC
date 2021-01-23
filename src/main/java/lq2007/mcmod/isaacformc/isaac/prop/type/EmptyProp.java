package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.data.NoData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public final class EmptyProp extends PropType<NoData> {

    public static final EmptyProp EMPTY = new EmptyProp();
    private static final ResourceLocation KEY = new ResourceLocation("isaac", "empty");

    private EmptyProp() {
        super(KEY, false);
    }

    @Override
    protected NoData createData() {
        return NoData.INSTANCE;
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
