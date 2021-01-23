package lq2007.mcmod.isaacformc.isaac;

import com.google.common.collect.Lists;
import lq2007.mcmod.isaacformc.isaac.room.EnumRoom;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.*;

public abstract class IsaacItem {

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

    private final List<EnumRoom> rooms;

    public IsaacItem(ResourceLocation key, int id, EnumRoom... rooms) {
        this.key = key;
        this.id = id;
        this.version = EnumIsaacVersion.fromPropId(id);
        this.rooms = Lists.newArrayList(rooms);
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

    public List<EnumRoom> spawnRoom() {
        return rooms;
    }

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
