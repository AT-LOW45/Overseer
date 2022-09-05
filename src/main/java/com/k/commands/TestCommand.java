package com.k.commands;

import com.k.Channels;
import com.k.Overseer;
import com.k.modules.polly.VoteOptions;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class TestCommand implements IMessageCommand {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void handle(MessageReceivedEvent messageReceivedEvent) {
//        CompletableFuture<Message> completableFuture = messageReceivedEvent.getChannel().sendMessage("hi")
//                .submit();

//
//        completableFuture.thenApply(message -> RestAction.allOf(
//                message.addReaction(Emoji.fromUnicode(VoteOptions.OPTION9.getEmote())),
//                message.addReaction(Emoji.fromUnicode(VoteOptions.OPTION1.getEmote())))).join().queue();


        messageReceivedEvent.getChannel().sendMessage("d").queue(message -> {
            Arrays.stream(VoteOptions.values()).forEach(voteOptions -> {
                message.addReaction(Emoji.fromUnicode(voteOptions.getEmote())).queue();
            });
        });

        Objects.requireNonNull(Overseer.getJDAinstance().getUserById("812271611997978676"))
                .openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("heyo").queue());

        Objects.requireNonNull(Overseer.getJDAinstance().getTextChannelById(Channels.POLL.getChannelId()))
                .sendMessage("test").queue();
    }
}
