package com.k.reactions;

import java.util.HashSet;
import java.util.Set;

import com.k.modules.polly.interactions.VoteCaster;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;


public class ReactionHandler {

    public ReactionHandler() {
        registerReactAction(new VoteCaster());
    }

    private final Set<IReactAction> reactActionSet = new HashSet<>();

    private IReactAction identifyAction(long identifier) {
        return reactActionSet
                .stream()
                .filter(reactAction -> reactAction.getIdentifier().contains(identifier))
                .findFirst()
                .orElse(null);
    }

    public void handle(MessageReactionAddEvent event) {

        IReactAction targetAction = identifyAction(event.getMessageIdLong());

        if (targetAction != null) {
            targetAction.handle(event);
        } else {
            System.out.println("no event");
        }

    }

    public void reject(MessageReactionAddEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("Reactions added by ")
                .append(event.getUser().getAsMention())
                .append(" are not valid");

        event.getChannel().sendMessage(sb).queue();
    }

    private void registerReactAction(IReactAction reactAction) {
        reactActionSet.add(reactAction);
    }

}
