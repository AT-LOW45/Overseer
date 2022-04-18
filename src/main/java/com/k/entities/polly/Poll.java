package com.k.entities.polly;

import com.k.Overseer;
import com.k.utilities.MiscConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Poll {

    private final Map<String, Integer> votes = new HashMap<>(); // emote, count
    private final Map<String, String> reactMapping = new HashMap<>(); // <desc, emoji>
    private final List<String> options;
    private int noOfVoters;
    private String pollTitle;
    private final Set<Voter> voters = new HashSet<>();
    private long poll_id;
    private String pollToken;
    private User pollInitiator;
    private int timeInMinutes;
    private boolean anonymity;
    public boolean isActive;
    public static Poll pollInstance; // access update methods in static context
    private Message pollMsg;

    private Poll(PollBuilder builder) {
        this.pollInitiator = builder.pollInitiator;
        this.pollToken = MiscConfig.generateID();
        this.anonymity = builder.anonymity;
        this.options = builder.options;
        this.timeInMinutes = builder.timeInMinutes;
        this.noOfVoters = 0;
        Poll.pollInstance = this;
        isActive = true;
    }

    public static class PollBuilder {

        private String pollTitle;
        private boolean anonymity;
        private final List<String> options = new ArrayList<>();
        private User pollInitiator;
        private int timeInMinutes;

        public PollBuilder setTitle(String title) {
            this.pollTitle = title;
            return this;
        }

        public PollBuilder setOption(String option) {
            this.options.add(option);
            return this;
        }

        public PollBuilder setAnonymity(boolean anonymity) {
            this.anonymity = anonymity;
            return this;
        }

        public PollBuilder setPollInitiator(User user) {
            this.pollInitiator = user;
            return this;
        }

        public PollBuilder setTimeInMinutes(int duration) {
            this.timeInMinutes = duration;
            return this;
        }

        public Poll build() {
            return new Poll(this);
        }

    }

    public void setPoll_id(long poll_id) {
        this.poll_id = poll_id;
    }


    public Map<String, Integer> getVotes() {
        return votes;
    }

    public Map<String, String> getReactMapping() {
        return reactMapping;
    }

    public String getPollToken() {
        return pollToken;
    }

    public Set<Voter> getVoters() {
        return voters;
    }

    public long getPoll_id() {
        return poll_id;
    }

    public int getTimeInMinutes() {
        return this.timeInMinutes;
    }

    public User getPollInitiator() {
        return pollInitiator;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getPollTitle() {
        return pollTitle;
    }


    private void configureOptions() {

        AtomicInteger i = new AtomicInteger();
        // get random emojis
        Arrays.stream(VoteOptions.values()).
                collect(Collectors.collectingAndThen(Collectors.toList(), voteOptions -> {
                    Collections.shuffle(voteOptions);
                    return voteOptions.stream();
                })).limit(options.size()).collect(Collectors.toList())
                .forEach(voteOptions -> {
                    reactMapping.put(options.get(i.get()), voteOptions.emote);
                    votes.put(voteOptions.emote, 0);
                    i.getAndIncrement();
                });
    }

    public Poll updateVotes(String voteCasted) {
        votes.put(voteCasted, votes.get(voteCasted) + 1);
        return this;
    }

    public Poll addMember(Voter voter) {
        voters.add(voter);
        return this;
    }


    public void updatePollMsg() {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle(pollTitle)
                .setDescription("This poll will last for " + "`" + this.getTimeInMinutes() + "` " + "minute(s)");

        for (Map.Entry<String, String> entry : reactMapping.entrySet()) {
            builder.addField("Option", entry.getValue() + " " + entry.getKey(), false);
        }

        builder.setFooter("Poll updated at: " + MiscConfig.setTime("hm"));

        pollMsg.editMessageEmbeds(builder.build()).queue();

    }

    public void endPoll(String pollToken) {
        Poll.pollInstance = null;

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle(pollTitle)
                .setDescription("Results");

        for (Map.Entry<String, Integer> entry : votes.entrySet()) {
            builder.addField("Option", entry.getKey() + ": `" + entry.getValue() + "`", true);
        }

        builder.setFooter("Poll ended at: " + MiscConfig.setTime("hm"));

        pollMsg.editMessageEmbeds(builder.build()).queue();

        pollMsg = null;
        votes.clear();
        reactMapping.clear();
        voters.clear();
        options.clear();


    }


    public void makePoll(SlashCommandEvent slashCMD) {

        configureOptions();
        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle(getPollTitle());
        builder.setDescription("This poll will last for: "
                + "`" + this.getTimeInMinutes() + "`"
                + " minute(s)");


        for (Map.Entry<String, String> entry : reactMapping.entrySet()) {
            builder.addField("", entry.getValue() + " " + entry.getKey(), false);
        }

        builder.setFooter("Poll started at: " + MiscConfig.setTime("hm"));

        TextChannel channel = slashCMD.getTextChannel();
        slashCMD.reply("Poll started").queue();

        channel.sendMessageEmbeds(builder.build()).queue(message -> {
            reactMapping.forEach((s, s2) -> message.addReaction(s2).queue());
            setPoll_id(message.getIdLong());
            pollMsg = message;
            try {
                TimeUnit.MINUTES.sleep(this.getTimeInMinutes());
                isActive = false;
            } catch (InterruptedException e) {
                Overseer.JDAinstance.getTextChannelById(message.getTextChannel().getIdLong())
                        .sendMessage("An error " +
                        "occurred while ending the poll").queue();
                e.printStackTrace();
            }

        });

        Poll.pollInstance.getPollInitiator().openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessage("You have started a poll. Use the token "
                        + "`" + Poll.pollInstance.pollToken + "`" +
                        " if you wish to end the poll before the specified time. **DO NOT SHARE THIS TOKEN WITH " +
                        "ANYONE YOU DON'T TRUST.**").queue());

    }


}
