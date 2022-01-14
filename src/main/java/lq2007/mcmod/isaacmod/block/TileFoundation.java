package lq2007.mcmod.isaacmod.block;

import lq2007.mcmod.isaacmod.prop.Prop;
import net.minecraft.tileentity.TileEntity;

public class TileFoundation extends TileEntity {

    private Prop prop = Prop.EMPTY;

    public TileFoundation() {
        super(ModTileEntities.FOUNDATION.get());
    }

    public Prop getProp() {
        return prop;
    }

    public boolean hasProp() {
        return prop != null;
    }

    public Prop setProp(Prop prop) {
        Prop old = this.prop;
        this.prop = prop;
        return old;
    }
}
