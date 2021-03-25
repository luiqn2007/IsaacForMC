package lq2007.mcmod.isaacmod.common.item;

import lq2007.mcmod.isaacmod.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacmod.register.Appoint;
import net.minecraft.item.Item;

@Appoint
public class PropItem extends Item {

    private AbstractPropType type;

    public PropItem(AbstractPropType type) {
        super(new Properties().group(ItemIcon.group()).maxStackSize(1));
    }
}
