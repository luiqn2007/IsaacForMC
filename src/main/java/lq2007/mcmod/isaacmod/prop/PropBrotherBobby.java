package lq2007.mcmod.isaacmod.prop;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public class PropBrotherBobby extends AbstractPropType {

    PropBrotherBobby() {
        super(true, true);
    }

    @Override
    public void onApply(World world, PlayerEntity player, Prop prop) {
        if (world.isRemote) return;
        BrotherBobbyData data = prop.getDataAs();
        if (!data.isAppliedTo(player)) {

        }
    }

    @Override
    public void onRemove(World world, PlayerEntity player, Prop prop) {
        if (world.isRemote) return;
        BrotherBobbyData data = prop.getDataAs();
        if (data.isAppliedTo(player)) {

        }
    }

    @Override
    public INBTSerializable<CompoundNBT> createData() {
        return new BrotherBobbyData();
    }

    public static class BrotherBobbyData extends BasePropData {
        @Override
        public void write(CompoundNBT nbt) {

        }

        @Override
        public void read(CompoundNBT nbt) {

        }
    }
}
