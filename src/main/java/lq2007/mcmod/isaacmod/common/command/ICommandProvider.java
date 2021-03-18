package lq2007.mcmod.isaacmod.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;

public interface ICommandProvider {

    LiteralArgumentBuilder<CommandSource> getCommand();
}
