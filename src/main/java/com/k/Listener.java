package com.k;

import com.k.commands.CommandManager;
import com.k.reactions.ReactionHandler;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Listener extends ListenerAdapter {

    private final CommandManager commandManager = new CommandManager();
    private final ReactionHandler reactionHandler = new ReactionHandler();


    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println("Overseer is ready");
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] command = event.getMessage().getContentRaw().split(" ");


        if(command[0].startsWith("!") && !event.getAuthor().isBot()) {
            commandManager.getMessageCommand(event, command[0].substring(1));
        }
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {

        if(!event.getUser().isBot()) {
            this.commandManager.getSlashCommand(event);
        }
    }


    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {

        if (!event.getUser().isBot()) {
            reactionHandler.handle(event);
        } else if (event.getUser().getIdLong() != 937254303347900447L) {
            reactionHandler.reject(event);
        }

    }


}
