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
        registerCommand(new HelpCommand(this));
        registerCommand(new PopCultureTriviaCommand());
    }

    public Optional<ICommand> findCommand(String name) {
        return Optional.of(commands).get()
                .stream()
                .filter(iCommand -> iCommand.getName().equals(name))
                .findFirst();
    }

    public Set<ICommand> getRegisteredCommands() {
        return commands;
    }

    public void getSlashCommand(SlashCommandEvent event) {
        Optional<ICommand> slashCommand = findCommand(event.getName());

        slashCommand.ifPresentOrElse(iCommand -> iCommand.handle(event),
                () -> event.reply("Something went wrong").queue());

    }

    public void getMessageCommand(GuildMessageReceivedEvent event, String command) {
        Optional<ICommand> messageCommand = findCommand(command);

        messageCommand.ifPresentOrElse(iCommand -> iCommand.handle(event),
                () -> event.getChannel().sendMessage("Command not registered").queue());

    }


    private void registerCommand(ICommand slashCommand) {
        commands.add(slashCommand);
    }
}
