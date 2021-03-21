package lq2007.mcmod.isaacmod.common.util.serializer;

import lq2007.mcmod.isaacmod.common.util.ReflectionUtil;
import lq2007.mcmod.isaacmod.common.util.serializer.buffer.IPacketReadable;
import lq2007.mcmod.isaacmod.common.util.serializer.buffer.IPacketSerializable;
import lq2007.mcmod.isaacmod.common.util.serializer.buffer.IPacketSerializer;
import lq2007.mcmod.isaacmod.common.util.serializer.buffer.IPacketWriteable;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class ObjectPacketSerializer<T extends IPacketSerializable> implements IPacketSerializer<T> {

    public static final ObjectPacketSerializer INSTANCE = new ObjectPacketSerializer();
    public static final ArrayList<Class<?>> CLASS_LIST = new ArrayList<>();

    public static synchronized void collectClass() {
        if (!CLASS_LIST.isEmpty()) return;
        ModContainer container = ModLoadingContext.get().getActiveContainer();
        Set<Class<?>> set = new HashSet<>();
        IModFileInfo owningFile = container.getModInfo().getOwningFile();
        if (owningFile instanceof ModFileInfo) {
            ModFileScanData data = ((ModFileInfo) owningFile).getFile().getScanResult();
            for (ModFileScanData.ClassData classData : data.getClasses()) {
                Type clazz = ReflectionUtil.get(ModFileScanData.ClassData.class, classData, "clazz");
                try {
                    Class<?> aClass = ObjectPacketSerializer.class.getClassLoader().loadClass(clazz.getClassName());
                    if (aClass.isInterface() || aClass.isAnnotation() || aClass.isEnum() || aClass.isAnonymousClass()) continue;
                    if (Modifier.isAbstract(aClass.getModifiers())) continue;
                    if (IPacketReadable.class.isAssignableFrom(aClass) || IPacketWriteable.class.isAssignableFrom(aClass)) {
                        set.add(aClass);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        CLASS_LIST.addAll(set);
        CLASS_LIST.sort(Comparator.comparing(Class::getName));
    }

    @Override
    public T read(PacketBuffer buffer) {
        try {
            Class<T> aClass = (Class<T>) CLASS_LIST.get(buffer.readVarInt());
            T t = aClass.newInstance();
            t.read(buffer);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PacketBuffer write(T item, PacketBuffer buffer) {
        buffer.writeVarInt(CLASS_LIST.indexOf(item.getClass()));
        item.write(buffer);
        return buffer;
    }
}