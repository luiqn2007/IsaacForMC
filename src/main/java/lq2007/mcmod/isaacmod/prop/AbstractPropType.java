package lq2007.mcmod.isaacmod.prop;

import lq2007.mcmod.isaacmod.Isaac;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class AbstractPropType {
    final ResourceLocation name;
    boolean canAddMulti, isActivate;

    public AbstractPropType(ResourceLocation name, boolean isActivate, boolean canAddMulti) {
        this.name = name;
        this.isActivate = isActivate;
        this.canAddMulti = canAddMulti;
        ModProps.propTypeMap.put(this.name, this);
    }

    AbstractPropType(boolean isActivate, boolean canAddMulti) {
        String name = getClass().getName();
        if (name.startsWith("Prop")) {
            name = name.substring(4);
        }
        StringBuilder sb = new StringBuilder(Character.toLowerCase(name.charAt(0)));
        for (int i = 1; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append('_').append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }

        this.name = new ResourceLocation(Isaac.ID, sb.toString());
        this.canAddMulti = canAddMulti;
        this.isActivate = isActivate;
        ModProps.propTypeMap.put(this.name, this);
    }

    public ResourceLocation getName() {
        return name;
    }

    public boolean canAddMulti() {
        return canAddMulti;
    }

    public boolean isActivate() {
        return isActivate;
    }

    public void onActivate(World world, PlayerEntity player, Prop prop) {

    }

    public abstract void onApply(World world, PlayerEntity player, Prop prop);

    public abstract void onRemove(World world, PlayerEntity player, Prop prop);

    public INBTSerializable<CompoundNBT> createData() {
        return EmptyPropData.INSTANCE;
    }
}
