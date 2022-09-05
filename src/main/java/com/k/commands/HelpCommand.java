package com.k.commands;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements IMessageCommand {

    private final ApplicationContext applicationContext;
    private StringBuilder stringBuilder;

    @Autowired
    public HelpCommand(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        System.out.println("help");
    }

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
    public void handle(MessageReceivedEvent messageReceivedEvent) {

        String[] args = parseMessageArgs(messageReceivedEvent);

        if (args.length == 0) {
            // display help all available commands (message commands + slash commands)

        } else {
            // display help for specified command (if found)
            stringBuilder = new StringBuilder();
            Optional<IMessageCommand> targetCommand =
                    applicationContext.getBean(CommandManager.class).findMessageCommand(args[0]);



            Map<String, String> commandUnion = Map.of();


            targetCommand.ifPresentOrElse(iCommand -> {
                stringBuilder.append(iCommand.getName())
                        .append(" ~ ")
                        .append(iCommand.getDescription());
                messageReceivedEvent.getChannel().sendMessage(stringBuilder.toString()).queue();
            }, () -> messageReceivedEvent.getChannel().sendMessage("This command does not exist").queue());

        }
    }

    @Override
    public String[] parseMessageArgs(MessageReceivedEvent event) {
        return IMessageCommand.super.parseMessageArgs(event);
    }
}
