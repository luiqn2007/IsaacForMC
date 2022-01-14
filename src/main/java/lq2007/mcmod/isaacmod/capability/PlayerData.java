package lq2007.mcmod.isaacmod.capability;

import lq2007.mcmod.isaacmod.prop.ModProps;
import lq2007.mcmod.isaacmod.prop.Prop;
import lq2007.mcmod.isaacmod.prop.AbstractPropType;
import lq2007.mcmod.isaacmod.util.NBTUtils;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerData implements ICapability<PlayerData> {

    private Prop currentActivateProp = Prop.EMPTY;
    private Prop secondaryActivateProp = Prop.EMPTY;
    private final List<Prop> passiveProps = new ArrayList<>();
    private final List<AbstractPropType> markedProps = new ArrayList<>();
    private boolean isSecondaryActivatePropEnabled = false;

    private final Set<AbstractPropType> passivePropTypes = new HashSet<>();

    public Prop addProp(Prop prop) {
        AbstractPropType propType = prop.getType();
        if (propType.isActivate()) {
            markedProps.add(propType);
            if (currentActivateProp.isEmpty()) {
                currentActivateProp = prop;
                return Prop.EMPTY;
            } else if (isSecondaryActivatePropEnabled) {
                if (secondaryActivateProp.isEmpty()) {
                    secondaryActivateProp = prop;
                    return Prop.EMPTY;
                } else {
                    Prop old = currentActivateProp;
                    currentActivateProp = prop;
                    return old;
                }
            } else {
                Prop old = currentActivateProp;
                currentActivateProp = prop;
                return old;
            }
        } else if (propType.canAddMulti() || !passivePropTypes.contains(propType)) {
            markedProps.add(propType);
            passiveProps.add(prop);
            passivePropTypes.add(propType);
            return Prop.EMPTY;
        }
        return prop;
    }

    public void swapActivateProp() {
        if (isSecondaryActivatePropEnabled) {
            Prop tmp = currentActivateProp;
            currentActivateProp = secondaryActivateProp;
            secondaryActivateProp = tmp;
        }
    }

    public void setSecondaryActivatePropEnabled(boolean secondaryActivatePropEnabled) {
        isSecondaryActivatePropEnabled = secondaryActivatePropEnabled;
    }

    public Prop getActivateProp(boolean current) {
        return current ? currentActivateProp : secondaryActivateProp;
    }

    public boolean containsMark(AbstractPropType type) {
        return markedProps.contains(type);
    }

    @Override
    public Capability<PlayerData> getCapability() {
        return ModCapabilities.CAP_PLAYER_DATA;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("primaryActivatedProp", currentActivateProp.serializeNBT());
        nbt.put("secondaryActivateProp", secondaryActivateProp.serializeNBT());
        nbt.put("passiveProps", NBTUtils.write(passiveProps, Prop::serializeNBT));
        nbt.put("markedProps", NBTUtils.write(markedProps, p -> StringNBT.valueOf(p.getName().toString())));
        nbt.putBoolean("secondaryActivateEnabled", isSecondaryActivatePropEnabled);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        currentActivateProp = Prop.fromNbt(nbt.getCompound("primaryActivatedProp"));
        secondaryActivateProp = Prop.fromNbt(nbt.getCompound("secondaryActivateProp"));
        NBTUtils.read(nbt.getList("passiveProps", Constants.NBT.TAG_COMPOUND), passiveProps, Prop::fromNbt);
        NBTUtils.read(nbt.getList("markedProps", Constants.NBT.TAG_STRING), markedProps, n -> ModProps.get(new ResourceLocation(n.getString())));
        isSecondaryActivatePropEnabled = nbt.getBoolean("secondaryActivateEnabled");

        passivePropTypes.clear();
        passiveProps.stream().map(Prop::getType).distinct().forEach(passivePropTypes::add);
    }
}
