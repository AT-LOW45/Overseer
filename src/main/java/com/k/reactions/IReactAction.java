package com.k.reactions;

import java.util.List;
import java.util.function.Consumer;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public interface IReactAction {

    void handle(MessageReactionAddEvent reactionAddEvent);

    <T> void update(T arg, Consumer<T> updateCallback);

    // for polls
    default void reject(int type) {};

    List<Long> getIdentifier();
}
