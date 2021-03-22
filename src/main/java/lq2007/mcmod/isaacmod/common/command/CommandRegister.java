package lq2007.mcmod.isaacmod.common.command;

import com.mojang.brigadier.CommandDispatcher;
import lq2007.mcmod.isaacmod.register.IAutoApply;
import lq2007.mcmod.isaacmod.register.registers.IRegister;
import net.minecraft.command.CommandSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.objectweb.asm.Type;

import java.util.HashSet;
import java.util.Set;

import static lq2007.mcmod.isaacmod.Isaac.LOGGER;

public class CommandRegister implements IRegister, IAutoApply {

    private final Set<Class<? extends ICommandProvider>> commands = new HashSet<>();

    @Override
    public void cache(ClassLoader classLoader, Type clazz, String className, String packageName, Class<?> aClass) {
        if (isExtends(aClass, ICommandProvider.class) && isInstantiable(aClass)) {
            LOGGER.warn("\tCached as Command");
            commands.add((Class<? extends ICommandProvider>) aClass);
        }
    }

    @Override
    public void apply() {
        if (!commands.isEmpty()) MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void apply(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        for (Class<? extends ICommandProvider> command : commands) {
            try {
                LOGGER.warn("Isaac.command {}", command);
                dispatcher.register(command.newInstance().getCommand());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
