package com.k.modules.polly;

import com.k.Activity;
import com.k.Channels;
import com.k.Overseer;
import com.k.factory.MessageEmbedFactory;
import com.k.utilities.MiscConfig;
import lombok.Builder;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Builder
@Getter
// warning - all poll options must be unique to properly store in LinkedHashMap
public class Poll {

    private final Map<String, Integer> votes = new LinkedHashMap<>(); // emote, count
    private final Map<String, String> reactMapping = new LinkedHashMap<>(); // <desc, emoji>
    private List<String> options;
    private int noOfVoters;
    private String pollTitle;
    private final Set<Voter> voters = new HashSet<>();
    private long poll_id;
    private String pollToken;
    private User pollInitiator;
    private int timeInMinutes;
    private boolean anonymity;
    protected static Poll pollInstance; // TODO - to be removed, access poll instance via app context
    private Message pollMsg;

    private SlashCommandInteractionEvent pollInitCommand;

    private ApplicationContext applicationContext;
    private DefaultListableBeanFactory beanFactory;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext,
                                      DefaultListableBeanFactory beanFactory) {
        this.applicationContext = applicationContext;
        this.beanFactory = beanFactory;
    }

    public void buildPoll() {

        this.pollToken = MiscConfig.generateID(Activity.POLL);
        configureOptions();

        MessageEmbed pollEmbed = applicationContext.getBean(MessageEmbedFactory.class).getPollEmbed();

        this.pollInitiator.openPrivateChannel()
                .queue(privateChannel -> privateChannel.sendMessage("You have started a poll. Use the token "
                                + "`" + this.pollToken + "`" +
                                " if you wish to end the poll before the specified time. **This token is CONFIDENTIAL. Do not share.**")
                        .queue());

        Objects.requireNonNull(Overseer.getJDAinstance()
                        .getTextChannelById(Channels.POLL.getChannelId()))
                .sendMessageEmbeds(pollEmbed)
                .queue(message -> {
                    setPoll_id(message.getIdLong());
                    this.pollMsg = message;
                    this.getReactMapping().forEach((emote, option) -> {
                        message.addReaction(Emoji.fromUnicode(emote)).queue();
                    });
                    try {
                        TimeUnit.MINUTES.sleep(this.getTimeInMinutes());
                        this.endPoll(this.pollToken);
                    } catch (InterruptedException e) {
                        Objects.requireNonNull(
                                        Overseer.JDAinstance.getTextChannelById(Channels.POLL.getChannelId()))
                                .sendMessage("An error " +
                                        "occurred while ending the poll")
                                .queue();
                        e.printStackTrace();
                    }

                });
    }

    private void setPoll_id(long poll_id) {
        this.poll_id = poll_id;
    }

    private void configureOptions() {
        String[] voteOptionEmotes = Arrays.stream(VoteOptions.values()).map(VoteOptions::getEmote)
                .toArray(String[]::new);

        for (int index = 0; index < options.size(); index++) {
            this.reactMapping.put(options.get(index), voteOptionEmotes[index]);
            this.votes.put(voteOptionEmotes[index], 0);
        }
    }

    public Poll updateVotes(String voteCasted) {
        this.votes.put(voteCasted, votes.get(voteCasted) + 1);
        return this;
    }

    public Poll addMember(Voter voter) {
        this.voters.add(voter);
        return this;
    }

    public void updatePollMsg() {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle(pollTitle)
                .setDescription(
                        "This poll will last for " + "`" + this.getTimeInMinutes() + "` " + "minute(s)");

        for (Map.Entry<String, String> entry : reactMapping.entrySet()) {
            builder.addField("Option", entry.getValue() + " " + entry.getKey(), false);
        }

        builder.setFooter("Poll updated at: " + MiscConfig.setTime("hm"));
        pollMsg.editMessageEmbeds(builder.build())
                .queue();

    }

    public void endPoll(String token) {
        if (!this.pollToken.equals(token)) {
            Objects.requireNonNull(Overseer.getJDAinstance()
                            .getTextChannelById(Channels.POLL.getChannelId()))
                    .sendMessage("Poll not found")
                    .queue();
            return;
        }


        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle(pollTitle)
                .setDescription("Results")
                .setFooter("Poll ended at: " + MiscConfig.setTime("hm"));
        ;

        for (Map.Entry<String, Integer> entry : votes.entrySet()) {
            builder.addField("Option", entry.getKey() + ": `" + entry.getValue() + "`", true);
        }

        pollMsg.editMessageEmbeds(builder.build())
                .queue();
        Objects.requireNonNull(Overseer.JDAinstance
                        .getTextChannelById(Channels.POLL.getChannelId()))
                .sendMessage("Poll " + token + " has ended. Results have been published")
                .setMessageReference(pollMsg.getId());

        beanFactory.destroySingleton("poll");
    }

}
