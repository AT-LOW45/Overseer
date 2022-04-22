package com.k.commands;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Optional;

@RequiredArgsConstructor
public class HelpCommand implements ICommand{

    private final CommandManager commandManager;
    private StringBuilder stringBuilder;

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "retrieves a list of registered commands and their descriptions, or a particular command if one is " +
                "provided";
    }

    @Override
    public void handle(GuildMessageReceivedEvent messageReceivedEvent) {

        String[] args = parseMessageArgs(messageReceivedEvent);

        if(args.length == 0) {


        } else {
            stringBuilder = new StringBuilder();
            Optional<ICommand> targetCommand = commandManager.findCommand(args[0]);

            targetCommand.ifPresentOrElse(iCommand -> {
                stringBuilder.append(iCommand.getName())
                        .append(" ~ ")
                        .append(iCommand.getDescription());
                messageReceivedEvent.getChannel().sendMessage(stringBuilder.toString()).queue();
            }, () -> messageReceivedEvent.getChannel().sendMessage("This command does not exist").queue());

        }
    }

    @Override
    public String[] parseMessageArgs(GuildMessageReceivedEvent event) {
        return ICommand.super.parseMessageArgs(event);
    }
}
