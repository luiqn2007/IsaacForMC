package lq2007.mcmod.isaacmod.prop;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class PropEmpty extends AbstractPropType {

    public static final PropEmpty INSTANCE = new PropEmpty();

    PropEmpty() {
        super(false, false);
    }

    @Override
    public void onApply(World world, PlayerEntity player, Prop prop) {

    }

    @Override
    public void onRemove(World world, PlayerEntity player, Prop prop) {

    }
}
