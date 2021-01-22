package lq2007.mcmod.isaacformc.isaac;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Objects;

public abstract class IsaacItem {

    public final ResourceLocation key;

    /**
     * id > 0 means the item is from The Binding of Isaac. The id is equals with the id in that game.
     * If the item is extended, the value is meaninglessly.
     */
    public final int id;
    public final EnumIsaacVersion version;

    public IsaacItem(ResourceLocation key, int id, EnumIsaacVersion version) {
        this.key = key;
        this.id = id;
        this.version = version;
    }

    /**
     * Get the name of the item.
     * It can be used for display.
     *
     * @return name
     */
    abstract public ITextComponent getName();

    /**
     * Get the description of the item.
     * It can be used for display.
     *
     * @return description
     */
    abstract public ITextComponent getDescription();

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
