package lq2007.mcmod.isaacformc.common.capability.data;

import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacformc.common.util.NBTUtils;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.LinkedList;

public class PropRecord implements INBTSerializable<CompoundNBT> {

    private final AbstractPropType type;
    private final LinkedList<Prop> props = new LinkedList<>();

    private boolean noPicked = true;

    public PropRecord(AbstractPropType type) {
        this.type = type;
    }

    public PropRecord(AbstractPropType type, Prop prop) {
        this(type);
        add(prop);
    }

    @Nullable
    public Prop add(Prop prop) {
        if (prop == Prop.EMPTY) {
            return null;
        }
        Prop removed = null;
        if (noPicked) {
            noPicked = false;
        } else if (type.isExclusive()) {
            removed = props.pollLast();
            props.clear();
        }
        props.add(prop);
        return removed;
    }

    @Nullable
    public Prop remove(boolean removeRecord) {
        if (!noPicked) {
            Prop prop = props.pollLast();
            if (removeRecord && props.isEmpty()) {
                noPicked = true;
            }
            return prop;
        }
        return null;
    }

    public void clear(boolean removeRecord) {
        if (!noPicked) {
            props.clear();
            if (removeRecord) {
                noPicked = true;
            }
        }
    }

    public boolean isEmpty() {
        return noPicked;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("_noPicked", noPicked);
        if (!noPicked && !props.isEmpty()) {
            nbt.put("_props", NBTUtils.convert(props, Prop::serializeNBT));
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        noPicked = nbt.getBoolean("_noPicked");
        if (!noPicked && nbt.contains("_props", Constants.NBT.TAG_LIST)) {
            NBTUtils.convert(nbt.getList("_props", Constants.NBT.TAG_COMPOUND), props, Prop::fromNbt);
        } else {
            props.clear();
        }
    }
}
