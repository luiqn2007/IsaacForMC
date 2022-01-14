package lq2007.mcmod.isaacmod.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModGroups {

    public static RegisterGroup REGISTER = new RegisterGroup();

    public static ItemGroup MAIN = REGISTER.registerStatic("isaac").iconItem("icon");

    public static ItemGroup ITEM = REGISTER.registerStatic("item").lang("Item", "道具").iconItem("item_brother_bobby");

    public static ItemGroup ENTITY = REGISTER.registerStatic("entity").lang("Entity", "实体").iconStack(() -> new ItemStack(Items.EGG));
}
