package lq2007.mcmod.isaacmod.common.item;

import net.minecraft.item.Item;

public class PropItem extends Item {

    public PropItem() {
        super(new Properties()
                .group(ItemIcon.group())
                .maxStackSize(1)
                .setISTER(() -> lq2007.mcmod.isaacmod.client.ister.PropItemRender::new));
    }
}
