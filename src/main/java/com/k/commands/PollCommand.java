package com.k.commands;

import com.k.entities.polly.Poll;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PollCommand implements ICommand {

    @Override
    public String getName() {
        return "poll";
    }

    @Override
    public String getDescription() {
        return "starts a poll if there isn't an ongoing one";
    }


    @Override
    public void handle(SlashCommandEvent event) {

        int duration = Integer.parseInt(Objects.requireNonNull(event.getOption("duration")).getAsString());

//        event.getJDA().getTextChannelById(event.getChannel().getId()).sendMessage("ff").queue(message -> RestAction.allOf(message.addReaction("🍔"), message.addReaction("🍎")).queue());
//        parseOptions(event.getOptions());

        boolean anonymity = Boolean.parseBoolean(Objects.requireNonNull(event.getOption("anonymous")).getAsString());
        String title = Objects.requireNonNull(event.getOption("title")).getAsString();

        if (Poll.pollInstance != null) {

            event.reply("You are not allowed to start a new poll while there is an ongoing one").queue();

            event.getChannel()
                    .sendMessage("Poll " + Poll.pollInstance.getPoll_id() + " is currently taking place")
                    .referenceById(Poll.pollInstance.getPoll_id()).queueAfter(2, TimeUnit.SECONDS);

        } else {

            Poll.PollBuilder builder = new Poll.PollBuilder()
                    .setTitle(title)
                    .setAnonymity(anonymity)
                    .setPollInitiator(event.getUser())
                    .setTimeInMinutes(duration);

            for (int i = 3; i < event.getOptions().size(); i++) {
                builder = builder.setOption(event.getOptions().get(i).getAsString());
            }

            Poll newPoll = builder.build();
            newPoll.makePoll(event);
        }


    }
}
