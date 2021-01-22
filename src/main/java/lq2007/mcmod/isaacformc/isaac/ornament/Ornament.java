package lq2007.mcmod.isaacformc.isaac.ornament;

import lq2007.mcmod.isaacformc.isaac.EnumIsaacVersion;
import lq2007.mcmod.isaacformc.isaac.IsaacItem;
import net.minecraft.util.ResourceLocation;

public abstract class Ornament extends IsaacItem {

    public Ornament(ResourceLocation key, int id) {
        super(key, id, EnumIsaacVersion.ISAAC);
    }

    public Ornament(ResourceLocation key) {
        super(key, 0, EnumIsaacVersion.ISAAC);
    }
}
