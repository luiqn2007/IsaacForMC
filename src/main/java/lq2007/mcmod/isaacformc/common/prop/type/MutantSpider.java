package lq2007.mcmod.isaacformc.common.prop.type;

import lq2007.mcmod.isaacformc.common.prop.data.NoData;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;

public class MutantSpider extends AbstractPropType implements TypeGroups.ShootDelay0 {

    public MutantSpider(String name, boolean isActive, int id, EnumPropPools... rooms) {
        super(name, isActive, id, rooms);
    }

    @Override
    protected NoData createData() {
        return NoData.INSTANCE;
    }
}
