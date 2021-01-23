package lq2007.mcmod.isaacformc.common.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static lq2007.mcmod.isaacformc.common.Isaac.ID;

public class Entities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ID);

    public static final RegistryObject<EntityType<EntityBobby>> TYPE_BOBBY = ENTITIES.register("isaac.babby", () -> EntityType.Builder
            .create(EntityBobby::new, EntityClassification.MISC)
            .immuneToFire()
            .disableSummoning()
            // todo size etc.
            .build("isaac.babby"));

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
