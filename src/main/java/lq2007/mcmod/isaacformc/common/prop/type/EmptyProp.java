package lq2007.mcmod.isaacformc.common.prop.type;

import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.common.prop.Prop;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.network.PacketBuffer;

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

    @Override
    public Prop read(PacketBuffer buffer) {
        return null;
    }

    @Override
    public PacketBuffer write(Prop item, PacketBuffer buffer) {
        return null;
    }
}
