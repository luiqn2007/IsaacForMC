package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.data.NoData;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;

// https://isaac.huijiwiki.com/wiki/%E7%B2%AA%E8%87%AD%E7%B4%A0
public class Skatole extends PropType<NoData> {

    public Skatole(String name, boolean isActive, int id, EnumPropPools... rooms) {
        super(name, isActive, id, rooms);
    }

    @Override
    protected NoData createData() {
        return NoData.INSTANCE;
    }
}
