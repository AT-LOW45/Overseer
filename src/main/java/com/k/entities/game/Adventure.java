package com.k.entities.game;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Adventure <T> implements GameHost<T>{

    @Override
    public T getGameType() {
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
