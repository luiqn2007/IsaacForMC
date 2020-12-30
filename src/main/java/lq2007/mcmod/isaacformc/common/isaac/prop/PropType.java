package lq2007.mcmod.isaacformc.common.isaac.prop;

import lq2007.mcmod.isaacformc.Isaac;
import lq2007.mcmod.isaacformc.common.isaac.IsaacItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public abstract class PropType extends IsaacItem {

    public PropType() {
        super(new ResourceLocation(Isaac.ID, "empty"));
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    public boolean onActive(PlayerEntity player, PropItem prop) {
        return false;
    }

    public void onPickUp(PlayerEntity player, PropItem prop) {

    }

    public PropItem read(CompoundNBT data, PropItem item) {
        return item;
    }

    public CompoundNBT write(PropItem item, CompoundNBT data) {
        data.putString("type", key.toString());
        return data;
    }
}
