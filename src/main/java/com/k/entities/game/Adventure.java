package com.k.entities.game;

import lombok.Getter;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

@Getter
public class Adventure <T extends Game> extends Game implements GameHost<T>{

    private final double encounterRate = 20.5;


    @Override
    public T getGameSessionType() {
        return null;
    }

    @Override
    public void prepareSession(GuildMessageReceivedEvent event, String openingSpeech) {

    }

    @Override
    public void concludeSession() {

    }

    @Override
    public void commentate(boolean type) {

    }

    @Override
    public T shuffle() {
        return null;
    }
}
