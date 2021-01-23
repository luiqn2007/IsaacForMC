package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.data.IPropData;
import lq2007.mcmod.isaacformc.isaac.prop.data.NoData;
import lq2007.mcmod.isaacformc.isaac.room.EnumRoom;
import net.minecraft.util.ResourceLocation;

public class Polyphemus extends PropType<NoData> implements TypeGroups.ShootDelay0 {

    public Polyphemus(ResourceLocation key, boolean isActive, EnumRoom... rooms) {
        super(key, isActive, rooms);
    }

    @Override
    protected NoData createData() {
        return null;
    }
}
