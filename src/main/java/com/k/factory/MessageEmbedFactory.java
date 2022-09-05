package com.k.factory;

import com.k.modules.polly.Poll;
import com.k.utilities.MiscConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MessageEmbedFactory {

    private final DefaultListableBeanFactory beanFactory;

    @Autowired
    public MessageEmbedFactory(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public MessageEmbed getPollEmbed() {
        EmbedBuilder pollEmbedBuilder = new EmbedBuilder();
        Poll currentPoll = (Poll) beanFactory.getSingleton("poll");

        if (currentPoll != null) {
            pollEmbedBuilder.setTitle(currentPoll.getPollTitle())
                    .setDescription(
                            "This poll will last for: " + "`" + currentPoll.getTimeInMinutes() + "`" + " minutes(s)")
                    .setFooter("Poll started at: " + MiscConfig.setTime("hm"));

            currentPoll.getReactMapping().forEach((emote, option) -> {
                pollEmbedBuilder.addField("Option", emote + ": " + option, false);
            });

            return pollEmbedBuilder.build();
        } else {
            throw new NullPointerException("there is no poll");
        }
    }

}
