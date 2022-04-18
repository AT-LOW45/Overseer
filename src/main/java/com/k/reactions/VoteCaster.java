package com.k.reactions;

import com.k.entities.polly.Poll;
import com.k.entities.polly.Voter;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VoteCaster implements ReactAction {

    private static GuildMessageReactionAddEvent reactionAddEvent;


    @Override
    public void handle(GuildMessageReactionAddEvent reactionAddEvent) {
        VoteCaster.reactionAddEvent = reactionAddEvent;

        Poll.pollInstance.getVoters()
                .stream()
                .filter(voter -> voter.getUser().equals(reactionAddEvent.getUser()))
                .findFirst()
                .ifPresentOrElse(voter -> {

                    update(voter, registered -> {
                          if(voter.getVoteCount() > 3) {
                              reject(1);
                          } else {
                              System.out.println("do something else");
                          }
                    });
                }, () -> update(reactionAddEvent.getUser(), user -> {

                    String voteCasted = reactionAddEvent.getReaction().getReactionEmote().getEmoji();

                    if(!Poll.pollInstance.getVotes().containsKey(voteCasted)) {
                        reject(2);
                    } else {
                        Poll.pollInstance.addMember(new Voter(user, voteCasted))
                                .updateVotes(voteCasted)
                                .updatePollMsg();
                    }
                }));

        reactionAddEvent.getReaction().removeReaction().queue();

    }

    @Override
    public <T> void update(T arg, Consumer<T> updateCallback) {
        updateCallback.accept(arg);
    }


    @Override
    public void reject(int type) {

        reactionAddEvent.getUser().openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessage(type == 1 ?
                        ("You have exceeded the maximum vote count of " + Voter.voteCap) :
                        ("You have provided an invalid vote option")).queue());

    }

    @Override
    public List<Long> getIdentifier() {

        return Poll.pollInstance == null ? Stream.of(0L).collect(Collectors.toList()) :
                Stream.of(Poll.pollInstance.getPoll_id()).collect(Collectors.toList());

    }
}
