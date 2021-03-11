package lq2007.mcmod.isaacformc.common.util.serializer;

import lq2007.mcmod.isaacformc.common.util.serializer.network.IPacketSerializable;
import lq2007.mcmod.isaacformc.common.util.serializer.network.IPacketSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.eventbus.ASMEventHandler;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.forgespi.language.IModInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ObjectPacketSerializer<T extends IPacketSerializable> implements IPacketSerializer<T> {

    public static final ObjectPacketSerializer INSTANCE = new ObjectPacketSerializer();
    public static final ArrayList<String> CLASS_LIST = new ArrayList<>();

    public static void collectClass() {
        ModContainer container = ModLoadingContext.get().getActiveContainer();
        if (container instanceof FMLModContainer) {
            container.scanResults;
        }
    }

    @Override
    public T read(PacketBuffer buffer) {
        Class<T> aClass = (Class<T>) ClassSerializer.INSTANCE.read(buffer);
        try {
            T t = aClass.newInstance();
            t.read(buffer);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PacketBuffer write(T item, PacketBuffer buffer) {
        ClassSerializer.INSTANCE.write(item.getClass(), buffer);
        item.write(buffer);
        return buffer;
    }
}
