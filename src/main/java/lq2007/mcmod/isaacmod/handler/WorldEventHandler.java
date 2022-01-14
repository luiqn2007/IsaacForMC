package lq2007.mcmod.isaacmod.handler;

import lq2007.mcmod.isaacmod.util.EntityUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeWorldServer;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WorldEventHandler {

    @SubscribeEvent
    public static void onWorldEnter(WorldEvent.Unload event) {
        IWorld world = event.getWorld();
        if (world == null || world instanceof IForgeWorldServer || world instanceof ClientWorld) {
            return;
        }
        if (world instanceof World) {
            EntityUtil.WORLD_ENTITY_MAP.remove(world);
        }
    }

    @SubscribeEvent
    public static void onChunkSave(ChunkDataEvent.Save event) {
        CompoundNBT chunkData = event.getData().getCompound("Level");
        if (chunkData.contains("Entities", Constants.NBT.TAG_LIST)) {
            ListNBT entities = chunkData.getList("Entities", Constants.NBT.TAG_COMPOUND);
            entities.removeIf(nbt -> {
                CompoundNBT entity = (CompoundNBT) nbt;
                // todo: remove all friends
                return false;
            });
        }
    }
}
