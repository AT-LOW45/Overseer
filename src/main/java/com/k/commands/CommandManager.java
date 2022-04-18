package com.k.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CommandManager {

    private final Set<ICommand> commands = new HashSet<>();

    public CommandManager() {
        registerCommand(new PollCommand());
        registerCommand(new EndPollCommand());
        registerCommand(new MemeCommand(new ObjectMapper()));
    }

    private ICommand findCommand(String name) {
        return commands.stream()
                .filter(sCommand -> sCommand.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void getSlashCommand(SlashCommandEvent event) {
        ICommand slashCommand = findCommand(event.getName());

        if (slashCommand != null) {
            slashCommand.handle(event);
        } else {
            event.reply("Something went wrong. Please try again later").queue();
        }
    }

    public void getMessageCommand(GuildMessageReceivedEvent event, String command) {
        ICommand messageCommand = findCommand(command);

        if(messageCommand != null) {
            messageCommand.handle(event);
        } else {
            event.getChannel().sendMessage("No such command").queue();
        }
    }


    private void registerCommand(ICommand slashCommand) {
        commands.add(slashCommand);
    }
}
