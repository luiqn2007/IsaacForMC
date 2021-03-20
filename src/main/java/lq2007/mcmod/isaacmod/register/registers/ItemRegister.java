package lq2007.mcmod.isaacmod.register.registers;

import lq2007.mcmod.isaacmod.register.Register;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegister extends BaseDeferredRegister<Item, Item> {

    public ItemRegister(Register context, String packageName) {
        super(ForgeRegistries.ITEMS, context, packageName);
    }

    public ItemRegister(Register context) {
        super(ForgeRegistries.ITEMS, context);
    }
}
