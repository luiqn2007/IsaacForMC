package lq2007.mcmod.isaacformc.isaac;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.*;

public abstract class IsaacElement {

    public final ResourceLocation key;

    /**
     * id > 0 means the item is from The Binding of Isaac. The id is equals with the id in that game.
     * If the item is extended, the value is meaninglessly.
     */
    public final int id;

    /**
     * The game version the item come from. If the item is create by other mod, the value is {@link EnumIsaacVersion#MOD}.
     */
    public final EnumIsaacVersion version;

    public IsaacElement(ResourceLocation key, int id) {
        this.key = key;
        this.id = id;
        this.version = EnumIsaacVersion.fromPropId(id);
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

    /**
     * Get the result of the item is enabled.
     *
     * @return True if the item is enabled.
     */
    public boolean isEnable() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IsaacElement element = (IsaacElement) o;
        return Objects.equals(key, element.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
