package lq2007.mcmod.isaacmod.capability;

import lq2007.mcmod.isaacmod.capability.empty.EmptyPlayerData;
import lq2007.mcmod.isaacmod.capability.storage.NBTStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class ModCapabilities {

    @CapabilityInject(PlayerData.class)
    public static Capability<PlayerData> CAP_PLAYER_DATA;

    public static void register() {
        CapabilityManager.INSTANCE.register(PlayerData.class, NBTStorage.get(), PlayerData::new);
    }

    public static PlayerData getPlayerData(PlayerEntity player) {
        return getCapability(player, CAP_PLAYER_DATA, EmptyPlayerData.INSTANCE);
    }

    public static <C extends ICapability<C>> C getCapability(ICapabilityProvider provider, Capability<C> capability, C emptyCap) {
        return provider.getCapability(capability).orElse(emptyCap);
    }

    public static <C extends ICapability<C>> C getCapability(ICapabilityProvider provider, Capability<C> capability, @Nullable Direction direction, C emptyCap) {
        return provider.getCapability(capability, direction).orElse(emptyCap);
    }
}
