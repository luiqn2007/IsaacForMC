package lq2007.mcmod.isaacformc.isaac.prop;

import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

public interface IItemUseType {

    default boolean cancelEntityUseItem(EnumItemUseStage stage, LivingEntity entity, ItemStack stack, int duration) {
        return false;
    }

    default int onEntityItemUse(PropItem item, EnumItemUseStage stage, LivingEntity entity, ItemStack stack, int duration) {
        switch (stage) {
            case START: return onEntityStartUseItem(item, entity, stack, duration);
            case TICK: return onEntityUsingItem(item, entity, stack, duration);
            case STOP: return onEntityStopUseItem(item, entity, stack, duration);
            case FINISH: return onEntityFinishUseItem(item, entity, stack, duration);
            default: return duration;
        }
    }

    default int onEntityStartUseItem(PropItem item, LivingEntity entity, ItemStack stack, int duration) {
        return duration;
    }

    default int onEntityUsingItem(PropItem item, LivingEntity entity, ItemStack stack, int duration) {
        return duration;
    }

    default int onEntityStopUseItem(PropItem item, LivingEntity entity, ItemStack stack, int duration) {
        return duration;
    }

    default int onEntityFinishUseItem(PropItem item, LivingEntity entity, ItemStack stack, int duration) {
        return duration;
    }

    enum EnumItemUseStage {
        START, TICK, STOP, FINISH;

        public static EnumItemUseStage fromEvent(LivingEntityUseItemEvent event) {
            if (event instanceof LivingEntityUseItemEvent.Start) return START;
            else if (event instanceof LivingEntityUseItemEvent.Tick) return TICK;
            else if (event instanceof LivingEntityUseItemEvent.Stop) return STOP;
            else return FINISH;
        }
    }
}
