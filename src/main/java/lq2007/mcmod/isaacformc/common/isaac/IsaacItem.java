package lq2007.mcmod.isaacformc.common.isaac;

import net.minecraft.util.ResourceLocation;

public abstract class IsaacItem {

    public final ResourceLocation key;

    public IsaacItem(ResourceLocation key) {
        this.key = key;
    }

    abstract public String getName();

    abstract public String getDescription();
}
