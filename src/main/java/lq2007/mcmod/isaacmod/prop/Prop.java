package lq2007.mcmod.isaacmod.prop;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Optional;

public class Prop implements INBTSerializable<CompoundNBT> {

    public static final Prop EMPTY = new Prop(ModProps.EMPTY);

    private final AbstractPropType type;
    private final INBTSerializable<CompoundNBT> data;

    public static Prop fromNbt(INBT nbt) {
        if (nbt instanceof CompoundNBT) {
            AbstractPropType type = ModProps.getNullable(new ResourceLocation(((CompoundNBT) nbt).getString("type")));
            if (type == ModProps.EMPTY) {
                return EMPTY;
            } else if (type != null) {
                Prop prop = new Prop(type);
                prop.deserializeNBT((CompoundNBT) nbt);
                return prop;
            }
        }
        return new Prop(ModProps.EMPTY);
    }

    public Prop(AbstractPropType type) {
        this.type = type;
        this.data = type.createData();
    }

    public AbstractPropType getType() {
        return type;
    }

    public INBTSerializable<CompoundNBT> getData() {
        return data;
    }

    public <T extends INBTSerializable<CompoundNBT>> T getDataAs() {
        return (T) data;
    }

    public <T extends INBTSerializable<CompoundNBT>> Optional<T> getDataAs(Class<T> type) {
        if (type.isInstance(data)) {
            return Optional.of((T) data);
        }
        return Optional.empty();
    }

    public boolean isEmpty() {
        return type == ModProps.EMPTY;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("type", type.getName().toString());
        nbt.put("typeData", data.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        data.deserializeNBT(nbt.getCompound("typeData"));
    }
}
