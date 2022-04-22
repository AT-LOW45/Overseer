package com.k.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.Arrays;
import java.util.List;

public interface ICommand {

    String getName();

    String getDescription();

    default void handle(SlashCommandEvent slashCommandEvent) {};

    default void handle(GuildMessageReceivedEvent messageReceivedEvent) {};

    default String[] parseMessageArgs(GuildMessageReceivedEvent event) {
        return Arrays.copyOfRange(
                event.getMessage().getContentRaw().split(" "),
                1,
                event.getMessage().getContentRaw().split(" ").length);
    }

}
