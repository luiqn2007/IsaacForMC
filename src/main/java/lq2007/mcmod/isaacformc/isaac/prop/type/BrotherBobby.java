package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.common.entity.EntityBobby;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.data.NoData;
import lq2007.mcmod.isaacformc.isaac.room.EnumRoom;
import net.minecraft.entity.LivingEntity;

public class BrotherBobby extends PropType<NoData> {

    public BrotherBobby(String name, boolean isActive, int id, EnumRoom... rooms) {
        super("brother_bobby", false, 8, EnumRoom.NORMAL_AND_DEVIL);
    }

    @Override
    protected NoData createData() {
        return NoData.INSTANCE;
    }

    @Override
    public void onPickup(LivingEntity entity, PropItem item, PropItem itemBeforeEvent) {
        if (!entity.world.isRemote && !) {
            EntityBobby bobby =
            entity.world.addEntity()
        }
        super.onPickup(entity, item, itemBeforeEvent);
    }
}
