package com.k.modules.polly.interactions;

import java.util.List;
import java.util.Objects;

import com.k.commands.IMessageCommand;

import com.k.commands.ISlashCommand;
import com.k.modules.polly.Poll;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EndPollCommand implements ISlashCommand {

    private final ApplicationContext applicationContext;
    private final DefaultListableBeanFactory beanFactory;

    public EndPollCommand(ApplicationContext applicationContext, DefaultListableBeanFactory beanFactory) {
        this.applicationContext = applicationContext;
        this.beanFactory = beanFactory;
    }

    @Override
    public String getName() {
        return "endpoll";
    }

    @Override
    public String getDescription() {
        return "closes a poll before the specified duration.";
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        String token = Objects.requireNonNull(event.getOption("polltoken")).getAsString();

        if (!beanFactory.containsSingleton("poll")) {
            event.reply("There is no ongoing poll").queue();
            return;
        }

        applicationContext.getBean(Poll.class).endPoll(token);
        event.reply("Poll " + token + " has ended, results have been published").queue();

    }

    @Override
    public String[] parseOptions() {
        return new String[0];
    }
}
