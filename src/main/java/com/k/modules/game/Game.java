package com.k.modules.game;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.User;

@Getter
@Setter
public class Game implements GameHost{

    private Lobby lobby;

    public Game(List<User> players, int allowedIdleTimeInSeconds) {
        this.lobby = new Lobby(players, allowedIdleTimeInSeconds);
    } 

    @Override
    public void prepareSession() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void commentate() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void advance() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void endSession() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Lobby getLobby() {
        // TODO Auto-generated method stub
        return this.lobby;
    }





}
