package lq2007.mcmod.isaacmod.isaac.ornament;

import lq2007.mcmod.isaacmod.isaac.IsaacElement;
import net.minecraft.util.ResourceLocation;

public abstract class Ornament extends IsaacElement {

    public Ornament(ResourceLocation key, int id) {
        super(key, id);
    }

    public Ornament(ResourceLocation key) {
        super(key, 0);
    }
}
