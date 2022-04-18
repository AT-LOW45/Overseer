package com.k.reactions;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.util.List;
import java.util.function.Consumer;

public interface ReactAction {

    void handle(GuildMessageReactionAddEvent reactionAddEvent);

    <T> void update(T arg, Consumer<T> updateCallback);

    // for polls
    default void reject(int type) {};

    List<Long> getIdentifier();
}
