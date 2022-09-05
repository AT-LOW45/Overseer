package com.k.modules.polly.interactions;

import com.k.commands.IMessageCommand;
import com.k.commands.ISlashCommand;
import com.k.modules.polly.Poll;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@Component
public class PollCommand implements ISlashCommand {

    private final DefaultListableBeanFactory beanFactory;
    private SlashCommandInteractionEvent event;

    @Autowired
    public PollCommand(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public String getName() {
        return "poll";
    }

    @Override
    public String getDescription() {
        return "starts a poll if there isn't an ongoing one";
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        this.event = event;
        int duration = Integer.parseInt(Objects.requireNonNull(event.getOption("duration")).getAsString());
        boolean anonymity = Boolean.parseBoolean(
                Objects.requireNonNull(event.getOption("anonymous")).getAsString());
        String title = Objects.requireNonNull(event.getOption("title")).getAsString();

        for (int index = 3; index < event.getOptions().size(); index++) {
            if (index > 3) {
                if (event.getOptions().get(index).getAsString()
                        .equals(event.getOptions().get(index - 1).getAsString())) {
                    event.reply("Unable to initiate a poll because some options are identical. Please " +
                            "ensure that all provided options are unique").queue();
                    return;
                }
            }
        }



        List<String> options = new ArrayList<>();


        event.getOptions().stream().skip(3).forEach(optionMapping -> {
            options.add(optionMapping.getAsString());
        });


        if (!beanFactory.containsSingleton("poll")) {

            beanFactory.registerSingleton("poll", Poll.builder()
                    .pollTitle(title)
                    .anonymity(anonymity)
                    .pollInitiator(event.getUser())
                    .timeInMinutes(duration)
                    .options(options)
                    .pollInitCommand(event)
                    .build());


            beanFactory.autowireBean(beanFactory.getBean("poll"));
            event.reply("The poll will begin shortly.").queue();
            ((Poll) beanFactory.getBean("poll")).buildPoll();

        } else {
            Poll currentPoll = (Poll) beanFactory.getBean("poll");

            event.reply("You are not allowed to start a new poll while there is an ongoing one").queue();
            event.getChannel()
                    .sendMessage("Poll " + currentPoll.getPoll_id() + " is currently taking place")
                    .setMessageReference(currentPoll.getPoll_id()).queueAfter(2, TimeUnit.SECONDS);
        }
    }

    @Override
    public String[] parseOptions() {
        int duration = Integer.parseInt(Objects.requireNonNull(event.getOption("duration")).getAsString());
        boolean anonymity = Boolean.parseBoolean(
                Objects.requireNonNull(event.getOption("anonymous")).getAsString());
        String title = Objects.requireNonNull(event.getOption("title")).getAsString();

        for (int index = 3; index < event.getOptions().size(); index++) {
            if (index > 3) {
                if (event.getOptions().get(index).getAsString()
                        .equals(event.getOptions().get(index - 1).getAsString())) {
                    event.reply("Unable to initiate a poll because some options are identical. Please " +
                            "ensure that all provided options are unique").queue();
                    return null;
                }
            }
        }
        return new String[] {};
    }

}

