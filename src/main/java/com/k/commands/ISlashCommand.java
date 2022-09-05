package com.k.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.List;

public interface ISlashCommand {

    String getName();
    String getDescription();
    void handle(SlashCommandInteractionEvent event);
    String[] parseOptions();
}
