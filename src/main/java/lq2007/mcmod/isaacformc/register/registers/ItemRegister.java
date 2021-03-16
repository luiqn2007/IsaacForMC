package lq2007.mcmod.isaacformc.register.registers;

import lq2007.mcmod.isaacformc.register.Register;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegister extends BaseDeferredRegister<Item, Item> {

    public ItemRegister(Register context, String packageName) {
        super(ForgeRegistries.ITEMS, context, Item.class, packageName);
    }
}