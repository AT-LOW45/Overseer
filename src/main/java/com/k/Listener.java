package com.k;

import org.jetbrains.annotations.NotNull;

import com.k.commands.CommandManager;
import com.k.reactions.ReactionHandler;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class Listener extends ListenerAdapter {

    private final CommandManager commandManager;
    private final ReactionHandler reactionHandler = new ReactionHandler();

    @Autowired
    public Listener(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println("Overseer is ready");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] command = event.getMessage().getContentRaw().split(" ");

        if (command[0].startsWith("!") && !event.getAuthor().isBot()) {
            commandManager.getMessageCommand(event, command[0].substring(1));
        }   
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getUser().isBot()) {
            this.commandManager.getSlashCommand(event);
        }
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (!Objects.requireNonNull(event.getUser()).isBot()) {
            reactionHandler.handle(event);
        } else if (event.getUser().getIdLong() != 937254303347900447L) {
            reactionHandler.reject(event);
        }
    }
}
