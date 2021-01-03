package lq2007.mcmod.isaacformc.common.isaac.prop;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

public class PropItem implements INBTSerializable<CompoundNBT> {

    public static final PropItem EMPTY = new PropItem(PropTypes.EMPTY, NoData.INSTANCE);

    public final PropType type;
    public final IPropData data;

    public static PropItem fromNbt(CompoundNBT nbt) {
        if (nbt.contains("_type", Constants.NBT.TAG_STRING)) {
            PropType type = PropTypes.get(new ResourceLocation(nbt.getString("_type")), null);
            if (type != null) {
                IPropData data = type.createData();
                PropItem item = new PropItem(type, data);
                item.deserializeNBT(nbt);
                data.onBindTo(item);
                return item;
            }
        }
        return PropItem.EMPTY;
    }

    public PropItem(PropType type) {
        this.type = type;
        this.data = type.createData();
        data.onBindTo(this);
    }

    private PropItem(PropType type, IPropData data) {
        this.type = type;
        this.data = data;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("_type", type.key.toString());
        nbt.put("_data", data.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        data.deserializeNBT(nbt.getCompound("_data"));
    }
}
