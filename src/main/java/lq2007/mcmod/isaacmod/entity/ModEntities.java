package lq2007.mcmod.isaacmod.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModEntities {

    public static final RegisterEntity REGISTER = new RegisterEntity();

    public static final EntityType<?> EMPTY = EntityType.Builder.create(EntityClassification.MISC).build("empty");

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
    }
}
