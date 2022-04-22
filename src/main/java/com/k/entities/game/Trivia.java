package com.k.entities.game;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;

@NoArgsConstructor
@Getter
@Setter
public class Trivia <T> implements GameHost<T> {

    private T gameType;
    private Set<String> questions;
    private User player;
    private boolean isHosting;
    private String difficulty;
    private final int allowedIdleTimeInSeconds = 30;
    private final int noOfQuestions = 5;
    private final String questionType = "multiple";
    private final String encoding = "base64";


    public Trivia(T gameType, User player) {
        this.gameType = gameType;
        this.isHosting = true;
        this.player = player;
    }

    public Trivia<T> getType() {
        return this;
    }

    public byte[] decode(String toDecode) {
        return Base64.getDecoder().decode(toDecode);
    }

    public T getGameType() {
        return gameType;
    }

    @Override
    public void prepareSession(GuildMessageReceivedEvent event, String openingSpeech) {
        event.getChannel().sendMessage(openingSpeech).queue();
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
