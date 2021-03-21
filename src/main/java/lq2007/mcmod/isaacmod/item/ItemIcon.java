package lq2007.mcmod.isaacmod.item;

import lq2007.mcmod.isaacmod.Isaac;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemIcon extends Item {

    private static ItemGroup group = null;

    public static ItemGroup group() {
        if (group == null) {
            group = new ItemGroup();
        }
        return group;
    }

    public ItemIcon() {
        super(new Properties());
    }

    public static class ItemGroup extends net.minecraft.item.ItemGroup {

        ItemStack icon = null;

        public ItemGroup() {
            super("isaac");
        }

        @Override
        @Nonnull
        public ItemStack createIcon() {
            if (icon == null) {
                icon = new ItemStack(Isaac.ITEMS.get(ItemIcon.class));
            }
            return icon;
        }
    }
}
