package com.k.commands;

import com.k.entities.polly.Poll;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.Objects;

public class EndPollCommand implements ICommand {

    @Override
    public String getName() {
        return "endpoll";
    }

    @Override
    public String getDescription() {
        return "closes a poll before the specified duration.";
    }

    @Override
    public void handle(SlashCommandEvent event) {
        String token = Objects.requireNonNull(event.getOption("polltoken")).getAsString();

        if (Poll.pollInstance != null) {

            if (Poll.pollInstance.getPollToken().equals(token)) {

                event.reply("Poll " + token + " has ended, results have been published").queue();
                Poll.pollInstance.endPoll(token);

            } else {
                event.reply("Poll not found").queue();
            }

        } else {
            event.reply("There is no ongoing poll").queue();
        }

    }
}
