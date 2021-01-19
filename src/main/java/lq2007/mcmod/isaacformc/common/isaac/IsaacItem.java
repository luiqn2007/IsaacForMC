package lq2007.mcmod.isaacformc.common.isaac;

import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public abstract class IsaacItem {

    public final ResourceLocation key;

    public IsaacItem(ResourceLocation key) {
        this.key = key;
    }

    abstract public String getName();

    abstract public String getDescription();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IsaacItem isaacItem = (IsaacItem) o;
        return Objects.equals(key, isaacItem.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
