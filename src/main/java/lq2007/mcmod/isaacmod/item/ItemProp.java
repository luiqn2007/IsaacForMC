package lq2007.mcmod.isaacmod.item;

import lq2007.mcmod.isaacmod.capability.ModCapabilities;
import lq2007.mcmod.isaacmod.prop.Prop;
import lq2007.mcmod.isaacmod.prop.AbstractPropType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ItemProp extends Item {

    public static final Map<AbstractPropType, ItemProp> PROP_ITEM_MAP = new HashMap<>();

    public final AbstractPropType prop;

    public ItemProp(AbstractPropType prop) {
        super(new Properties().maxStackSize(1).group(ModGroups.ITEM));
        this.prop = prop;
        PROP_ITEM_MAP.put(prop, this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (worldIn.isRemote) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        Prop prop = new Prop(this.prop);
        ItemStack item = playerIn.getHeldItem(handIn);
        CompoundNBT data = item.getChildTag("prop");
        if (data != null) {
            prop.deserializeNBT(data);
        }
        Prop back = ModCapabilities.getPlayerData(playerIn).addProp(prop);
        if (back.equals(prop)) {
            return ActionResult.resultFail(item);
        }
        if (!playerIn.isCreative()) {
            item.shrink(1);
        }
        if (!back.isEmpty()) {
            ItemStack newItem = new ItemStack(PROP_ITEM_MAP.get(back.getType()));
            newItem.getOrCreateTag().put("prop", back.serializeNBT());
            playerIn.inventory.addItemStackToInventory(newItem);
        }
        return ActionResult.resultSuccess(item);
    }
}
