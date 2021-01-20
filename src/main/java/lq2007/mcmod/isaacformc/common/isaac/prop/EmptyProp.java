package lq2007.mcmod.isaacformc.common.isaac.prop;

import lq2007.mcmod.isaacformc.common.isaac.prop.type.PropType;
import net.minecraft.util.ResourceLocation;

public final class EmptyProp extends PropType {

    public static final EmptyProp EMPTY = new EmptyProp();
    private static final ResourceLocation KEY = new ResourceLocation("isaac", "empty");

    private EmptyProp() {
        super(KEY, false);
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
