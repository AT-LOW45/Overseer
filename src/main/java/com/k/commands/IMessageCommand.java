package com.k.commands;

import java.util.Arrays;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;


public interface IMessageCommand {

    String getName();

    String getDescription();

    void handle(MessageReceivedEvent messageReceivedEvent);

    default String[] parseMessageArgs(MessageReceivedEvent event) {
        return Arrays.copyOfRange(
                event.getMessage().getContentRaw().split(" "),
                1,
                event.getMessage().getContentRaw().split(" ").length);
    }

}
