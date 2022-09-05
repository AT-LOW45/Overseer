package com.k.modules.game;

public class PopCultureTrivia extends Trivia {

    private int weebKudos;
    private final String[] comments = {"wow, you're such a weeb", "za warudoooo!"};
    private final int[] categories = {};

    public PopCultureTrivia(GameHost gameHost) {
        super(gameHost);
    }

    @Override
    public void prepareSession() {
        // TODO Auto-generated method stub
        super.prepareSession();
        System.out.println("do you know pop?");
    }



}
