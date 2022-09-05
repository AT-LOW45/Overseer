package com.k.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class CommandManager {

    private final Set<IMessageCommand> commands;
    private final Set<ISlashCommand> slashCommands;

    @Autowired
    public CommandManager(Set<IMessageCommand> commands, Set<ISlashCommand> slashCommands) {
        this.commands = commands;
        this.slashCommands = slashCommands;
    }

    public Optional<IMessageCommand> findMessageCommand(String name) {
        return Optional.of(commands).get()
                .stream()
                .filter(iCommand -> iCommand.getName().equals(name))
                .findFirst();
    }

    public Optional<ISlashCommand> findSlashCommand(String name) {
        return Optional.of(slashCommands).get()
                .stream()
                .filter(iCommand -> iCommand.getName().equals(name))
                .findFirst();
    }

    public Set<IMessageCommand> getRegisteredMessageCommands() {
        return this.commands;
    }

    public Set<ISlashCommand> getRegisteredSlashCommands() {
        return this.slashCommands;
    }

    public void getSlashCommand(SlashCommandInteractionEvent event) {
        Optional<ISlashCommand> slashCommand = findSlashCommand(event.getName());

        slashCommand.ifPresentOrElse(iCommand -> iCommand.handle(event),
                () -> event.reply("Something went wrong").queue());

    }

    public void getMessageCommand(MessageReceivedEvent event, String command) {
        Optional<IMessageCommand> messageCommand = findMessageCommand(command);

        messageCommand.ifPresentOrElse(iCommand -> iCommand.handle(event),
                () -> event.getChannel().sendMessage("Command not registered").queue());

    }
}
