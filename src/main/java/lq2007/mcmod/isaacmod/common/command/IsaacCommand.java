package lq2007.mcmod.isaacmod.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;

import java.util.function.Predicate;

/**
 * https://isaac.huijiwiki.com/wiki/%E6%8E%A7%E5%88%B6%E5%8F%B0
 */
public class IsaacCommand implements ICommandProvider, Command<CommandSource>, Predicate<CommandSource> {

    @Override
    public LiteralArgumentBuilder<CommandSource> getCommand() {
        return Commands.literal("isaac")
                .requires(this)
                .executes(this);
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        return 0;
    }

    @Override
    public boolean test(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(2) && commandSource.getEntity() instanceof PlayerEntity;
    }
}
