package lq2007.mcmod.isaacmod.common.network;

import lq2007.mcmod.isaacmod.Isaac;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public abstract class BasePacket {

    /**
     * Send to players track the entity.
     * @param entity entity is tracked
     */
    @OnlyIn(Dist.DEDICATED_SERVER)
    public void sendToTracker(Entity entity) {
        if (!entity.world.isRemote) {
            sendToTracker(new ChunkPos(entity.chunkCoordX, entity.chunkCoordZ), (ServerWorld) entity.world);
        }
    }

    /**
     * Send to players track the position
     * @param pos pos
     * @param world world
     */
    @OnlyIn(Dist.DEDICATED_SERVER)
    public void sendToTracker(BlockPos pos, World world) {
        if (!world.isRemote) {
            sendToTracker(new ChunkPos(pos), (ServerWorld) world);
        }
    }

    /**
     * Send to players track the chunk
     * @param pos chunk pos
     * @param world world
     */
    @OnlyIn(Dist.DEDICATED_SERVER)
    public void sendToTracker(ChunkPos pos, ServerWorld world) {
        world.getChunkProvider().chunkManager.getTrackingPlayers(pos, false).forEach(this::sendTo);
    }

    /**
     * Send to all players in the server.
     */
    @OnlyIn(Dist.DEDICATED_SERVER)
    public void sendToAllPlayers() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            server.getPlayerList().getPlayers().forEach(this::sendTo);
        }
    }

    /**
     * Send to all players in the world.
     * @param world world
     */
    @OnlyIn(Dist.DEDICATED_SERVER)
    public void sendToAllPlayers(World world) {
        if (!world.isRemote) {
            ServerWorld sw = (ServerWorld) world;
            sw.getPlayers().forEach(this::sendTo);
        }
    }

    /**
     * Send to all players in the world (dimension).
     * @param dimension dimension
     */
    @OnlyIn(Dist.DEDICATED_SERVER)
    public void sendToAllPlayers(RegistryKey<World> dimension) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            ServerWorld world = server.getWorld(dimension);
            if (world != null) {
                sendToAllPlayers(world);
            }
        }
    }

    /**
     * Send to all players in the world (dimension).
     * @param dimension dimension
     */
    @OnlyIn(Dist.DEDICATED_SERVER)
    public void sendToAllPlayers(DimensionType dimension) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            for (ServerWorld world : server.getWorlds()) {
                if (world.getDimensionType() == dimension) {
                    sendToAllPlayers(world);
                    break;
                }
            }
        }
    }

    /**
     * Send to client player.
     * @param player player
     */
    @OnlyIn(Dist.DEDICATED_SERVER)
    public void sendTo(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            Isaac.NETWORKS.CHANNEL.sendTo(this, ((ServerPlayerEntity) player).connection.netManager, NetworkDirection.PLAY_TO_SERVER);
        }
    }

    /**
     * Send to server.
     */
    @OnlyIn(Dist.CLIENT)
    public void sendToServer() {
        Isaac.NETWORKS.CHANNEL.sendToServer(this);
    }

    /**
     * Get the player.
     * If in client, it return the player, else it return sender.
     * If in client and no world, return null.
     * @return player. If no world, it return null.
     */
    public PlayerEntity getPlayer(NetworkEvent.Context context) {
        if (context.getDirection().getReceptionSide().isClient()) {
            return net.minecraft.client.Minecraft.getInstance().player;
        } else {
            return context.getSender();
        }
    }
}
