package com.k.entities.game;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
public class Trivia <T extends Game> extends Game implements GameHost<T> {

    private T triviaType;
    private Set<String> questions;
    private String difficulty;
    private final int noOfQuestions = 5;
    private final String questionType = "multiple";
    private final String encoding = "base64";


    public Trivia(T triviaType, User player) {
        this.triviaType = triviaType;
        super.player = player;
        super.isHosting = true;
        super.allowedIdleTimeInSeconds = 30;
    }

    protected String decode(String toDecode) throws UnsupportedEncodingException {
        byte[] decoded =  Base64.getDecoder().decode(toDecode);
        return new String(decoded, StandardCharsets.UTF_8);
    }

    // get parent class
    public Trivia<T> getGameType() {
        return this;
    }

    // get child class
    @Override
    public T getGameSessionType() {
        return triviaType;
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
