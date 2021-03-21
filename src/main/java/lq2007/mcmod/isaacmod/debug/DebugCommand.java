package lq2007.mcmod.isaacmod.debug;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import lq2007.mcmod.isaacmod.Isaac;
import lq2007.mcmod.isaacmod.common.command.ICommandProvider;
import lq2007.mcmod.isaacmod.common.prop.Prop;
import lq2007.mcmod.isaacmod.common.prop.type.IsaacProps;
import lq2007.mcmod.isaacmod.common.prop.type.ab.BrotherBobby;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class DebugCommand implements ICommandProvider, SuggestionProvider<CommandSource>, Command<CommandSource> {

    @Override
    public LiteralArgumentBuilder<CommandSource> getCommand() {
        RequiredArgumentBuilder<CommandSource, String> params = Commands
                .argument("params", StringArgumentType.greedyString())
                .suggests(this).executes(this);
        return Commands.literal("isaacDebug").then(params);
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        String[] params = context.getArgument("params", String.class)
                .trim()
                .toLowerCase(Locale.ROOT)
                .split("\\.");
        String command = params[0];
        String[] parameters = ArrayUtils.remove(params, 0);
        execute(context.getSource().asPlayer(), command, parameters);
        return 0;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        builder.suggest("[+/-]bobby");
        return builder.buildFuture();
    }

    private void execute(PlayerEntity player, String command, String[] parameters) {
        switch (command) {
            case "bobby":
            case "+bobby": {
                BrotherBobby bobby = IsaacProps.BROTHER_BOBBY;
                Prop prop = new Prop(bobby);
                if (bobby.onPickup(player, prop)) {
                    Isaac.CAPABILITIES.getProps(player).pickup(prop);
                }
                break;
            }
            case "-bobby": {
                BrotherBobby bobby = IsaacProps.BROTHER_BOBBY;
                Prop prop = Isaac.CAPABILITIES.getProps(player).getProp(bobby);
                if (prop != Prop.EMPTY && bobby.onRemove(player, prop, true)) {
                    Isaac.CAPABILITIES.getProps(player).remove(prop, true);
                }
                break;
            }
        }
    }
}
