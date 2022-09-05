package com.k.modules.polly.interactions;

import com.k.Channels;
import com.k.Overseer;
import com.k.modules.polly.Poll;
import com.k.modules.polly.Voter;
import com.k.reactions.IReactAction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VoteCaster implements IReactAction {

    private MessageReactionAddEvent reactionAddEvent;
    private ApplicationContext applicationContext;
    private Poll currentPoll;

    @PostConstruct
    public void setCurrentPoll() {
        this.currentPoll = (Poll) applicationContext.getBean("poll");
    }

    @Override
    public void handle(MessageReactionAddEvent reactionAddEvent) {
        this.reactionAddEvent = reactionAddEvent;

        currentPoll.getVoters()
                .stream()
                .filter(voter -> voter.getUser().equals(reactionAddEvent.getUser()))
                .findFirst()
                .ifPresentOrElse(voter -> {

                    update(voter, registered -> {
                        if (voter.getVoteCount() > 3) {
                            reject(1);
                        } else {
                            System.out.println("do something else");
                        }
                    });
                }, () -> update(reactionAddEvent.getUser(), user -> {

                    String voteCasted = reactionAddEvent.getReaction().getEmoji().getAsReactionCode();

                    if (!this.currentPoll.getVotes().containsKey(voteCasted)) {
                        reject(2);
                    } else {
                        this.currentPoll.addMember(new Voter(user, voteCasted))
                                .updateVotes(voteCasted)
                                .updatePollMsg();
                    }
                }));

        reactionAddEvent.getReaction().removeReaction().queue();

    }

    @Override
    public <T> void update(T participant, Consumer<T> updateCallback) {
        if ((participant instanceof Voter || participant instanceof User)) {
            if (((User) participant).isBot()) {
                Objects.requireNonNull(
                                Overseer.getJDAinstance()
                                        .getTextChannelById(Channels.POLL.getChannelId()))
                        .sendMessage(
                                "Only non-bot users are allowed to vote").queue();
            } else {
                updateCallback.accept(participant);
            }
        } else {
            throw new IllegalArgumentException("Unidentified users are not allowed to vote.");
        }
    }

    @Override
    public void reject(int type) {

        Objects.requireNonNull(reactionAddEvent.getUser()).openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessage(type == 1 ?
                        ("You have exceeded the maximum vote count of " + Voter.voteCap) :
                        ("You have provided an invalid vote option")).queue());
    }

    @Override
    public List<Long> getIdentifier() {

        return this.currentPoll == null ? Stream.of(0L).collect(Collectors.toList()) :
                Stream.of(this.currentPoll.getPoll_id()).collect(Collectors.toList());
    }
}
