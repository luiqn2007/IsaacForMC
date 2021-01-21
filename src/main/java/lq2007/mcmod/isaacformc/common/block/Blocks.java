package lq2007.mcmod.isaacformc.common.block;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static lq2007.mcmod.isaacformc.common.Isaac.*;

public class Blocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ID);

    public static final RegistryObject<BlockFoundation> FOUNDATION = BLOCKS.register("foundation", BlockFoundation::new);
    public static final RegistryObject<TileEntityType<TileFoundation>> TYPE_FOUNDATION = TILE_ENTITIES.register("foundation", () -> TileEntityType.Builder
            .create(TileFoundation::new, FOUNDATION.get())
            .build(null));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        TILE_ENTITIES.register(bus);
    }
}
