package com.k.reactions;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.util.HashSet;
import java.util.Set;

public class ReactionHandler {

    public ReactionHandler() {
        registerReactAction(new VoteCaster());
    }

    private final Set<ReactAction> reactActionSet = new HashSet<>();

    private ReactAction identifyAction(long identifier) {
        return reactActionSet
                .stream()
                .filter(reactAction -> reactAction.getIdentifier().contains(identifier))
                .findFirst()
                .orElse(null);
    }

    public void handle(GuildMessageReactionAddEvent event) {
//        System.out.println(event.getReaction().getReactionEmote().getEmoji());
        ReactAction targetAction = identifyAction(event.getMessageIdLong());

        if (targetAction != null) {
            targetAction.handle(event);
        } else {
            System.out.println("no event");
        }

    }

    public void reject(GuildMessageReactionAddEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("Reactions added by ")
                .append(event.getUser().getAsMention())
                .append(" are not valid");

        event.getChannel().sendMessage(sb).queue();
    }

    private void registerReactAction(ReactAction reactAction) {
        reactActionSet.add(reactAction);
    }

}
