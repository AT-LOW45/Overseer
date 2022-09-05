package com.k.modules.game;

public interface GameHost {

    void prepareSession();
    void commentate();
    void advance();
    void endSession();
    Lobby getLobby();

}
