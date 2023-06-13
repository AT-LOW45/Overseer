package com.k.modules.game;

import lombok.Getter;

@Getter
public abstract class Adventure implements GameHost{

    private final double encounterRate = 20.5;

    @Override
    public void advance() {

        
    }

    @Override
    public void commentate() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void endSession() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Lobby getLobby() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void prepareSession() {
        // TODO Auto-generated method stub
        
    }

    


}
