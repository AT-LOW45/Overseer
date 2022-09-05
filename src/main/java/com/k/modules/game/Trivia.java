package com.k.modules.game;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;


public abstract class Trivia implements GameHost {

    private GameHost gameHost;
    private Set<String> questions;
    private String difficulty;
    private final int noOfQuestions = 5;
    private final String questionType = "multiple";
    private final String encoding = "base64";
    

    public Trivia(GameHost gameHost) {
        this.gameHost = gameHost;
        
    }

    protected String decode(String toDecode) throws UnsupportedEncodingException {
        byte[] decoded = Base64.getDecoder().decode(toDecode);
        return new String(decoded, StandardCharsets.UTF_8);
    }

    @Override
    public void prepareSession() {
        // TODO Auto-generated method stub
        gameHost.prepareSession();
    }

    @Override
    public void commentate() {
        // TODO Auto-generated method stub
        gameHost.commentate();
    }

    @Override
    public void advance() {
        // TODO Auto-generated method stub
        gameHost.advance();
    }

    @Override
    public void endSession() {
        // TODO Auto-generated method stub
        System.out.println("Let's test your know-how");
        gameHost.endSession();
    }

    @Override
    public Lobby getLobby() {
        // TODO Auto-generated method stub
        return gameHost.getLobby();
    }

}
