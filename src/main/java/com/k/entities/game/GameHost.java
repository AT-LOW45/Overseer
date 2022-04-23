package com.k.entities.game;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface GameHost<T> {

    T getGameSessionType();

    void prepareSession(GuildMessageReceivedEvent event, String openingSpeech);

    void concludeSession();

    void commentate(boolean type);

    T shuffle();

}
